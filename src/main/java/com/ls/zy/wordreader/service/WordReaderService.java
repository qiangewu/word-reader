package com.ls.zy.wordreader.service;

import com.ls.zy.wordreader.entity.BuildAnalysisTemplate;
import com.ls.zy.wordreader.handlers.BuildTemplateWordHandler;
import com.ls.zy.wordreader.handlers.EchartGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordReaderService {

    private final Logger logger = LoggerFactory.getLogger(WordReaderService.class);


    public void generateNewWord(BuildAnalysisTemplate buildAnalysisTemplate){
        BuildTemplateWordHandler.generateNewWord(buildAnalysisTemplate);
    }

}
