package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Gabriel Takács, Apr 2015
 */
public class Channel {

    private File file;

    private String name;

    private ArrayList<DataRow> data;

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

    public ArrayList<DataRow> getData() throws Exception {
        if (this.data == null) {
            this.readData();
        }

        return this.data;
    }

    private void readData() throws Exception {
        if (!this.file.canRead()) {
            throw new Exception("Channel file is not readable!");
        }

        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        String line;
        ArrayList<DataRow> data = new ArrayList<DataRow>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            DataRow row = new DataRow();
            row.setTimestamp(parts[0]);
            row.setValue(Float.parseFloat(parts[1]));
            data.add(row);
        }

        this.data = data;
    }
}
