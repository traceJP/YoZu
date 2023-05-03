package com.tracejp.yozu.thirdparty.domain.vo;

import lombok.Data;

/**
 * <p> 分片记录 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 23:14
 */
@Data
public class FileChunkTaskRecordVo {

    /**
     * 当前分片序号
     */
    private Integer partId;

    /**
     * 分片大小
     */
    private Long size;

}
