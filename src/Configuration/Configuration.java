package Configuration;

public class Configuration {

    private static Configuration instance = null;

    private Double deltaBoostConstant = 10.0;

    private Double defaultEventDetectionThreshold = 10.0;

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
}
