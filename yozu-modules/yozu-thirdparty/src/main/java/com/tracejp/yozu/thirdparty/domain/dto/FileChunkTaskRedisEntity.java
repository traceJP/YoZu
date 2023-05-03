package com.tracejp.yozu.thirdparty.domain.dto;

import lombok.Data;

import java.util.Map;

/**
 * <p> 分片任务实体 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 22:57
 */
@Data
public class FileChunkTaskRedisEntity {

    /**
     * 上传id
     */
    private String uploadId;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件保存名
     */
    private String fileKey;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 分片数量
     */
    private Integer chunkNum;

    /**
     * 分片大小
     */
    private Long chunkSize;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 其他参数
     */
    private Map<String, String> params;

}
