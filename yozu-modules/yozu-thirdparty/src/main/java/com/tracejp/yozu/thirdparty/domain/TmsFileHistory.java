package com.tracejp.yozu.thirdparty.domain;

import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 上传文件历史对象 tms_file_history
 *
 * @author tracejp
 * @date 2023-05-03
 */
@Data
public class TmsFileHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一自增id
     */
    private Long id;

    /**
     * 文件访问路径
     */
    @Excel(name = "文件访问路径")
    private String path;

    /**
     * 文件md5值
     */
    @Excel(name = "文件md5值")
    private String identifier;

}
