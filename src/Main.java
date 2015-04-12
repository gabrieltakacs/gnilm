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

            /*
             * Prepare training data
             */

            // Lighting
            Channel lighting = house.getChannel("lighting");
            DataFrame lightingDataFrame = lighting.read(null, 1303143733);
            ArrayList<Window> lightingWindows = WindowExtractor.detectWindows(lightingDataFrame, 10.0);
            System.out.println("Lighting windows: " + lightingWindows.size());
            for (Iterator<Window> iterator = lightingWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
//                currentWindow.printInfo();
            }

            // Refridgerator
            Channel refridgerator = house.getChannel("refridgerator");
            DataFrame refridgeratorDataFrame = refridgerator.read(null, 1303143733);
            ArrayList<Window> refridgeratorWindows = WindowExtractor.detectWindows(refridgeratorDataFrame, 10.0);
            System.out.println("Refridgerator windows: " + refridgeratorWindows.size());
            for (Iterator<Window> iterator = refridgeratorWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
//                currentWindow.printInfo();
            }

            // Bathroom
            Channel bathroom = house.getChannel("bathroom");
            DataFrame bathroomDataFrame = bathroom.read(null, 1303143733);
            ArrayList<Window> bathroomWindows = WindowExtractor.detectWindows(bathroomDataFrame, 10.0);
            System.out.println("Bathroom windows: " + bathroomWindows.size());
            for (Iterator<Window> iterator = bathroomWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
//                currentWindow.printInfo();
            }

            /*
             * Prepare test data
             */
            Channel mains = house.getChannel("mains");
            DataFrame mainsDataFrame = mains.read(null, 1303132929);
            ArrayList<Window> mainsWindows = WindowExtractor.detectWindows(mainsDataFrame, 10.0);
            System.out.println("Mains windows: " + mainsWindows.size());
            for (Iterator<Window> iterator = mainsWindows.iterator(); iterator.hasNext();) {
                Window currentWindow = iterator.next();
                currentWindow.printInfo();
                currentWindow.printData();

                // Porovnavam s lighting
                System.out.println("LIGHTING");
                for (Iterator<Window> lightingIterator = lightingWindows.iterator(); lightingIterator.hasNext();) {
                    Window lightingWindow = lightingIterator.next();
                    Dtw dtw = new Dtw();
                    Double score = dtw.calculateDistance(currentWindow, lightingWindow);
                    System.out.println("S(L) @ " + lightingWindow.getTimestamp() + ": " + score);
                }

                // Porovnavam s refridgerator
                System.out.println("REFRIDGERATOR");
                for (Iterator<Window> refridgeratorIterator = refridgeratorWindows.iterator(); refridgeratorIterator.hasNext();) {
                    Window refridgeratorWindow = refridgeratorIterator.next();
                    Dtw dtw = new Dtw();
                    Double score = dtw.calculateDistance(currentWindow, refridgeratorWindow);
                    System.out.println("S(R) @ " + refridgeratorWindow.getTimestamp() + ": " + score);
                }

                // Porovnavam s bathroom
                System.out.println("BATHROOM");
                for (Iterator<Window> bathroomIterator = bathroomWindows.iterator(); bathroomIterator.hasNext();) {
                    Window bathroomWindow = bathroomIterator.next();
                    Dtw dtw = new Dtw();
                    Double score = dtw.calculateDistance(currentWindow, bathroomWindow);
                    System.out.println("S(B) @ " + bathroomWindow.getTimestamp() + ": " + score);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
