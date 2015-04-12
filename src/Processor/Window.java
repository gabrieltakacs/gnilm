package Processor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window {

    private Integer timestamp;

    private ArrayList<Double> values;

    private Boolean isIncreasing = null;

    public Window() {
        this.values = new ArrayList<Double>();
    }

    public void addValue(double value) {

        if (this.isIncreasing == null) {
            if (this.values.size() == 1) {
                if (value - this.values.get(0) > 0) {
                    this.setIncreasing(true);
                } else {
                    this.setIncreasing(false);
                }
            }
        }

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

    public Boolean isIncreasing() {
        return this.isIncreasing;
    }

    private void setIncreasing(Boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

}
