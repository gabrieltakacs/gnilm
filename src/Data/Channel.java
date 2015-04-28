package Data;

import Configuration.Configuration;
import Processor.Window;
import Processor.WindowExtractor;
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

    private ArrayList<Window> windows;

    private Double windowThreshold;

    public Channel(File file) {
        this.file = file;
        String name = file.getName().replaceFirst("[.][^.]+$", "");
        this.setName(name);
        this.setWindowThreshold(Configuration.defaultWindowThreshold);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO: ak tu chcem mat local caching, nemozem tu zadavat from a to ako parametre!
    public ArrayList<Window> getWindows(Integer timestampFrom, Integer timestampTo) throws Exception {
        if (this.windows == null) { // Local caching
            DataFrame dataFrame = this.read(timestampFrom, timestampTo);

            WindowExtractor windowExtractor = new WindowExtractor();
            this.windows = windowExtractor.detectWindows(dataFrame, this.windowThreshold);
        }

        return this.windows;
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

    public Channel setWindowThreshold(Double threshold) {
        this.windowThreshold = threshold;
        return this;
    }

}
