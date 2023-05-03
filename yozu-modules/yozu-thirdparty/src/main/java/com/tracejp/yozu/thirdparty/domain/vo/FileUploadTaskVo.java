package com.tracejp.yozu.thirdparty.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * <p> 文件上传任务记录 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 22:25
 */
@Data
public class FileUploadTaskVo {

    /**
     * 是否完成上传（是否已经合并分片）
     */
    private Boolean finished;

    /**
     * 文件访问地址
     */
    private String path;

    /**
     * 已上传的分片记录
     */
    private List<FileChunkTaskRecordVo> record;

}
