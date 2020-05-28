package com.ls.zy.wordreader.handlers;

import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.enums.EchartsType;
import com.ls.zy.wordreader.utils.EchartsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成Echarts对应图表
 */
public class EchartGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EchartGenerator.class);

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    public static String generateElectricityTrend(Option electricityTrendOption,String outputDir){
        String path = null;
        if(electricityTrendOption!=null){
            path = EchartsUtil.generateEchartsPicture(electricityTrendOption, EchartsType.HISTOGRAM, outputDir);
        }
        return path;
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    public static String generateLoadLineOption(Option loadLineOption,String outputDir){
        String path = null;
        if(loadLineOption!=null){
            path = EchartsUtil.generateEchartsPicture(loadLineOption, EchartsType.SMOOTH_LINE, outputDir);
        }
        return path;
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    public static String generatePowerFactorTrend(Option powerFactorTrendOption,String outputDir){
        String path = null;
        if(powerFactorTrendOption!=null){
            path = EchartsUtil.generateEchartsPicture(powerFactorTrendOption, EchartsType.SMOOTH_LINE, outputDir);
        }
        return path;
    }

//    public static void main(String[] args) {
//        Option powerFactorTrendOption = SimulateDateHandler.initPowerFactorTrendOption();
//        generatePowerFactorTrend(powerFactorTrendOption,"C:\\Users\\zhangyang\\Desktop\\temp");
//    }


}