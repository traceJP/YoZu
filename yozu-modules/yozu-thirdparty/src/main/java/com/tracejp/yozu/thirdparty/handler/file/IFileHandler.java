package com.tracejp.yozu.thirdparty.handler.file;

import com.tracejp.yozu.thirdparty.domain.param.InitChunkParam;
import com.tracejp.yozu.thirdparty.domain.vo.FileUploadTaskVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p> FileHandler 接口 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:16
 */
public interface IFileHandler {

    /**
     * 文件上传接口
     * @param file 文件
     * @param bucketName 桶名
     * @return 文件访问地址
     */
    String uploadFile(MultipartFile file, String bucketName) throws Exception;

    /**
     * 获取文件上传签名
     * @param fileKey 文件名
     * @param bucketName 桶名
     * @param params 附加参数
     * @return 签名
     */
    Map<String, String> uploadPreSign(String fileKey, String bucketName, Map<String, String> params);

    /**
     * 获取文件上传签名
     * @param identifier 文件标识
     * @param partId 分块编号
     * @return 签名
     */
    Map<String, String> uploadPreSignByChunk(String identifier, Integer partId);

    /**
     * 初始化分块上传任务
     * @param param 初始化参数
     * @return 任务信息
     */
    FileUploadTaskVo initChunkTask(InitChunkParam param);

    /**
     * 合并分片
     * @param identifier 文件标识
     */
    FileUploadTaskVo chunkMerge(String identifier);

    /**
     * 得到分块上传任务信息
     * @param identifier 文件标识
     * @return 任务信息
     */
    FileUploadTaskVo getChunkTaskInfo(String identifier);

    /**
     * 获取文件访问地址
     * @param fileKey 文件名
     * @param bucketName 桶名
     * @return 文件访问地址
     */
    String getFileUrl(String fileKey, String bucketName);

}
