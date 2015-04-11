package Processor;

import Data.DataFrame;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class WindowExtractor {

    public static ArrayList<Window> detectWindows(DataFrame dataFrame, double threshold) {


        ArrayList<Double> values = dataFrame.getValues();
        ArrayList<Integer> timestamps = dataFrame.getTimestamps();

        ArrayList<Window> windows = new ArrayList<Window>();

        Double previousValue = null;
        Double previousDiff = 0.0;
        Window currentWindow = null;
        Integer rowIndex = 0;
        for (Iterator<Double> iterator = values.iterator(); iterator.hasNext();) {
            double value = iterator.next();

            if (previousValue != null) {
                    if (currentWindow != null && currentWindow.getValues().size() > 0) {
                        if ((previousDiff > 0 && value - previousValue <= 0) || (previousDiff < 0 && value - previousValue >= 0)) { // Change of direction
                            windows.add(currentWindow);
                            currentWindow = null;
                    }
                }

                if (Math.abs(value - previousValue) > threshold) { // Event detected
                    if (currentWindow == null) { // Window has not been created yet
                        currentWindow = new Window(); // Create a new window
                        currentWindow.setTimestamp(timestamps.get(rowIndex));
                        currentWindow.addValue(previousValue); // Add previous value to the window
                    }
                    currentWindow.addValue(value);
                    previousDiff = value - previousValue;
                }
            }

            previousValue = value;
            rowIndex++;
        }

        if (currentWindow != null) {
            windows.add(currentWindow);
        }

        return windows;
    }
}
