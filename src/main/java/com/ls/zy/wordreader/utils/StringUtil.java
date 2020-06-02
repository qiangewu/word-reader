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

    /**
     * 类方法的详细使用说明_字符串判空
     *
     * @param value
     *            参数1的使用说明
     * @return 返回结果的说明
     *             注明从此类方法中抛出异常的说明
     */
    public static boolean isEmpty(String value) {
        if ((value == null) || (value.length() == 0)) {
            return true;
        }
        return false;
    }

}
