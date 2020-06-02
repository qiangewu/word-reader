package com.ls.zy.wordreader.task;

import com.ls.zy.wordreader.config.GlobalConfig;
import com.ls.zy.wordreader.entity.BuildAnalysisTemplate;
import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.handlers.BuildTemplateWordHandler;
import com.ls.zy.wordreader.handlers.SimulateDateHandler;
import com.ls.zy.wordreader.service.EchartService;
import com.ls.zy.wordreader.service.FileMongodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.ls.zy.wordreader.handlers.EchartGenerator.generatePowerFactorTrend;

/**
 * 定时读取某路径下的doc文件模板并解析
 * 生成对应的doc文件
 */
@Component
public class ReaderDocTask {

    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    FileMongodbService fileMongodbService;

    @Autowired
    EchartService echartService;

    /**
     * 楼宇潜力客户用能初步分析报告模板
     */
    @PostConstruct
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void readerExcel(){

        //模拟数据，实际场景需要替换此处
//        BuildAnalysisTemplate buildAnalysisTemplate = SimulateDateHandler.inintBuildAnalysisTemplate();
//        BuildTemplateWordHandler.generateNewWord(buildAnalysisTemplate);

        Option powerFactorTrendOption = SimulateDateHandler.initPowerFactorTrendOption();
        System.out.println(echartService.generatePowerFactorTrend(powerFactorTrendOption,"C:\\Users\\zhangyang\\Desktop\\temp"));

    }



}
