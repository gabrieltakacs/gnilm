package Recommender;

import Configuration.Configuration;
import Controllers.MainController;
import Data.Channel;
import EnergyCalculator.EnergyCalculator;
import Utils.TimeUtils;

import java.text.DecimalFormat;
import java.util.Date;

public class Recommender {

    private Date highTariffZoneFrom;
    private Date highTariffZoneUntil;

    private MainController controller;

    public Recommender() {
        Configuration configuration = Configuration.getInstance();

        this.highTariffZoneFrom = TimeUtils.convertHoursStringToDate(configuration.getHighTariffZoneFrom());
        this.highTariffZoneUntil = TimeUtils.convertHoursStringToDate(configuration.getHighTariffZoneUntil());
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void generateRecommendations(Channel channel) {
        Double totalConsumption = EnergyCalculator.calculateConsumption(channel, null, null);
        Double highTariffConsumption = EnergyCalculator.calculateConsumption(channel, this.highTariffZoneFrom, this.highTariffZoneUntil);

        if (totalConsumption > 0.005) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            this.controller.addLineToEnergyCalculatorOutput("Kanal " + channel.getName() + " spotreboval " + decimalFormat.format(highTariffConsumption)  + "kWh z celkovej spotreby " + decimalFormat.format(totalConsumption) + "kWh vo vysokej tarife.", false);
        }
    }


}
