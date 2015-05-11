package Configuration;

import java.util.HashMap;

public class Configuration {

    private static Configuration instance = null;

    private Double deltaBoostConstant = 10.0;

    private Double defaultEventDetectionThreshold = 10.0;

    private String inputDirectory;

    private Integer trainDataRangeFrom = 1303082307;
    private Integer trainDataRangeUntil = 1303709500;

    private Integer testDataRangeFrom = 1303139500;
    private Integer testDataRangeUntil = 1303140500;


    private String highTariffZoneFrom = "6:00";
    private String highTariffZoneUntil = "22:00";

    private HashMap<String, Double> thresholds;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();

            // Initialize thresholds
            instance.thresholds = new HashMap<String, Double>();
            instance.initThresholds();
        }

        return instance;
    }

    private void initThresholds() {
        this.setThreshold("mains", 10.0);
    }

    public void setThreshold(String channel, Double value) {
        this.thresholds.put(channel, value);
    }

    public Double getThreshold(String channel) {
        Double threshold =  this.thresholds.get(channel);
        if (threshold == null) {
            return this.defaultEventDetectionThreshold;
        }
        return threshold;
    }

    public Double getDeltaBoostConstant() {
        return deltaBoostConstant;
    }

    public void setDeltaBoostConstant(Double deltaBoostConstant) {
        this.deltaBoostConstant = deltaBoostConstant;
    }

    public Double getDefaultEventDetectionThreshold() {
        return defaultEventDetectionThreshold;
    }

    public void setDefaultEventDetectionThreshold(Double defaultEventDetectionThreshold) {
        this.defaultEventDetectionThreshold = defaultEventDetectionThreshold;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public Integer getTrainDataRangeFrom() {
        return trainDataRangeFrom;
    }

    public void setTrainDataRangeFrom(Integer trainDataRangeFrom) {
        this.trainDataRangeFrom = trainDataRangeFrom;
    }

    public Integer getTrainDataRangeUntil() {
        return trainDataRangeUntil;
    }

    public void setTrainDataRangeUntil(Integer trainDataRangeUntil) {
        this.trainDataRangeUntil = trainDataRangeUntil;
    }

    public Integer getTestDataRangeFrom() {
        return testDataRangeFrom;
    }

    public void setTestDataRangeFrom(Integer testDataRangeFrom) {
        this.testDataRangeFrom = testDataRangeFrom;
    }

    public Integer getTestDataRangeUntil() {
        return testDataRangeUntil;
    }

    public void setTestDataRangeUntil(Integer testDataRangeUntil) {
        this.testDataRangeUntil = testDataRangeUntil;
    }

    public String getHighTariffZoneFrom() {
        return highTariffZoneFrom;
    }

    public void setHighTariffZoneFrom(String highTariffZoneFrom) {
        this.highTariffZoneFrom = highTariffZoneFrom;
    }

    public String getHighTariffZoneUntil() {
        return highTariffZoneUntil;
    }

    public void setHighTariffZoneUntil(String highTariffZoneUntil) {
        this.highTariffZoneUntil = highTariffZoneUntil;
    }
}
