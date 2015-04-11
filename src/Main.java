import Data.Channel;
import Data.DataFrame;
import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
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
            DataFrame mainsDataFrame = mains.read(null, 1303130596);
            ArrayList<Window> mainsWindows = WindowExtractor.detectWindows(mainsDataFrame, 10.0);
            System.out.println("Mains windows: " + mainsWindows.size());
            for (Iterator<Window> iterator = mainsWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

            // Read bathroom channel
            Channel bathroom = house.getChannel("bathroom");
            DataFrame bathroomDataFrame = bathroom.read(null, 1303130596);
            ArrayList<Window> bathroomWindwos = WindowExtractor.detectWindows(bathroomDataFrame, 10.0);
            System.out.println("Bathroom windows: " + bathroomWindwos.size());

            for (Iterator<Window> iterator = bathroomWindwos.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
