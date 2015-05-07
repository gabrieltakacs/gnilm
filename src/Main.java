
import Data.Channel;
import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
import EnergyCalculator.EnergyCalculator;
import Processor.Processor;
import Processor.Window;
import Recommender.Recommender;

/**
 * Gabriel Takács, Mar 2015
 */
public class Main {

    public static void main(String[] args) {

        /*
        Window windowA = new Window();
        windowA.addValue(1229.19);
        windowA.addValue(1211.02);
        windowA.addValue(1.2);
        windowA.addValue(0.03);
        windowA.addValue(0.05);
        windowA.addValue(0);

        Window windowB = new Window();
        windowB.addValue(1874.0);
        windowB.addValue(1259.0);
        windowB.addValue(644.0);
        windowB.addValue(29.0);
        windowB.addValue(28.25);
        windowB.addValue(27.5);
        windowB.addValue(26.75);

        Window windowC = new Window();
        windowC.addValue(1199.0);
        windowC.addValue(800.33);
        windowC.addValue(401.67);
        windowC.addValue(3.0);
        windowC.addValue(2.75);
        windowC.addValue(2.5);
        windowC.addValue(2.25);

        System.out.println("DIST (Mains - Microwave): " + windowA.calculateDistance(windowB));
        System.out.println("DIST (Mains - Dishwasher): " + windowA.calculateDistance(windowC));
        */


//        MainView mainView = new MainView();
//        new MainController(mainView);

        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house2");

            // Safe: 1303129329, 1303136529
            // Zla detekcia (house2): 1303107507, 1303111107
            Processor processor = new Processor();
            processor.setTrainDataRange(1303082307, 1303709500); // The first week (1303082307, 1303709500)
            processor.setTestDataRange(1303138500, 1303139000);
            processor.setHouse(house);
            processor.addTrainDataChannel(house.getChannel("kitchen1").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("lighting").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("stove").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("microwave").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("washer").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("kitchen2").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("refridgerator").setWindowThreshold(10.0));
            processor.addTrainDataChannel(house.getChannel("dishwasher").setWindowThreshold(10.0).setCurrentValue(1216.67));
            processor.addTrainDataChannel(house.getChannel("disposal").setWindowThreshold(10.0));

            processor.detectEvents();

            Channel theChannel = house.getChannel("dishwasher");

            Recommender recommender = new Recommender();
            recommender.generateRecommendations(theChannel);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
