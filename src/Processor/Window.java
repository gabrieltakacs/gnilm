package Processor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window {

    private Integer timestamp;

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

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTimestamp() {
        return this.timestamp;
    }

    public void printInfo() {
        System.out.println("* Window @ " + this.timestamp + ", length: " + this.values.size());
    }

    public void printData() {
        for (Iterator<Double> iterator = this.values.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }

}
