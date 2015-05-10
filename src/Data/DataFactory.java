package Data;

import java.io.File;

public class DataFactory {

    public static House getHouse(String path) throws Exception {

        File houseBaseDirectory = new File(path);
        if (!houseBaseDirectory.exists()) {
            throw new Exception("Directory does not exists!");
        }

        return new House(houseBaseDirectory);
    }

}
