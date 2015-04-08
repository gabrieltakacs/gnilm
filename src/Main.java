import Data.Channel;
import Data.DataRow;
import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;
import Processor.Window;
import Processor.WindowExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Gabriel Takács, Mar 2015
 */
public class Main {

    public static void main(String[] args) {
        ReddDataLoader dataLoader = new ReddDataLoader();

        try {
            dataLoader.setBaseDirectory("/home/gtakacs/fiit/bp/gnilm/data/");
            House house = dataLoader.getHouse("house1");
            Channel channel = house.getChannel("mains");

            Vector<Double> values = channel.getValues();
            Vector<String> timestamps = channel.getTimestamps();
            Double threshold = 10.0;

            WindowExtractor windowExtractor = new WindowExtractor();
            ArrayList<Window> windows = windowExtractor.detectWindows(values, timestamps, threshold);

            System.out.println("Pocet detekovanych okien: " + windows.size());

            Window testWindow = windows.get(0);

            for (Iterator<Double> iterator = testWindow.iterator(); iterator.hasNext();) {
                System.out.println(iterator.next());
            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
