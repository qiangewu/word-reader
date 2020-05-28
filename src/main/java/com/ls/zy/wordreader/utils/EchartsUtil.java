package com.ls.zy.wordreader.utils;

import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.entity.echarts.SeriesItem;
import com.ls.zy.wordreader.enums.EchartsType;
import com.ls.zy.wordreader.enums.SystemEnv;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * echarts图表图片生成
 */
public class EchartsUtil {

    private static final String ECHARTS_CONVERT_PATH = EchartsUtil.class.getClassLoader().getResource("").getPath() + "static/js/echarts-convert.js";
    private static final String SYSTEM_ENV = PropertiesUtil.readProperties().getSystemEnv();
    private static final String PHANTOMJS_PATH =PropertiesUtil.readProperties().getPhantomjsPath();
//    private static final String TEMP_DIR =PropertiesUtil.readProperties().getTempPath();

    static Logger logger = LoggerFactory.getLogger(EchartsUtil.class);

    /**
     * 生成option对应图表
     * @param option
     * @param echartsType  需要生成的模版类型（待拓展）
     * @return 图片路径
     */
    public static String generateEchartsPicture(Option option, EchartsType echartsType,String outputDir){
        String optionJson = null;
        if(echartsType.equals(EchartsType.HISTOGRAM)){
            optionJson = generateHistogramOption(option);
        }
        if(echartsType.equals(EchartsType.SMOOTH_LINE)){
            optionJson = generateSmoothLineOption(option);
        }
        if(StringUtil.isBlank((optionJson))){
            logger.error("图表生成失败，请检查相应参数");
            return null;
        }
        return toEChartsPicture(optionJson,outputDir);
    }

    /**
     * 生成option对应图表
     * @param items   对照组名称，String[] 必填
     * @param colors   对照组颜色，String[] 非必填 提供三组不同颜色的对照组
     * @param xRanges   x轴刻度，String[] 必填
     * @param datas   对照组对应数据，int[][] 必填
     * @param echartsType  需要生成的模版类型（待拓展）
     * @return 图片路径
     */
    public static String generateEchartsPicture(String[] items,String[] colors,String[] xRanges,double[][] datas, String unit, EchartsType echartsType,String outputDir){
        String optionJson = null;
        if(echartsType.equals(EchartsType.HISTOGRAM)){
            optionJson = generateHistogramOption(items,colors,xRanges,datas,unit);
        }
        if(echartsType.equals(EchartsType.SMOOTH_LINE)){
            optionJson = generateSmoothLineOption(items,colors,xRanges,datas,unit);
        }
        if(StringUtil.isBlank((optionJson))){
            logger.error("图表生成失败，请检查相应参数");
            return null;
        }
        return toEChartsPicture(optionJson, outputDir);
    }

