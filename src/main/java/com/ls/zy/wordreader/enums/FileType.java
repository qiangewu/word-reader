package com.ls.zy.wordreader.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum FileType {
    DOC("1","DOC"),
    DOCX("2","DOCX"),
    TEXT("3","TEXT"),
    PDF("4","PDF");

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
