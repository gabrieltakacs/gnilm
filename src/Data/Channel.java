package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Gabriel Takács, Apr 2015
 */
public class Channel {

    private File file;

    private String name;

    private Vector<Double> values;

    private Vector<String> timestamps;

    public Channel(File file) {
        this.file = file;
        String name = file.getName().replaceFirst("[.][^.]+$", "");
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Double> getValues() throws Exception {
        if (this.values == null) {
            this.readData();
        }

        return this.values;
    }

    public Vector<String> getTimestamps() throws Exception {
        if (this.timestamps == null) {
            this.readData();
        }

        return this.timestamps;
    }

    private void readData() throws Exception {
        if (!this.file.canRead()) {
            throw new Exception("Channel file is not readable!");
        }

        this.timestamps = new Vector<String>();
        this.values = new Vector<Double>();

        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            this.timestamps.add(parts[0]);
            this.values.add(Double.parseDouble(parts[1]));
        }
    }
}
