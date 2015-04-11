package Data;

import java.util.ArrayList;

/**
 * Gabriel Takács, Apr 2015
 */
public class DataFrame {

    private ArrayList<Integer> timestamps;

    private ArrayList<Double> values;

    private Integer length;

    public DataFrame() {
        this.timestamps = new ArrayList<Integer>();
        this.values = new ArrayList<Double>();
        this.length = 0;
    }

    public void addRow(Integer timestamp, Double value) {
        this.timestamps.add(timestamp);
        this.values.add(value);
        this.length++;
    }

    public ArrayList<Integer> getTimestamps() {
        return this.timestamps;
    }

    public ArrayList<Double> getValues() {
        return this.values;
    }

    public DataRow getRow(Integer index) {
        DataRow row = new DataRow(this.timestamps.get(index), this.values.get(index));
        return row;
    }

    public Integer getLength() {
        return this.length;
    }

}
