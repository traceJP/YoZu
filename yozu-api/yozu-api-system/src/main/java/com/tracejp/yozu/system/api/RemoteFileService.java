package com.tracejp.yozu.system.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.tracejp.yozu.common.core.constant.ServiceNameConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.system.api.domain.SysFile;
import com.tracejp.yozu.system.api.factory.RemoteFileFallbackFactory;

/**
 * 文件服务
 * 
 * @author yozu
 */
@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService
{
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);
}
