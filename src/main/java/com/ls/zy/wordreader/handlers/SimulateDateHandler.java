package com.ls.zy.wordreader.handlers;

import com.ls.zy.wordreader.entity.BuildAnalysisTemplate;
import com.ls.zy.wordreader.entity.EnergyUsage;
import com.ls.zy.wordreader.entity.echarts.Option;
import com.ls.zy.wordreader.entity.echarts.SeriesItem;
import com.ls.zy.wordreader.enums.IntentionStatus;
import com.ls.zy.wordreader.enums.LoadLevel;
import com.ls.zy.wordreader.enums.ProductionType;
import com.ls.zy.wordreader.enums.UnitType;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟数据
 * 测试使用
 * 可参考作为模板
 */
public class SimulateDateHandler {

    public static String[] X_RANGES = new String[] {"6月1日", "4日", "7日", "10日", "13日", "16日", "19日", "22日", "25日", "28日", "30日"};

    /**
     * 模拟
     * 楼宇潜力客户用能初步分析报告模板
     * 数据
     * @return
     */
    public static BuildAnalysisTemplate inintBuildAnalysisTemplate(){
        BuildAnalysisTemplate template = new BuildAnalysisTemplate();
        template.setDoorNo("0077723286");
        template.setUserName("上海浦东大酒店");
        template.setUserType("");
        template.setBuildingType(null);
        template.setTotalCapacity("1234567");
        template.setArea("上海");
        template.setServeTelNum("0510-xxxxx");
//        //echarts 柱状图 峰谷平电量（按月）趋势
//        template.setElectricityTrend(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        Option electricityTrendOption = initElectricityTrendOption();
        template.setElectricityTrendOption(electricityTrendOption);

        template.setElectricQuantityTotal("49207504");
        template.setElectricQuantityPeak("1863771");
        template.setElectricQuantityPeakRatio("3.79%");
        template.setElectricQuantityTrough("1701008");
        template.setElectricQuantityTroughRatio("3.46%");
        template.setElectricQuantityAverage("45642725");
        template.setElectricQuantityAverageRatio("92.76%");
        template.setElectricityTrendAnalysis("本期，该用户单位能耗为XXX，在同类别楼宇中属于平均偏向水平。总用电量为49207504kWh。" +
                "其中峰时用电1863771kWh，占比3.79%，平时用电45642725kWh，占比92.76%，谷时用电1701008kWh，占比23.46%。从用电量方面来看，" +
                "峰时用电占比不是较高的，该用户可以合理避开高峰用电。");
//        //echarts 平滑曲线图 月均负荷曲线
//        template.setLoadLine(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        Option loadLineOption = initLoadLineOption();
        template.setLoadLineOption(loadLineOption);

        template.setLoadAverage("20545.38");
        template.setLoadMaxValue("25452");
        template.setLoadMaxTime("2019-06-28 23:29:20");
        template.setLoadMinValue("17256");
        template.setLoadMinTime("17256");
        template.setLoadAnalysis("本期，最大负荷25452kW，发生时间2019-06-28 23:29:20，最小负荷17256kW，" +
                "发生时间2019-06-14 05:30:50，平均负荷20545.38kW。负载率大于30%且低于80%，用电负荷合理，" +
                "不存在 “大马拉小车”或“小马拉大车”现象。");
//        //echarts 平滑曲线图 总体功率因数趋势
//        template.setPowerFactorTrend(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        Option powerFactorTrendOption = initPowerFactorTrendOption();
        template.setPowerFactorTrendOption(powerFactorTrendOption);


        template.setPowerElectric("49207680");
        template.setReactiveElectric("13804800");
        template.setPowerFactor("0.9628");
        template.setAssessmentCriterion("0.9");
        template.setCoefficient("0%");
        template.setPowerFactorAnalysis("本期，该用户总体功率因数为0.9628，高于考核标准值0.9，符合无功补偿的要求。" +
                "建议该变电站继续加强无功管理，及时做好电容装置的运行维护工作。");

//        //html table图表 主要用能情况表
//        template.setPowerUsage(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        List<EnergyUsage> energyUsageList = initEnergyUsageList();
        template.setEnergyUsageList(energyUsageList);

        template.setEnergyConsumptionDiagnosis("本期，该用户平均单位面积能耗为XX，远低于同类——星级酒店的标准，存在较大的改进空间。");
        template.setElectricDiagnosis("本期，该用户总用电量为49207504kWh。其中峰时用电1863771kWh，占比3.79%，平时用电45642725kWh，占比92.76%，" +
                "谷时用电1701008kWh，占比3.46%。从用电量方面来看，峰时用电占比不是最高的，该用户已经合理避开高峰用电。");
        template.setLoadDiagnosis("本期，变压器的平均负载率为XX，变压主变存在“大马拉小车”现象。");
        template.setPowerFactorDiagnosis("该用户总体功率因数低于考核标准值，建议该用户增加无功管理，使功率因数达到标准，以控制力调电费。");
        return template;
    }

