package Controllers.Listeners;

import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
import java.awt.event.ActionEvent;
import Processor.Processor;

public class RunActionListener extends ActionListenerGeneral {

    @Override
    public void actionPerformed(ActionEvent e) {
        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house2");

            Processor processor = new Processor(this.controller);
            processor.setTrainDataRange(1303082307, 1303709500);
            processor.setTestDataRange(1303139500, 1303140500);
            processor.setHouse(house);
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
