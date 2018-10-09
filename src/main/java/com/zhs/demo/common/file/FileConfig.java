package com.zhs.demo.common.file;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Zhang on 2018/10/9.
 */
@Configuration
public class FileConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件大小限制，超出此大小页面会抛出异常信息
        factory.setMaxFileSize("10MB");
        // 设置上传数据总大小
        factory.setMaxRequestSize("100MB");

        return factory.createMultipartConfig();
    }

}
