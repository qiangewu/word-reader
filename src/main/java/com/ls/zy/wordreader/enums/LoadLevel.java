package com.ls.zy.wordreader.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 用电负荷等级
 */
@NoArgsConstructor
@AllArgsConstructor
public enum LoadLevel {
    ONE(1,"一级"),
    TWO(2,"二级"),
    TREE(3,"三级");

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}