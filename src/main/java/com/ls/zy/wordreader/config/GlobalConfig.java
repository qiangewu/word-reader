package com.ls.zy.wordreader.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:global.properties")
@Data
public class GlobalConfig {

    /**
     * 生成临时文件及图片存放路径
     */
    @Value("${tempDir}")
    String tempDir;

    /**
     * 楼宇潜力客户用能初步分析报告模板路径
     */
    @Value("${templateDir}")
    String templateDir;

    /**
     * 系统环境
     */
    @Value("${systemEnv}")
    String systemEnv;

    /**
     * phantomjs.exe路径
     */
    @Value("${phantomjsPath}")
    String phantomjsPath;

}
