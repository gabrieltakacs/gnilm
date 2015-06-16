package Utils;

public class Stats {

    public static Double precision(Double tp, Double fp) {
        if (tp + fp == 0) {
            return 0.0;
        }

        return (double) (tp / (tp + fp));
    }

    public static Double recall(Double tp, Double fn) {
        if (tp + fn == 0) {
            return 0.0;
        }

        return (double) (tp / (tp + fn));
    }

    public static Double fscore(Double precision, Double recall) {
        if (precision + recall == 0) {
            return 0.0;
        }

        return (2.0 * precision * recall) / (precision + recall);
    }

}
