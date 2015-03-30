package Data;

import java.util.ArrayList;

/**
 * Gabriel Takács, Mar 2015
 */
public class DataSet {

    private ArrayList<DataRow> data;

    public ArrayList<DataRow> getData() {
        return this.data;
    }

    public void addRow(DataRow dataRow) {
        this.data.add(dataRow);
    }

}
