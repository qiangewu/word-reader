package com.ls.zy.wordreader.entity.echarts;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Option {
    /**
     * 横坐标刻度
     */
    String[] xRange;

    /**
     * 单位
     */
    String unit;

    /**
     * 对照组信息
     */
    ArrayList<SeriesItem> seriesItemList;
}
