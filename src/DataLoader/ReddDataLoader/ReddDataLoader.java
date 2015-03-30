package DataLoader.ReddDataLoader;

import DataLoader.DataLoader;
import DataLoader.DataLoaderInterface;
import java.io.*;
import java.util.ArrayList;

/**
 * Gabriel Takács, Mar 2015
 */
public class ReddDataLoader extends DataLoader implements DataLoaderInterface {

    private static final String labelsFileName = "labels.dat";

    private String rootPath;

    private File rootDirectory;

    private String houseSubDirectory;

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

        this.rootPath = directory;
        this.rootDirectory = file;
    }

    /**
     *
     * @param houseSubDirectory
     * @throws Exception
     */
    public void setHouseSubDirectory(final String houseSubDirectory) throws Exception {

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.compareTo(houseSubDirectory) == 0) {
                    return true;
                }
                return false;
            }
        };

        if (this.rootDirectory.listFiles(filter).length == 0) {
            throw new Exception("The root directory does not contain directory for " + houseSubDirectory);
        }

        this.houseSubDirectory = houseSubDirectory;
    }

    @Override
    public void load() throws IOException {
        String path = new StringBuilder().append(this.rootPath).append(this.houseSubDirectory).append("/").append(labelsFileName).toString();
        this.prepareLabels(path);
    }

    private ArrayList<FileLabel> prepareLabels(String path) throws IOException {
        File file = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        ArrayList<FileLabel> fileLabels = new ArrayList<FileLabel>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            FileLabel fileLabel = new FileLabel("channel_" + parts[0] + ".dat", parts[1]);
            fileLabels.add(fileLabel);
        }

        return fileLabels;
    }

}
