import Data.Channel;
import Data.DataFrame;
import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
import Processor.Dtw;
import Processor.Window;
import Processor.WindowExtractor;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Mar 2015
 */
public class Main {

    public static void main(String[] args) {
        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house1");

            // Read mains channel
            Channel mains = house.getChannel("mains");
            DataFrame mainsDataFrame = mains.read(null, 1303130730);
            ArrayList<Window> mainsWindows = WindowExtractor.detectWindows(mainsDataFrame, 10.0);
            System.out.println("Mains windows: " + mainsWindows.size());
            for (Iterator<Window> iterator = mainsWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

            // Read bathroom channel
            Channel bathroom = house.getChannel("bathroom");
            DataFrame bathroomDataFrame = bathroom.read(null, 1303130730);
            ArrayList<Window> bathroomWindows = WindowExtractor.detectWindows(bathroomDataFrame, 10.0);
            System.out.println("Bathroom windows: " + bathroomWindows.size());
            for (Iterator<Window> iterator = bathroomWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

            // Lighting
            Channel lighting = house.getChannel("lighting");
            DataFrame lightingDataFrame = lighting.read(null, 1303130730);
            ArrayList<Window> lightingWindows = WindowExtractor.detectWindows(lightingDataFrame, 10.0);
            System.out.println("Lighting windows: " + lightingWindows.size());
            for (Iterator<Window> iterator = lightingWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

            // Kitchen
            Channel kitchen = house.getChannel("kitchen");
            DataFrame kitchenDataFrame = kitchen.read(null, 1303130730);
            ArrayList<Window> kitchenWindows = WindowExtractor.detectWindows(kitchenDataFrame, 10.0);
            System.out.println("Kitchen windows: " + kitchenWindows.size());
            for (Iterator<Window> iterator = kitchenWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

            Dtw dtwProcessor1 = new Dtw();
            Double score = dtwProcessor1.calculateDistance(mainsWindows.get(2), lightingWindows.get(0));
            System.out.println("DTW score (M, B): " + score);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
