package com.ls.zy.wordreader.controller;

import com.ls.zy.wordreader.config.GlobalConfig;
import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.enums.EchartsType;
import com.ls.zy.wordreader.service.EchartService;
import com.ls.zy.wordreader.service.FileMongodbService;
import com.ls.zy.wordreader.utils.EchartsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("echart")
public class EchartController {

    @Autowired
    EchartService echartService;

    @Autowired
    FileMongodbService fileMongodbService;

    @Autowired
    GlobalConfig globalConfig;

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    @GetMapping("generateElectricityTrend")
    public String generateElectricityTrend(Option electricityTrendOption){
        return echartService.generateElectricityTrend(electricityTrendOption,globalConfig.getTempDir());
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    @GetMapping("generateLoadLineOption")
    public String generateLoadLineOption(Option loadLineOption,String outputDir){
        return echartService.generateLoadLineOption(loadLineOption,globalConfig.getTempDir());
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    @GetMapping("generatePowerFactorTrend")
    public String generatePowerFactorTrend(Option powerFactorTrendOption,String outputDir){
        return echartService.generatePowerFactorTrend(powerFactorTrendOption,globalConfig.getTempDir());
    }

    @RequestMapping(value = "/downloadFile",method = RequestMethod.GET)
    public void downloadFile(String fileKey, HttpServletResponse response,HttpServletRequest httpServletRequest) throws Exception {
        fileMongodbService.downloadFile(fileKey,response);
    }

}
