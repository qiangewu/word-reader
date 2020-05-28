package com.ls.zy.wordreader.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 生产性质
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ProductionType {
    SECURITY_ASSURANCE(1,"安全保障"),
    MAIN_PRODUCTION(2,"主要生产"),
    SUPPORTING_PRODUCTION(3,"辅助生产"),
    NONPRODUCTIVE(4,"非生产性");

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
