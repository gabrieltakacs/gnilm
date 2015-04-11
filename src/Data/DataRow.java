package Data;

/**
 * Gabriel Takács, Mar 2015
 */
public class DataRow {

    private Integer timestamp;

    private double value;

    public DataRow(Integer timestamp, Double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
