package com.ls.zy.wordreader.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerUtil {

    private static final String RESOURCES_PATH = FreemarkerUtil.class.getClassLoader().getResource("").getPath();

    /**
     * 将datas信息写入模板并返回
     * @param templateFileName
     * @param templateDirectory
     * @param datas
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateString(String templateFileName, String templateDirectory, Map<String, Object> datas)
            throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        // 设置默认编码
        configuration.setDefaultEncoding("UTF-8");

        // 设置模板所在文件夹
        configuration.setDirectoryForTemplateLoading(new File(RESOURCES_PATH + templateDirectory));

        // 生成模板对象
        Template template = configuration.getTemplate(templateFileName);

        // 将datas写入模板并返回
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(datas, stringWriter);
            stringWriter.flush();
            return stringWriter.getBuffer().toString();
        }
    }
}