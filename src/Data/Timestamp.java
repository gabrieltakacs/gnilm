package Data;

/**
 * Gabriel Takács, Apr 2015
 */
public class Timestamp {

    protected String value;

    public Timestamp(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
