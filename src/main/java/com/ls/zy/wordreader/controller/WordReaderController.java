package com.ls.zy.wordreader.controller;

import com.ls.zy.wordreader.config.GlobalConfig;
import com.ls.zy.wordreader.entity.BuildAnalysisTemplate;
import com.ls.zy.wordreader.handlers.BuildTemplateWordHandler;
import com.ls.zy.wordreader.service.WordReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("word-reader")
public class WordReaderController {

    @Autowired
    WordReaderService wordReaderService;

    @Autowired
    GlobalConfig globalConfig;

    @GetMapping("generateNewWord")
    public String generateNewWord(BuildAnalysisTemplate buildAnalysisTemplate){
        return wordReaderService.generateNewWord(buildAnalysisTemplate,globalConfig.getTempDir());
    }

}
