package com.ls.zy.wordreader.service;

import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.handlers.EchartGenerator;
import com.ls.zy.wordreader.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生成Echarts对应图表
 */
@Service
public class EchartService {

    private final Logger logger = LoggerFactory.getLogger(EchartService.class);

    @Autowired
    FileMongodbService fileMongodbService;

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    public String generateElectricityTrend(Option electricityTrendOption,String outputDir){
        String path = EchartGenerator.generateElectricityTrend(electricityTrendOption,outputDir);
        String fileKey = fileMongodbService.simpleUploadPicture(path);
        FileUtil.deleteAllSafely(path);
        return  fileKey;
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    public String generateLoadLineOption(Option loadLineOption,String outputDir){
        String path =  EchartGenerator.generateLoadLineOption(loadLineOption,outputDir);
        String fileKey = fileMongodbService.simpleUploadPicture(path);
        FileUtil.deleteAllSafely(path);
        return  fileKey;
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    public String generatePowerFactorTrend(Option powerFactorTrendOption,String outputDir){
        String path =  EchartGenerator.generatePowerFactorTrend(powerFactorTrendOption,outputDir);
        String fileKey = fileMongodbService.simpleUploadPicture(path);
        FileUtil.deleteAllSafely(path);
        return  fileKey;
    }


}