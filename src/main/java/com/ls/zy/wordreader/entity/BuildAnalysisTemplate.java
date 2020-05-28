package com.ls.zy.wordreader.entity;

import com.ls.zy.wordreader.entity.echarts.Option;
import lombok.Data;

import java.util.List;

/**
 * 对应楼宇潜力客户用能初步分析报告模板
 */
@Data
public class BuildAnalysisTemplate {
    /**
     * 户号
     */
    String doorNo;

    /**
     * 用户名称
     */
    String userName;

    /**
     * 用户分类
     */
    String userType;

    /**
     * 楼宇类型
     */
    String buildingType;

    /**
     * 总用电容量
     */
    String totalCapacity;

    /**
     * 用电地区
     */
    String area;

    /**
     * 服务热线
     */
    String serveTelNum;

    /**
     * 峰谷平电量（按月）趋势图路径
     * 自动生成，不需要填写
     */
    String electricityTrend;

    /**
     * 峰谷平电量统计
     * 总电量
     */
    String electricQuantityTotal;

    /**
     * 峰谷平电量统计
     * 峰电量
     */
    String electricQuantityPeak;

    /**
     * 峰谷平电量统计
     * 峰电量占比
     */
    String electricQuantityPeakRatio;

    /**
     * 峰谷平电量统计
     * 谷电量
     */
    String electricQuantityTrough;

    /**
     * 峰谷平电量统计
     * 峰电量占比
     */
    String electricQuantityTroughRatio;

    /**
     * 峰谷平电量统计
     * 平电量
     */
    String electricQuantityAverage;

    /**
     * 峰谷平电量统计
     * 平电量占比
     */
    String electricQuantityAverageRatio;

    /**
     * 峰谷平电量（按月）趋势图分析
     */
    String electricityTrendAnalysis;

    /**
     * 月均负荷曲线图路径
     * 自动生成，不需要填写
     */
    String loadLine;

    /**
     * 负荷统计平均值
     */
    String loadAverage;

    /**
     * 负荷统计最大值值
     * 17256.12
     */
    String loadMaxValue;

    /**
     * 最大值发生时间
     * 2019-06-28 23:29:2
     */
    String loadMaxTime;

    /**
     * 负荷统计最小值值
     * 17256.12
     */
    String loadMinValue;

    /**
     * 最小值发生时间
     * 2019-06-28 23:29:2
     */
    String loadMinTime;

    /**
     * 总体负荷统计分析
     */
    String loadAnalysis;

    /**
     * 总体功率因数趋势图路径
     * 自动生成，不需要填写
     */
    String powerFactorTrend;

    /**
     * 有功电量
     */
    String powerElectric;

    /**
     * 无功电量
     */
    String reactiveElectric;

    /**
     * 功率因数
     */
    String powerFactor;

    /**
     * 考核标准
     */
    String assessmentCriterion;

    /**
     * 奖惩系数
     */
    String coefficient;

    /**
     * 总体功率因数统计
     */
    String powerFactorAnalysis;

    /**
     * 用能情况表路径
     * 自动生成，不需要填写
     */
    String powerUsage;

    /**
     * 单位能耗对标诊断
     */
    String energyConsumptionDiagnosis;

    /**
     * 峰谷电诊断
     */
    String electricDiagnosis;

    /**
     * 变压器负载诊断
     */
    String loadDiagnosis;

    /**
     * 变压器负载诊断
     */
    String powerFactorDiagnosis;

    /**
     * echarts 柱状图 峰谷平电量（按月）趋势
     */
    Option electricityTrendOption;

    /**
     * echarts 平滑曲线图 月均负荷曲线
     */
    Option loadLineOption;

    /**
     * echarts 平滑曲线图 总体功率因数趋势
     */
    Option powerFactorTrendOption;

    /**
     * html table图表 主要用能情况数据
     */
    List<EnergyUsage> energyUsageList;


}
