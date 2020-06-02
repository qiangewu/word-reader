package com.ls.zy.wordreader.service;

import com.ls.zy.wordreader.entity.BuildAnalysisTemplate;
import com.ls.zy.wordreader.handlers.BuildTemplateWordHandler;
import com.ls.zy.wordreader.handlers.EchartGenerator;
import com.ls.zy.wordreader.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordReaderService {

    private final Logger logger = LoggerFactory.getLogger(WordReaderService.class);

    @Autowired
    FileMongodbService fileMongodbService;

    public String generateNewWord(BuildAnalysisTemplate buildAnalysisTemplate,String outputDir){
        String path = BuildTemplateWordHandler.generateNewWord(buildAnalysisTemplate,outputDir);
        String fileKey = fileMongodbService.simpleUploadPicture(path);
        FileUtil.deleteAllSafely(path);
        return  fileKey;
    }

}
