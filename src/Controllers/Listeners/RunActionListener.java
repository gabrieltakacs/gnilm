package Controllers.Listeners;

import Configuration.Configuration;
import Data.DataFactory;
import Data.House;
import Processor.Processor;
import java.awt.event.ActionEvent;

public class RunActionListener extends ActionListenerAbstract {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            House house = DataFactory.getHouse(Configuration.getInstance().getInputDirectory());

            Processor processor = new Processor(this.controller);

            // TODO: toto treba presunut do processoru
            processor.addTrainDataChannel(house.getChannel("kitchen1").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("lighting").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("stove").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("microwave").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("washer").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("kitchen2").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("refridgerator").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("dishwasher").setWindowThreshold(10.0).setCurrentValue(1205.67));
            processor.addTrainDataChannel(house.getChannel("disposal").setWindowThreshold(10.0));
            processor.detectEvents();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
