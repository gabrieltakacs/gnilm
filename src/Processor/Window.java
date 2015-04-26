package Processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window {

    private Integer timestamp;

    private ArrayList<Double> values;

    private Boolean isIncreasing = null;

    private Double minValue;

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

        if (this.minValue == null || value < this.minValue) {
            this.minValue = value;
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

    public Double calculateDistance(Window otherWindow) {
        ArrayList<Double> otherWindowValues = otherWindow.getValues();

        double cache[][] = new double[this.values.size()][otherWindowValues.size()];
        for (int i = 1; i < this.values.size(); i++) {
            for (int j = 1; j < otherWindowValues.size(); j++) {
                Vector<Double> tmp = new Vector<Double>();
                tmp.add(this.getCacheElement(cache, i-1, j));
                tmp.add(this.getCacheElement(cache, i-1, j-1));
                tmp.add(this.getCacheElement(cache, i, j-1));
                double d = Math.abs(this.values.get(i) - otherWindowValues.get(i)) + this.getMinOfVector(tmp);
                cache[i][j] = d;
            }
        }

        return cache[this.values.size()-1][otherWindowValues.size()-1];
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public void close() {
        int i = 0;
        for (Iterator<Double> iterator = this.values.iterator(); iterator.hasNext();) {
            Double value = iterator.next();
            value -= this.minValue;
            this.values.set(i, value);
        }
    }

    private void setIncreasing(Boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
    }

    private double getMinOfVector(Vector<Double> vector) {
        Boolean isFirst = true;
        double min = 0.0;
        for (Iterator<Double> doubleIterator = vector.iterator(); doubleIterator.hasNext();) {
            if (isFirst) {
                min = doubleIterator.next();
                isFirst = false;
            } else {
                double currentNumber = doubleIterator.next();
                if (currentNumber < min) {
                    min = currentNumber;
                }
            }
        }

        return min;
    }

    private double getCacheElement(double[][] cache, int x, int y) {
        if (x == 0 && y == 0) {
            return 0.0;
        } else if (x == 0 || y == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return cache[x][y];
    }

}
