package com.tracejp.yozu.thirdparty.domain.param;

import com.tracejp.yozu.api.thirdparty.enums.FileBucketEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 初始化分片任务参数 <p/>
 *
 * @author traceJP
 * @since 2023/5/3 10:31
 */
@Data
public class InitChunkParam {

    /**
     * 文件唯一标识(MD5)
     */
    @NotBlank(message = "文件标识不能为空")
    private String identifier;

    /**
     * 文件大小（byte）
     */
    @NotNull(message = "文件大小不能为空")
    private Long totalSize;

    /**
     * 分片大小（byte）
     */
    @NotNull(message = "分片大小不能为空")
    private Long chunkSize;

    /**
     * 文件保存名
     */
    @NotBlank(message = "文件保存名称不能为空")
    private String filename;

    /**
     * 桶名
     */
    @NotNull(message = "桶名不能为空")
    private FileBucketEnum bucket;

}
