package Configuration;

public class Configuration {

    private static Configuration instance = null;

    private Double deltaBoostConstant = 10.0;

    private Double defaultEventDetectionThreshold = 10.0;

    private String inputDirectory;

    private Integer trainDataRangeFrom = 1303082307;
    private Integer trainDataRangeUntil = 1303709500;

    private Integer testDataRangeFrom = 1303139500;
    private Integer testDataRangeUntil = 1303140500;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public static final String highTariffZoneFrom = "6:00";

    public static final String highTariffZoneUntil = "22:00";

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
}
