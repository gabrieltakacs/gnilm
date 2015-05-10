package DataLoader.ReddDataLoader;

import Data.House;
import DataLoader.DataLoaderAbstract;
import DataLoader.DataLoaderInterface;
import java.io.File;
import java.util.ArrayList;

public class ReddDataLoader extends DataLoaderAbstract implements DataLoaderInterface {

    @Override
    public ArrayList<String> listHouses() throws Exception {
        if (this.rootDirectory == null) {
            throw new Exception("Base directory not set!");
        }

        File[] subDirectories = this.rootDirectory.listFiles();
        ArrayList<String> list = new ArrayList<String>();

        for (File currentFile : subDirectories) {
            list.add(currentFile.getName());
        }

        return list;
    }

    @Override
    public House getHouse(String houseName) throws Exception {
        if (this.rootDirectory == null) {
            throw new Exception("Base directory not set!");
        }

        String housePath = this.rootDirectory.getAbsolutePath() + "/" + houseName;
        File houseBaseDirectory = new File(housePath);
        if (!houseBaseDirectory.exists()) {
            throw new Exception("Directory does not exists!");
        }

        House house = new House(houseBaseDirectory);
        return house;
    }
}
