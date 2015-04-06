import Data.Channel;
import Data.DataRow;
import Data.House;
import DataLoader.ReddDataLoader.ReddDataLoader;

import java.util.ArrayList;

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
            System.out.println(channel.getData().get(0).getValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
