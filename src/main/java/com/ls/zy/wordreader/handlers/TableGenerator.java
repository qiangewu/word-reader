package com.ls.zy.wordreader.handlers;

import com.ls.zy.wordreader.entity.EnergyUsage;
import com.ls.zy.wordreader.utils.HtmlTableUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成对应table图片
 */
public class TableGenerator {

    /**
     * 主要用能情况表
     */
    public static String generateEnergyUsageTable(List<EnergyUsage> energyUsageList,String outputDir){
        String path = null;
        if(energyUsageList!=null){
            Map<String,Object> datas = new HashMap<>();
            datas.put("energyUsagaList",energyUsageList);
            path = HtmlTableUtil.generateTablePicture(datas,outputDir);
        }
        return path;
    }

}
