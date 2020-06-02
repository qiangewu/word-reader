package com.ls.zy.wordreader.service;

import com.ls.zy.wordreader.entity.EnergyUsage;
import com.ls.zy.wordreader.handlers.TableGenerator;
import com.ls.zy.wordreader.utils.FileUtil;
import com.ls.zy.wordreader.utils.HtmlTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成对应table图片
 */
@Service
public class TableService {

    @Autowired
    FileMongodbService fileMongodbService;

    /**
     * 主要用能情况表
     */
    public String generateEnergyUsageTable(List<EnergyUsage> energyUsageList,String outputDir){
        String path = TableGenerator.generateEnergyUsageTable(energyUsageList,outputDir);
        String fileKey = fileMongodbService.simpleUploadPicture(path);
        FileUtil.deleteAllSafely(path);
        return  fileKey;
    }

}
