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
            House house = dataLoader.getHouse("house1");

            // Safe: 1303129329, 1303136529
            Processor processor = new Processor();
            processor.setTrainDataRange(1303129329, 1303734129); // The first week
            processor.setTestDataRange(1303737430, 1303741030);
            processor.setHouse(house);
            processor.addTrainDataChannel(house.getChannel("lighting").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("refridgerator").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("bathroom").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("microwave").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("kitchen").setWindowThreshold(10.0));
            processor.detectEvents();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
