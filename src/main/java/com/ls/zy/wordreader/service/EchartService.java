package com.ls.zy.wordreader.service;

import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.enums.EchartsType;
import com.ls.zy.wordreader.handlers.EchartGenerator;
import com.ls.zy.wordreader.utils.EchartsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 生成Echarts对应图表
 */
@Service
public class EchartService {

    private final Logger logger = LoggerFactory.getLogger(EchartService.class);

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    public String generateElectricityTrend(Option electricityTrendOption,String outputDir){
        return EchartGenerator.generateElectricityTrend(electricityTrendOption,outputDir);
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    public String generateLoadLineOption(Option loadLineOption,String outputDir){
        return EchartGenerator.generateLoadLineOption(loadLineOption,outputDir);
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    public String generatePowerFactorTrend(Option powerFactorTrendOption,String outputDir){
        return EchartGenerator.generatePowerFactorTrend(powerFactorTrendOption,outputDir);
    }


}