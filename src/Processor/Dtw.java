package Processor;

import Data.Window;
import java.util.Iterator;
import java.util.Vector;

/**
 * Gabriel Takács, Apr 2015
 */
public class Dtw {

    private double[][] results;

    public Double calculateDistance(Window p, Window q) {

        this.results = new double[p.size()][q.size()];

        for (int i = 1; i < p.size(); i++) {
            for (int j = 1; j < q.size(); j++) {
                Vector<Double> tmp = new Vector<Double>();
                tmp.add(this.getCacheElement(i-1, j));
                tmp.add(this.getCacheElement(i-1, j-1));
                tmp.add(this.getCacheElement(i, j-1));

                double d = Math.abs(p.get(i) - q.get(i));
                d += this.getMinOfVector(tmp);
                this.results[i][j] = d;
            }
        }

        return results[p.size()-1][q.size()-1];
    }

    private double getCacheElement(int x, int y) {
        if (x == 0 && y == 0) {
            return 0.0;
        } else if (x == 0 || y == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return this.results[x][y];
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

}
