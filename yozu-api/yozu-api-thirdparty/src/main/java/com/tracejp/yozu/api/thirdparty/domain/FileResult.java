package com.tracejp.yozu.api.thirdparty.domain;

import lombok.Data;

/**
 * <p> 文件上传结果 <p/>
 *
 * @author traceJP
 * @since 2023/5/4 18:45
 */
@Data
public class FileResult {

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

}
