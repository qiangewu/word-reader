package com.ls.zy.wordreader.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Map;

public class FreemarkerUtil {

    static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

//    private static final String RESOURCES_PATH = FreemarkerUtil.class.getClassLoader().getResource("").getPath();

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
        //未打包使用
//        configuration.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:"+  templateDirectory));

        //打包采用静态方法,没有合适解决方式，本地Temp中新建，后删除
        String tempPath = PropertiesUtil.readProperties().getTempDir();
        String localTempFilePath = tempPath+File.separator+templateFileName;

//        InputStream ips = FreemarkerUtil.class.getClassLoader().getResourceAsStream(templateDirectory+File.separator+templateFileName);
//        InputStream ips = new FileInputStream(ResourceUtils.getFile("classpath:"+templateDirectory+File.separator+templateFileName));
        ClassPathResource resource = new ClassPathResource(templateDirectory+File.separator+templateFileName);
        InputStream ips = resource.getInputStream();
        FileUtil.writeToLocal(ips,localTempFilePath);

        // 设置模板所在文件夹
        configuration.setDirectoryForTemplateLoading(new File(tempPath));

        // 生成模板对象
        Template template = configuration.getTemplate(templateFileName);

        // 将datas写入模板并返回
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(datas, stringWriter);
            stringWriter.flush();
            try {
                FileUtil.deleteAllSafely(localTempFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringWriter.getBuffer().toString();
        }
    }
}