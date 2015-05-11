package Data;

import Configuration.Configuration;
import Processor.Window;
import Processor.WindowExtractor;
import Utils.Log;

import java.io.*;
import java.util.*;

public class Channel {

    private File file;

    private String name;

    private ArrayList<Window> windows;

    private Double windowThreshold;

    private Integer currentTimestamp;
    private Double currentValue;

    private ArrayList<Window> associatedWindows;

    private TreeMap<Integer, Double> reconstructedConsumption;

    private Integer timestampFrom;
    private Integer timestampTo;

    public Channel(File file) {
        this.file = file;
        String name = file.getName().replaceFirst("[.][^.]+$", "");
        this.setName(name);
        this.setWindowThreshold(Configuration.getInstance().getDefaultEventDetectionThreshold());
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

    public void setDataRange(Integer timestampFrom, Integer timestampTo) {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
    }

    public ArrayList<Window> getWindows() throws Exception {
        if (this.windows == null) { // Local caching
            DataFrame dataFrame = this.read(this.timestampFrom, timestampTo);

            WindowExtractor windowExtractor = new WindowExtractor();
            this.windows = windowExtractor.detectWindows(dataFrame, this.windowThreshold);
        }

        return this.windows;
    }

    public DataFrame read(Integer timestampFrom, Integer timestampTo) throws Exception {
        if (!this.file.canRead()) {
            throw new Exception("Channel file is not readable!");
        }

        Integer testDataFrom = Configuration.getInstance().getTestDataRangeFrom();

        DataFrame dataFrame = new DataFrame();
        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            Double timestampDoubleValue = Double.parseDouble(parts[0]);
            Integer timestamp = timestampDoubleValue.intValue();
            Double value = Double.parseDouble(parts[1]);

            if (timestamp.equals(testDataFrom)) {
                this.setCurrentValue(value);
            }

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
        Integer index = 0;
        for (Iterator<Double> iterator = window.getValues().iterator(); iterator.hasNext();) {

            Double mainsWindowValue = iterator.next();
            Double value = this.currentValue;
            if (iterator.hasNext()) {
                Double nextMainsWindowValue = window.getValues().get(index + 1);
                Double diff = nextMainsWindowValue - mainsWindowValue;
                value = this.currentValue + diff;
            }

            if (value < 0.0) {
                value = 0.0;
            }

            this.reconstructedConsumption.put(windowTimestamp, value);
            this.currentTimestamp = windowTimestamp;
            this.currentValue = value;

            windowTimestamp++;
            index++;
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

    public void exportToFile(String path) throws FileNotFoundException, UnsupportedEncodingException {
        String fullPath = path + "/" + this.getName() + ".gnilm.dat";
        PrintWriter writer = new PrintWriter(fullPath, "UTF-8");
        for (Map.Entry<Integer, Double> entry : this.reconstructedConsumption.entrySet()) {
            writer.println(entry.getKey() + " " + entry.getValue());
        }
        writer.close();

        Log.getInstance().log("Disaggregated channel " + this.getName() + " saved to: " + fullPath);
    }

    public TreeMap<Integer, Double> getReconstructedConsumption() {
        return this.reconstructedConsumption;
    }

}
