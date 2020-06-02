package com.ls.zy.wordreader.controller;

import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.enums.EchartsType;
import com.ls.zy.wordreader.service.EchartService;
import com.ls.zy.wordreader.utils.EchartsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("echart")
public class EchartController {

    @Autowired
    EchartService echartService;

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    @GetMapping("getAllAdjustmentReason")
    public String generateElectricityTrend(Option electricityTrendOption, String outputDir){
        return echartService.generateElectricityTrend(electricityTrendOption,outputDir);
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    @GetMapping("getAllAdjustmentReason")
    public String generateLoadLineOption(Option loadLineOption,String outputDir){
        return echartService.generateLoadLineOption(loadLineOption,outputDir);
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    @GetMapping("getAllAdjustmentReason")
    public String generatePowerFactorTrend(Option powerFactorTrendOption,String outputDir){
        return echartService.generatePowerFactorTrend(powerFactorTrendOption,outputDir);
    }

}
