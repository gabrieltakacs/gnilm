package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Gabriel Takács, Apr 2015
 */
public class Channel {

    private File file;

    private String name;

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

    public DataFrame read(Integer timestampFrom, Integer timestampTo) throws Exception {
        if (!this.file.canRead()) {
            throw new Exception("Channel file is not readable!");
        }

        DataFrame dataFrame = new DataFrame();
        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            Double timestampDoubleValue = Double.parseDouble(parts[0]);
            Integer timestamp = timestampDoubleValue.intValue();
            Double value = Double.parseDouble(parts[1]);

            if (timestampFrom != null && timestamp < timestampFrom) {
                continue;
            }

            if (timestampTo != null && timestamp > timestampTo) {
                break;
            }

            dataFrame.addRow(timestamp, value);
        }

        return dataFrame;
    }

}
