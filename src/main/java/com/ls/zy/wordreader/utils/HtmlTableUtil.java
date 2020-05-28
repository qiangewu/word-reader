package com.ls.zy.wordreader.utils;

import freemarker.template.TemplateException;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 处理HTML静态页面生成Table对应的图片
 */
public class HtmlTableUtil {

    static Logger logger = LoggerFactory.getLogger(HtmlTableUtil.class);

    public static String generateTablePicture(Map<String,Object> datas) {
        String path = null;
        try {
            String imageHtml = null;
            imageHtml = FreemarkerUtil.generateString("energy-usaga.html", "/templates", datas);
            HtmlParser htmlParser = new HtmlParserImpl();
            htmlParser.loadHtml(imageHtml);
            ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
            path = "C:\\Users\\zhangyang\\Desktop\\temp\\test\\1.gif";
            imageRenderer.saveImage(path);
//            Thread.sleep(2000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            logger.error(e.toString());
        } catch (IOException e){
            e.printStackTrace();
            logger.error(e.toString());
        }catch (TemplateException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return path;
    }

}
