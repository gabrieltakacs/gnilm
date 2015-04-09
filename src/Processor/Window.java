package Processor;

import Data.Timestamp;
import java.util.ArrayList;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window {

    private Timestamp timestamp;

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

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

}
