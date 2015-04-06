package DataLoader;

import java.io.File;

/**
 * Gabriel Takács, Mar 2015
 */
abstract public class DataLoaderAbstract {

    protected File rootDirectory;

    /**
     * Method sets the data base directory. This directory should contain individual subdirectories for all houses.
     * @param directory
     * @throws Exception
     */
    public void setBaseDirectory(String directory) throws Exception {
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

        this.rootDirectory = file;
    }

}
