package com.ls.zy.wordreader.entity.echarts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Echarts中对照组的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesItem {

    String itemName;

    String color;

    double[] data;

}
