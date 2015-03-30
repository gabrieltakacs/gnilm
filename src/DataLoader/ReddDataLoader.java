package DataLoader;

import java.io.File;

/**
 * Gabriel Takács, Mar 2015
 */
public class ReddDataLoader extends DataLoader implements DataLoaderInterface {

    private static final String labelsFileName = "labels.dat";

    private String directory;

    /**
     * Method sets data directory for the REDD data. This directory should contain individual subdirectories for all houses.
     * @param directory
     * @throws Exception
     */
    public void setDataDirectory(String directory) throws Exception {

        File file = new File(directory);

        if (!file.exists()) {
            throw new Exception("Directory " + directory + " does not exist!");
        }

        if (!file.isDirectory()) {
            throw new Exception(directory + " is not a directory!");
        }

        if (!file.canRead()) {
            throw new Exception("Directory " + directory + " is not readable!");
        }

        if (file.listFiles().length == 0) {
            throw new Exception("Directory " + directory + " is empty!");
        }

        this.directory = directory;
    }

    @Override
    public void loadData() {

    }
}
