import Controllers.MainController;
import Views.MainView;

public class Main {

    public static void main(String[] args) {

        MainView mainView = new MainView();
        new MainController(mainView);

        /*
        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house2");

            // Safe: 1303129329, 1303136529
            // Zla detekcia (house2): 1303107507, 1303111107
            Processor processor = new Processor();
            processor.setTrainDataRange(1303082307, 1303709500); // The first week (1303082307, 1303709500)
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

            Channel theChannel = house.getChannel("dishwasher");

            Recommender recommender = new Recommender();
            recommender.generateRecommendations(theChannel);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */
    }

}