    /**
     * 生成柱状图option
     * @param items   对照组名称，String[]
     *                * @param colors   对照组颜色，String[] 非必填 提供三组不同颜色的对照组
     * @param xRanges   x轴刻度，String[]
     * @param datas   对照组对应数据，int[][]
     * @return
     */
    static String generateHistogramOption(String[] items,String[] colors,String[] xRanges,double[][] datas, String unit){
        HashMap<String, Object> resultMap = generateResultMap(items,colors,xRanges,datas,unit);
        String option = null;
        try {
            option = FreemarkerUtil.generateString("histogram.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return option;
        }
    }

    /**
     * 生成柱状图option
     * 当前模板只支持三组的曲线颜色，再多需要自定义
     * @return
     */
    static String generateHistogramOption(Option option){
        HashMap<String, Object> resultMap = generateResultMap(option);
        String optionJson = null;
        try {
            optionJson = FreemarkerUtil.generateString("histogram.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return optionJson;
        }
    }



    /**
     * 生成平滑曲线图option
     * 当前模板只支持单个组的曲线，不支持多组对比
     * @param items   对照组名称，String[] 必填
     * @param colors   对照组颜色，String[] 非必填 提供三组不同颜色的对照组
     * @param xRanges   x轴刻度，String[] 必填
     * @param datas   对照组对应数据，int[][] 必填
     * @return
     */
    static String generateSmoothLineOption(String[] items,String[] colors,String[] xRanges,double[][] datas,String unit){
        HashMap<String, Object> resultMap = generateResultMap(items,colors,xRanges,datas,unit);
        String option = null;
        try {
            option = FreemarkerUtil.generateString("smooth-line.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return option;
        }
    }

    /**
     * 生成平滑曲线图option
     * 当前模板只支持三组的曲线颜色，再多需要自定义
     * @param option
     * @return
     */
    static String generateSmoothLineOption(Option option){
        HashMap<String, Object> resultMap = generateResultMap(option);
        String optionJson = null;
        try {
            optionJson = FreemarkerUtil.generateString("smooth-line.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return optionJson;
        }
    }

    /**
     * 根据输入信息处理为option可以识别的类型
     * @param items
     * @param colors
     * @param xRanges
     * @param datas
     * @param unit
     * @return
     */
    static HashMap<String, Object> generateResultMap(String[] items,String[] colors,String[] xRanges,double[][] datas, String unit){
        Map<String,String> seriesMap = new HashMap<>();
        for(int i = 0;i<items.length;i++){
            seriesMap.put(items[i],toDoubleArrayStr(datas[i]));
        }
        if(colors==null||colors.length<1){
            colors = new String[]{"#ff9900", "#00cc66", "#1495eb"};
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("items", items);
        resultMap.put("colors", toStringArrayStr(colors));
        resultMap.put("xRanges",  toStringArrayStr(xRanges));
        resultMap.put("seriesMap", seriesMap);
        resultMap.put("unit", "单位:  "+unit);
        return resultMap;
    }

    /**
     * 根据输入信息处理为option可以识别的类型
     * @param option
     * @return
     */
    static HashMap<String, Object> generateResultMap(Option option){
        Map<String,String> seriesMap = new HashMap<>();
        List<SeriesItem> seriesItems = option.getSeriesItemList();
        String[] colors = new String[seriesItems.size()];
        String[] titles = new String[seriesItems.size()];
        for(int i=0;i<seriesItems.size();i++){
            seriesMap.put(seriesItems.get(i).getItemName(),toDoubleArrayStr(seriesItems.get(i).getData()));
            titles[i] = seriesItems.get(i).getItemName();
            colors[i] = seriesItems.get(i).getColor();
        }
        if(!isLegalColors(colors)){
            colors = new String[]{"#ff9900", "#00cc66", "#1495eb"};
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("items", titles);
        resultMap.put("colors", toStringArrayStr(colors));
        resultMap.put("xRanges",  toStringArrayStr(option.getXRange()));
        resultMap.put("seriesMap", seriesMap);
        resultMap.put("unit", "单位:  "+option.getUnit());
        return resultMap;
    }

    /**
     * 是否是echarts可以识别的颜色
     * false表示不合法
     * @param colors
     * @return
     */
    static boolean isLegalColors(String[] colors){
        if(colors==null||colors.length<1){
            return false;
        }
        for(String color:colors){
            if(StringUtil.isBlank(color)||!color.startsWith("#")){
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符型数组转化为','分割的字符串
     * @return
     */
    static String toStringArrayStr(String[] array){
        if(array==null||array.length<1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String str:array){
            sb.append("\""+str+"\""+",");
        }
        return sb.substring(0,sb.length()-1);
    }

    /**
     * 将int型数组转化为','分割的字符串
     * @return
     */
    static String toDoubleArrayStr(double[] array){
        if(array==null||array.length<1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(double str:array){
            sb.append(str+",");
        }
        return sb.substring(0,sb.length()-1);
    }

    /**
     * phantomjs根据option生成图表图片
     * @param option
     * @return
     */
    static String toEChartsPicture(String option,String outputDir) {
        //.JSON文件路径，用于读取生成echarts图片
        String dataPath = writeFile(option, outputDir);
        String fileName= "echarts-"+ UUID.randomUUID().toString().substring(0, 8) + ".png";
        String path = outputDir+ File.separator +fileName;
        try {
            File file = new File(path);     //文件路径（路径+文件名）
            if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            //处理不同系统环境下echartsConvertJs路径及cmd命令
            String echartsConvertPath = ECHARTS_CONVERT_PATH;
            if(SystemEnv.WINDOWS.getType().equals(SYSTEM_ENV)){
                echartsConvertPath = echartsConvertPath.substring(1);
            }
            String cmd = PHANTOMJS_PATH + " " + echartsConvertPath + " -infile " + dataPath + " -outfile " + path;
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                logger.info(line);
            }
            input.close();
            process.destroy();
            FileUtil.deleteFile(dataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return path;
        }
    }

    /**
     * 新建临时的echarts option.json文件
     * 用于phantomjs生成图片
     * @param options
     * @return
     */
    public static String writeFile(String options,String outputDir) {
        String dataPath= outputDir+ File.separator + UUID.randomUUID().toString().substring(0, 8) +".json";
        try {
            /* 写入Txt文件 */
            File writename = new File(dataPath); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writename.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(writename.getParent());
                dir.mkdirs();
                writename.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(options); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPath;
    }

}
