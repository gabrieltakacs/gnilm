package Data;

import Configuration.Configuration;
import Processor.Window;
import Processor.WindowExtractor;

import java.io.*;
import java.util.*;

/**
 * Gabriel Takács, Apr 2015
 */
public class Channel {

    private File file;

    private String name;

    private ArrayList<Window> windows; // patterns

    private Double windowThreshold;

    private Integer currentTimestamp;
    private Double currentValue;

    private ArrayList<Window> associatedWindows;

    private TreeMap<Integer, Double> reconstructedConsumption;

    public Channel(File file) {
        this.file = file;
        String name = file.getName().replaceFirst("[.][^.]+$", "");
        this.setName(name);
        this.setWindowThreshold(Configuration.defaultWindowThreshold);
        this.currentValue = 0.0;
        this.reconstructedConsumption = new TreeMap<Integer, Double>();
        this.associatedWindows = new ArrayList<Window>();
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

    public Channel setCurrentValue(Double value) {
        this.currentValue = value;
        return this;
    }

    public Double getCurrentValue() {
        return this.currentValue;
    }

    public void addAssociatedWindow(Window window) {
        this.associatedWindows.add(window);
        // Nasekam tam hodnoty merani z useku, ked sa nic nedialo
        for (Integer i = this.currentTimestamp + 1; i < window.getTimestamp(); i++) {
            this.reconstructedConsumption.put(i, this.currentValue);
        }

        // Prejdem kazdu hodnotu daneho okna, odcitam od nej "consumptionUnder" a opat nasekam do hodnot
        Integer windowTimestamp = window.getTimestamp();
        for (Iterator<Double> iterator = window.getValues().iterator(); iterator.hasNext();) {
            Double value = iterator.next();
            this.reconstructedConsumption.put(windowTimestamp, value);
            this.currentTimestamp = windowTimestamp;
            this.currentValue = value;

            windowTimestamp++;
        }
    }

    public void closeChannel(Integer timestamp) {
        for (Integer i = this.currentTimestamp + 1; i < timestamp; i++) {
            this.reconstructedConsumption.put(i, this.currentValue);
        }
    }

    public void setCurrentTimestamp(Integer timestamp) {
        this.currentTimestamp = timestamp;
    }

    public void test() {
        for (Map.Entry<Integer, Double> entry : this.reconstructedConsumption.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    public void exportToFile(String path) throws FileNotFoundException, UnsupportedEncodingException {
        String fullPath = path + this.getName() + ".dat";
        PrintWriter writer = new PrintWriter(fullPath, "UTF-8");
        for (Map.Entry<Integer, Double> entry : this.reconstructedConsumption.entrySet()) {
            writer.println(entry.getKey() + " " + entry.getValue());
        }
        writer.close();
    }

}
