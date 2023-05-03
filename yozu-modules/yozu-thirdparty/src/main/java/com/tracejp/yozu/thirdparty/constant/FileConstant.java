package com.tracejp.yozu.thirdparty.constant;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/2 21:11
 */
public class FileConstant {

    /**
     * 文件上传签名过期时间（ms）
     */
    public static final Long PRE_SIGN_URL_EXPIRE = 300000L;

    /**
     * 分片上传任务 cache key 前缀
     */
    public static final String FILE_UPLOAD_TASK_PREFIX = "file:upload:task:";

    /**
     * 分片上传任务过期时间（断点续传的最大时间）（ms）
     */
    public static final Long FILE_UPLOAD_TASK_EXPIRE = 86400000L;

}
