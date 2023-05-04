package com.tracejp.yozu.thirdparty.controller;

import com.tracejp.yozu.api.thirdparty.enums.FileBucketEnum;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.common.core.utils.file.FileUtils;
import com.tracejp.yozu.common.security.annotation.InnerAuth;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import com.tracejp.yozu.system.api.domain.SysFile;
import com.tracejp.yozu.thirdparty.domain.TmsFileHistory;
import com.tracejp.yozu.thirdparty.domain.param.InitChunkParam;
import com.tracejp.yozu.thirdparty.domain.vo.FileUploadTaskVo;
import com.tracejp.yozu.thirdparty.handler.file.IFileHandler;
import com.tracejp.yozu.thirdparty.service.ITmsFileHistoryService;
import com.tracejp.yozu.thirdparty.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p> 文件 Controller <p/>
 *
 * @author traceJP
 * @since 2023/5/2 11:36
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileHandler fileHandler;

    @Autowired
    private ITmsFileHistoryService tmsFileHistoryService;


    /**
     * 文件上传请求
     * @deprecated 请使用签名直传
     */
    @Deprecated
    @InnerAuth
    @PostMapping("/upload")
    public R<SysFile> upload(MultipartFile file, FileBucketEnum bucket) {
        try {
            // 上传并返回访问地址
            String url = fileHandler.uploadFile(file, bucket.getBucketName());
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 获取签名
     *
     * @param filename 保存文件名 eg abc.jpg
     * @param bucket   桶名
     * @return 签名
     */
    @GetMapping("/sign/{filename}")
    public R<?> uploadPreSign(@PathVariable String filename, @RequestParam("bucket") FileBucketEnum bucket) {
        String fileKey = FileUploadUtils.extractFilename(filename);
        Map<String, String> result = fileHandler.uploadPreSign(fileKey, bucket.getBucketName(), null);
        return R.ok(result);
    }

    /**
     * 获取分片签名
     * @param identifier 文件标识
     * @param partId 分片序号
     * @return 签名
     */
    @GetMapping("/sign/chunk/{identifier}")
    public R<?> uploadPreSignByChunk(@PathVariable String identifier, @RequestParam("partId") Integer partId) {
        Map<String, String> result = fileHandler.uploadPreSignByChunk(identifier, partId);
        return R.ok(result);
    }

    /**
     * 创建一个分片任务
     * @param param 分片任务参数
     * @return 任务信息
     */
    @PostMapping("/chunk/init")
    public R<FileUploadTaskVo> createChunkTask(@RequestBody InitChunkParam param) {
        FileUploadTaskVo taskVo = fileHandler.initChunkTask(param);
        return R.ok(taskVo);
    }

    /**
     * 合并分片
     * @param identifier 文件标识
     * @return 合并结果
     */
    @PostMapping("/merge/{identifier}")
    public R<?> merge(@PathVariable String identifier) {
        try {
            FileUploadTaskVo taskVo = fileHandler.chunkMerge(identifier);

            // 记录文件上传历史
            TmsFileHistory fileHistory = new TmsFileHistory();
            fileHistory.setIdentifier(identifier);
            fileHistory.setPath(taskVo.getPath());
            fileHistory.setCreateTime(DateUtils.getNowDate());
            fileHistory.setCreateBy(SecurityUtils.getUsername());
            tmsFileHistoryService.insertTmsFileHistory(fileHistory);
        } catch (ServiceException e) {
            return R.fail(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 获取一个上传任务的信息
     * @return 任务信息
     */
    @GetMapping("/info/{identifier}")
    public R<FileUploadTaskVo> getUploadInfo(@PathVariable String identifier) {
        FileUploadTaskVo taskVo;

        // 检查 md5 是否匹配（秒传处理）
        TmsFileHistory file = tmsFileHistoryService.selectFileHistoryByIdentifier(identifier);
        if (file != null) {
            taskVo = new FileUploadTaskVo();
            taskVo.setFinished(true);
            taskVo.setPath(file.getPath());
            return R.ok(taskVo);
        }

        // 查询是否为分块上传任务
        taskVo = fileHandler.getChunkTaskInfo(identifier);
        if (taskVo != null) {
            return R.ok(taskVo);
        }

        // 无上传任务
        return R.fail();
    }

}
