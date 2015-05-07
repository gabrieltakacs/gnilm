package EnergyCalculator;

import Data.Channel;
import Utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class EnergyCalculator {

    public static Double calculateConsumption(Channel channel, Date timeFrom, Date timeTo) {
        Double totalConsumption = 0.0;

        Map<Integer, Double> data = channel.getReconstructedConsumption();
        for (Map.Entry<Integer, Double> row : data.entrySet()) {
            Integer currentTimestamp = row.getKey();
            Double currentValue = row.getValue();

            if (timeFrom != null || timeTo != null) {
                Date date = TimeUtils.convertTimestampSecondsToDate(currentTimestamp);

                if (timeFrom != null) {
                    if (TimeUtils.compareTwoTimes(date, timeFrom) == TimeUtils.LOWER) {
                        continue;
                    }
                }

                if (timeTo != null) {
                    if (TimeUtils.compareTwoTimes(date, timeTo) == TimeUtils.GREATER) {
                        continue;
                    }
                }
            }

            if (currentValue > 0) {
                totalConsumption += currentValue;
            }
        }

        totalConsumption /= 3600000.0; // Conversion from Ws to kWh

        return totalConsumption;
    }

}
