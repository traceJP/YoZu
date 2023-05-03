package com.tracejp.yozu.thirdparty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.amazonaws.services.s3.AmazonS3;
import com.tracejp.yozu.api.thirdparty.enums.FileBucketEnum;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.thirdparty.controller.FileController;
import com.tracejp.yozu.thirdparty.domain.param.InitChunkParam;
import com.tracejp.yozu.thirdparty.domain.vo.FileUploadTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * <p> 文件上传接口测试 <p/>
 *
 * @author traceJP
 * @since 2023/5/3 15:48
 */
@SpringBootTest
@Slf4j
public class TestFileUpload {

    @Autowired
    private FileController fileController;

    @Autowired
    private AmazonS3 fileClient;

    /**
     * 签名上传测试
     */
    @Test
    public void testSignUpload() {
        final String filename = "abc.jpg";
        final FileBucketEnum bucket = FileBucketEnum.DEFAULT;
        R<?> r = fileController.uploadPreSign(filename, bucket);
        if (R.fail() == r) {
            log.error("获取签名失败");
        }

        // 签名url
        String jsonString = JSON.toJSONString(r.getData());
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String url = (String) jsonObject.get("url");
        log.info("签名url: {}", url);

    }

    /**
     * 测试初始化分片上传
     */
    @Test
    public void testInitChunkUpload() {

        InitChunkParam initChunkParam = new InitChunkParam();
        initChunkParam.setFilename("abc.jpg");
        initChunkParam.setBucket(FileBucketEnum.DEFAULT);

        // 计算文件 md5 以及分片信息
        File file = new File("C:\\Users\\15250\\Desktop\\1595197497960.png");
        boolean hasFile = file.isFile();
        if (!hasFile) {
            log.error("文件不存在");
        }

        // md5 http://www.metools.info/other/o21.html
        initChunkParam.setIdentifier("e0568b18b429a9bd13fd327fe7475cb5");

        initChunkParam.setChunkSize(1000L);  // 每片大小
        initChunkParam.setTotalSize(file.length());  // 文件总大小

        R<FileUploadTaskVo> r = fileController.createChunkTask(initChunkParam);
        if (R.FAIL == r.getCode()) {
            log.error("初始化分片上传失败");
        }

        FileUploadTaskVo data = r.getData();
        // data：FileUploadTaskVo(finished=false, path=http://localhost:9000/yozu/2023/05/03/abc_20230503163040A001.jpg, record=null)
        log.info("data：{}", data);

    }

}
