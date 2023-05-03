package com.tracejp.yozu.thirdparty.handler.file;

import cn.hutool.core.date.DateUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.redis.service.RedisService;
import com.tracejp.yozu.thirdparty.constant.FileConstant;
import com.tracejp.yozu.thirdparty.domain.dto.FileChunkTaskRedisEntity;
import com.tracejp.yozu.thirdparty.domain.param.InitChunkParam;
import com.tracejp.yozu.thirdparty.domain.vo.FileChunkTaskRecordVo;
import com.tracejp.yozu.thirdparty.domain.vo.FileUploadTaskVo;
import com.tracejp.yozu.thirdparty.util.FileUploadUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> minio 实现类 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:21
 */

public class MinioFileHandler implements IFileHandler {

    private AmazonS3 fileClient;

    private RedisService redisService;

    private String clientUrl;


    public MinioFileHandler(AmazonS3 fileClient, RedisService redisService, String clientUrl) {
        this.fileClient = fileClient;
        this.redisService = redisService;
        this.clientUrl = clientUrl;
    }

    @Override
    public String uploadFile(MultipartFile file, String bucketName) throws Exception {
        String fileKey = FileUploadUtils.extractFilename(file);
        InputStream inputStream = file.getInputStream();
        fileClient.putObject(bucketName, fileKey, inputStream, null);
        inputStream.close();
        return getFileUrl(fileKey, bucketName);
    }

    @Override
    public Map<String, String> uploadPreSign(String filename, String bucketName, Map<String, String> params) {
        String fileKey = FileUploadUtils.extractFilename(filename);
        Date currentDate = new Date();
        Date expireDate = DateUtil.offsetMillisecond(currentDate, FileConstant.PRE_SIGN_URL_EXPIRE.intValue());
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileKey)
                .withExpiration(expireDate)
                .withMethod(HttpMethod.PUT);
        if (params != null) {
            params.forEach(request::addRequestParameter);
        }
        URL url = fileClient.generatePresignedUrl(request);
        return Collections.singletonMap("url", url.toString());
    }

    @Override
    public Map<String, String> uploadPreSignByChunk(String identifier, Integer partId) {
        // 尝试获取任务信息
        FileChunkTaskRedisEntity task =
                redisService.getCacheObject(FileConstant.FILE_UPLOAD_TASK_PREFIX + identifier);
        if (task == null) {
            throw new ServiceException("上传任务不存在");
        }

        // 签名
        Map<String, String> params = new HashMap<>(2);
        params.put("partNumber", partId.toString());
        params.put("uploadId", task.getUploadId());
        return this.uploadPreSign(task.getFilename(), task.getBucketName(), params);
    }

    @Override
    public FileUploadTaskVo initChunkTask(InitChunkParam param) {
        // 初始化分片上传
        String fileKey = FileUploadUtils.extractFilename(param.getFilename());
        String contentType = MediaTypeFactory.getMediaType(fileKey)
                .orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        InitiateMultipartUploadResult initiateMultipartUploadResult = fileClient.initiateMultipartUpload(
                new InitiateMultipartUploadRequest(param.getBucket().getBucketName(), fileKey)
                        .withObjectMetadata(objectMetadata)
        );
        String uploadId = initiateMultipartUploadResult.getUploadId();

        // 保存任务信息
        FileChunkTaskRedisEntity task = new FileChunkTaskRedisEntity();
        int chunkNum = (int) Math.ceil(param.getTotalSize() * 1.0 / param.getChunkSize());
        task.setFileKey(fileKey);
        task.setFilename(param.getFilename());
        task.setBucketName(param.getBucket().getBucketName());
        task.setChunkNum(chunkNum);
        task.setChunkSize(param.getChunkSize());
        task.setTotalSize(param.getTotalSize());
        task.setUploadId(uploadId);
        redisService.setCacheObject(FileConstant.FILE_UPLOAD_TASK_PREFIX + param.getIdentifier(),
                task, FileConstant.FILE_UPLOAD_TASK_EXPIRE, TimeUnit.MILLISECONDS);

        // 返回初始化信息
        FileUploadTaskVo result = new FileUploadTaskVo();
        result.setFinished(false);
        result.setPath(getFileUrl(fileKey, param.getBucket().getBucketName()));
        return result;
    }

    @Override
    public FileUploadTaskVo chunkMerge(String identifier) {
        // 尝试获取任务信息
        FileChunkTaskRedisEntity task =
                redisService.getCacheObject(FileConstant.FILE_UPLOAD_TASK_PREFIX + identifier);
        if (task == null) {
            throw new ServiceException("上传任务不存在");
        }

        // 尝试合并
        ListPartsRequest listPartsRequest =
                new ListPartsRequest(task.getBucketName(), task.getFileKey(), task.getUploadId());
        PartListing partListing = fileClient.listParts(listPartsRequest);
        List<PartSummary> parts = partListing.getParts();
        if (!Objects.equals(parts.size(), task.getChunkNum())) {
            // 已上传分块数量与记录中的数量不对应，不能合并分块
            throw new ServiceException("分片缺失，请重新上传");
        }

        // 合并分块
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest()
                .withUploadId(task.getUploadId())
                .withKey(task.getFileKey())
                .withBucketName(task.getBucketName())
                .withPartETags(parts.stream()
                        .map(partSummary -> new PartETag(partSummary.getPartNumber(), partSummary.getETag()))
                        .collect(Collectors.toList())
                );
        fileClient.completeMultipartUpload(completeMultipartUploadRequest);

        // 删除任务信息
        redisService.deleteObject(FileConstant.FILE_UPLOAD_TASK_PREFIX + identifier);

        // 返回结果
        FileUploadTaskVo result = new FileUploadTaskVo();
        result.setFinished(true);
        result.setPath(getFileUrl(task.getFileKey(), task.getBucketName()));
        return result;
    }

    @Override
    public FileUploadTaskVo getChunkTaskInfo(String identifier) {
        FileUploadTaskVo result = new FileUploadTaskVo();

        FileChunkTaskRedisEntity taskVo =
                redisService.getCacheObject(FileConstant.FILE_UPLOAD_TASK_PREFIX + identifier);
        String bucketName = taskVo.getBucketName();

        boolean doesObjectExist = fileClient.doesObjectExist(bucketName, taskVo.getFileKey());
        if (doesObjectExist) {  // 已存在，直接返回
            result.setFinished(true);
            result.setPath(getFileUrl(taskVo.getFileKey(), bucketName));
            return result;
        }

        // 未上传完，返回分片信息
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, taskVo.getFileKey(), taskVo.getUploadId());
        PartListing partListing = fileClient.listParts(listPartsRequest);
        List<PartSummary> parts = partListing.getParts();
        result.setFinished(false);
        if (!CollectionUtils.isEmpty(parts)) {
            List<FileChunkTaskRecordVo> recordVos = new ArrayList<>(parts.size());
            // 已完成分片
            parts.forEach(item -> {
                FileChunkTaskRecordVo recordVo = new FileChunkTaskRecordVo();
                recordVo.setPartId(item.getPartNumber());
                recordVo.setSize(item.getSize());
                recordVos.add(recordVo);
            });

            result.setRecord(recordVos);
        }
        return result;
    }

    @Override
    public String getFileUrl(String fileKey, String bucketName) {
        return clientUrl + "/" + bucketName + "/" + fileKey;
    }

}
