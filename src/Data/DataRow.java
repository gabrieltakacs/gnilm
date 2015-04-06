package Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * Gabriel Takács, Mar 2015
 */
public class DataRow {

    private String timestamp;

    private float value;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
