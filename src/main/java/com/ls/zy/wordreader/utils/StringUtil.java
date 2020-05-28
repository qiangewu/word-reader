package com.ls.zy.wordreader.utils;

import org.springframework.util.StringUtils;

public class StringUtil {

    /**
     * 判断字符串是否为空或者空字符串
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        if(StringUtils.isEmpty(str)){
            return true;
        }
        if(StringUtils.isEmpty(str.trim())){
            return true;
        }
        return false;
    }

}
