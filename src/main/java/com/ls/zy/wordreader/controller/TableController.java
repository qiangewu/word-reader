package com.ls.zy.wordreader.controller;

import com.ls.zy.wordreader.config.GlobalConfig;
import com.ls.zy.wordreader.entity.EnergyUsage;
import com.ls.zy.wordreader.handlers.TableGenerator;
import com.ls.zy.wordreader.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 生成对应table图片
 */
@RestController
@RequestMapping("table")
public class TableController {

    @Autowired
    TableService tableService;

    @Autowired
    GlobalConfig globalConfig;

    /**
     * 主要用能情况表
     */
    @GetMapping("generateEnergyUsageTable")
    public String generateEnergyUsageTable(List<EnergyUsage> energyUsageList){
        return tableService.generateEnergyUsageTable(energyUsageList,globalConfig.getTempDir());
    }

}
