import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
import Processor.Processor;

/**
 * Gabriel Takács, Mar 2015
 */
public class Main {

    public static void main(String[] args) {
        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house2");

            // Safe: 1303129329, 1303136529
            // Zla detekcia (house2): 1303107507, 1303111107
            Processor processor = new Processor();
            processor.setTrainDataRange(1303082307, 1303168707); // The first week
            processor.setTestDataRange(1303111107, 1303114707);
            processor.setHouse(house);
            processor.addTrainDataChannel(house.getChannel("kitchen1").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("lighting").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("stove").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("microwave").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("washer").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("kitchen2").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("refridgerator").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("dishwasher").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("disposal").setWindowThreshold(10.0));

            processor.detectEvents();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
