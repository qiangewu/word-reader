package com.ls.zy.wordreader.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 更换意向类型
 */
@NoArgsConstructor
@AllArgsConstructor
public enum IntentionStatus {

    HAVE(1,"有"),
    NONE(2,"无");

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
