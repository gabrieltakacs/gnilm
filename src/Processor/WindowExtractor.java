package Processor;

import Data.DataFrame;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gabriel Takács, Apr 2015
 */
public class WindowExtractor {

    private ArrayList<Window> windows;

    public WindowExtractor() {
        this.windows = new ArrayList<Window>();
    }

    public ArrayList<Window> detectWindows(DataFrame dataFrame, double threshold) {

        Integer numberOfClosingValues = 3;

        Window window = null;
        Double lastValue = null;

        Integer savedClosingValues = 0;
        Integer index = 0;
        for (Iterator<Double> iterator = dataFrame.getValues().iterator(); iterator.hasNext();) {
            Double value = iterator.next();

            if (lastValue != null)
            {
                if (Math.abs(lastValue - value) > threshold) {
                    if (savedClosingValues > 0) {
                        savedClosingValues = 0;
                    }

                    // Event has just started
                    if (window == null) {
                        window = new Window();
                        window.setTimestamp(dataFrame.getTimestamps().get(index-1));
                        window.addValue(lastValue);
                    }

                    // Add current value to the event
                    window.addValue(value);
                } else {
                    if (window != null) { // There is an active window
                        if (savedClosingValues < numberOfClosingValues) {
                            window.addValue(value);
                            savedClosingValues++;
                        } else {
                            this.addWindow(window);
                            savedClosingValues = 0;
                            window = null;
                        }
                    }
                }
            }

            lastValue = value;
            index++;
        }

        // Save unfinished window
        if (window != null) {
            this.addWindow(window);
        }

        return this.windows;
    }

    private void addWindow(Window window) {
        if (window != null && window.getValues().size() > 2) {
            this.windows.add(window);
        }
    }

}
