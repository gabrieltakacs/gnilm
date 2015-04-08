package Processor;

import java.util.Vector;

/**
 * Gabriel Takács, Apr 2015
 */
public class Window extends Vector<Double> {

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
