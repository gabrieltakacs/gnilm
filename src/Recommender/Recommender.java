package Recommender;

import Configuration.Configuration;
import Data.Channel;
import EnergyCalculator.EnergyCalculator;
import Utils.TimeUtils;
import java.util.Date;

public class Recommender {

    private Date highTariffZoneFrom;
    private Date highTariffZoneUntil;

    public Recommender() {
        this.highTariffZoneFrom = TimeUtils.convertHoursStringToDate(Configuration.highTariffZoneFrom);
        this.highTariffZoneUntil = TimeUtils.convertHoursStringToDate(Configuration.highTariffZoneUntil);
    }

    public void generateRecommendations(Channel channel) {
        Double totalConsumption = EnergyCalculator.calculateConsumption(channel, null, null);
        Double highTariffConsumption = EnergyCalculator.calculateConsumption(channel, this.highTariffZoneFrom, this.highTariffZoneUntil);

        System.out.println("Kanal " + channel.getName() + " spotreboval " + highTariffConsumption + " z celkovej spotreby " + totalConsumption + " vo vysokej tarife.\n");

    }


}
