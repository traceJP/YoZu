package com.tracejp.yozu.api.thirdparty.enums;

/**
 * <p> Bucket枚举 <p/>
 *
 * @author traceJP
 * @since 2023/5/3 10:15
 */
public enum FileBucketEnum {

    DEFAULT((short) 1, "yozu");

    private final Short code;

    private final String bucketName;

    FileBucketEnum(Short code, String bucketName) {
        this.code = code;
        this.bucketName = bucketName;
    }

    public Short getCode() {
        return code;
    }

    public String getBucketName() {
        return bucketName;
    }

}
