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
            processor.setTrainDataRange(1303129329, 1303734129);
            processor.setTestDataRange(1303137130, 1303137730);
            processor.setHouse(house);
            processor.addTrainDataChannel("lighting");
            processor.addTrainDataChannel("refridgerator");
            processor.addTrainDataChannel("bathroom");
            processor.addTrainDataChannel("microwave");
            processor.detectEvents();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
