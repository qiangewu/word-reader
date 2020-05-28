package com.ls.zy.wordreader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用能情况
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyUsage {

    /**
     * 设备名称
     */
    String deviceName;

    /**
     * 设备数量
     */
    Integer deviceNum;

    /**
     * 用能类型
     */
    String energyType;

    /**
     * 预计年均容量
     */
    Integer expectedCapacity;

    /**
     * 总用电容量
     */
    Integer totalCapacity;

    /**
     * 用电负荷等级
     */
    Integer loadLevel;

    /**
     * 生产性质
     */
    Integer productionType;

    /**
     * 跟换意向
     */
    Integer replacementIntention;

}
