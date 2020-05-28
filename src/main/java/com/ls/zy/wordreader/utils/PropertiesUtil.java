package com.ls.zy.wordreader.utils;


import com.ls.zy.wordreader.config.GlobalConfig;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class PropertiesUtil {

    public static GlobalConfig readProperties(){
        GlobalConfig globalConfig = new GlobalConfig();
        Properties p = new Properties();
        try {
            p.load(PropertiesUtil.class.getResourceAsStream("/global.properties"));
            for (Object key : p.keySet()) {
                if(key.toString().equals("tempDir")) {
                    globalConfig.setTempPath(p.get(key).toString());
                    continue;
                }
                if(key.toString().equals("templateDir")) {
                    globalConfig.setTemplateDir(p.get(key).toString());
                    continue;
                }
                if(key.toString().equals("templateResultDir")) {
                    globalConfig.setTemplateResultDir(p.get(key).toString());
                    continue;
                }
                if(key.toString().equals("systemEnv")) {
                    globalConfig.setSystemEnv(p.get(key).toString());
                    continue;
                }
                if(key.toString().equals("phantomjsPath")) {
                    globalConfig.setPhantomjsPath(p.get(key).toString());
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalConfig;
    }

}