    //模拟 echarts 柱状图 峰谷平电量（按月）趋势
    public static Option initElectricityTrendOption(){
        Option electricityTrendOption = new Option();
        electricityTrendOption.setXRange(X_RANGES);
        electricityTrendOption.setUnit(UnitType.KWH.getDesc());
        ArrayList<SeriesItem> electricityTrendItems = new ArrayList<>();
        electricityTrendItems.add(new SeriesItem("峰电量","",new double[]{10000000,2,3,4,5}));
        electricityTrendItems.add(new SeriesItem("谷电量","",new double[]{10000000,2,3,20000000,5}));
        electricityTrendItems.add(new SeriesItem("平电量","",new double[]{10000000,2,3,4,40000000}));
        electricityTrendOption.setSeriesItemList(electricityTrendItems);
        return electricityTrendOption;
    }

    //echarts 平滑曲线图 月均负荷曲线
    public static Option initLoadLineOption(){
        Option loadLineOption = new Option();
        loadLineOption.setXRange(X_RANGES);
        loadLineOption.setUnit(UnitType.KW.getDesc());
        ArrayList<SeriesItem> loadLineItems = new ArrayList<>();
        loadLineItems.add(new SeriesItem("有功功率|晶体 三厂","",new double[]{21000,12000,14000,35000,12000,15000,17000,21000,12000,14000,35000}));
        loadLineOption.setSeriesItemList(loadLineItems);
        return loadLineOption;
    }

    //echarts 平滑曲线图 总体功率因数趋势
    public static Option initPowerFactorTrendOption(){
        Option powerFactorTrendOption = new Option();
        powerFactorTrendOption.setXRange(X_RANGES);
        powerFactorTrendOption.setUnit(UnitType.KW.getDesc());
        ArrayList<SeriesItem> powerFactorTrendItems = new ArrayList<>();
        powerFactorTrendItems.add(new SeriesItem("月功率因数","",new double[]{0.20,0.56,0.78,0.82,0.9,0.92,0.6,0.82}));
        powerFactorTrendOption.setSeriesItemList(powerFactorTrendItems);
        return powerFactorTrendOption;
    }

    public static List<EnergyUsage> initEnergyUsageList(){
        List<EnergyUsage> energyUsageList = new ArrayList<>();
        EnergyUsage usage1 = initEnergyUsage();
        usage1.setLoadLevel(LoadLevel.ONE.getType());
        usage1.setProductionType(ProductionType.SECURITY_ASSURANCE.getType());
        usage1.setReplacementIntention(IntentionStatus.HAVE.getType());
        EnergyUsage usage2 = initEnergyUsage();
        usage2.setLoadLevel(LoadLevel.TWO.getType());
        usage2.setProductionType(ProductionType.MAIN_PRODUCTION.getType());
        usage2.setReplacementIntention(IntentionStatus.HAVE.getType());
        EnergyUsage usage3 = initEnergyUsage();
        usage3.setLoadLevel(LoadLevel.TREE.getType());
        usage3.setProductionType(ProductionType.SUPPORTING_PRODUCTION.getType());
        usage3.setReplacementIntention(IntentionStatus.NONE.getType());
        EnergyUsage usage4 = initEnergyUsage();
        usage4.setEnergyType("用能类型4");
        usage4.setLoadLevel(LoadLevel.ONE.getType());
        usage4.setProductionType(ProductionType.NONPRODUCTIVE.getType());
        usage4.setReplacementIntention(IntentionStatus.NONE.getType());
        EnergyUsage usage5 = initEnergyUsage();
        energyUsageList.add(usage1);
        energyUsageList.add(usage2);
        energyUsageList.add(usage3);
        energyUsageList.add(usage4);
        energyUsageList.add(usage5);
        return energyUsageList;
    }


    public static EnergyUsage initEnergyUsage(){
        EnergyUsage usage = new EnergyUsage();
        usage.setDeviceName("testDevice");
        usage.setDeviceNum(1200);
        usage.setEnergyType("用能类型1");
        usage.setExpectedCapacity(123000);
        usage.setTotalCapacity(230000);
        usage.setLoadLevel(LoadLevel.ONE.getType());
        usage.setProductionType(ProductionType.SUPPORTING_PRODUCTION.getType());
        usage.setReplacementIntention(IntentionStatus.HAVE.getType());
        return usage;
    }

}
