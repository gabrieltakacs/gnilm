package Processor;

import java.util.ArrayList;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window {

    private String timestamp;

    private ArrayList<Double> values;

    public Window() {
        this.values = new ArrayList<Double>();
    }

    public void addValue(double value) {
        this.values.add(value);
    }

    public ArrayList<Double> getValues() {
        return this.values;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

}
