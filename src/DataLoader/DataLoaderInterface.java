package DataLoader;

import Data.House;

import java.io.IOException;
import java.util.ArrayList;

public interface DataLoaderInterface {

    public void setBaseDirectory(String directory) throws Exception;

    public ArrayList<String> listHouses() throws Exception;

    public House getHouse(String house) throws Exception;

}
