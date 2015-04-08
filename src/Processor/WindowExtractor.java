package Processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Gabriel Takács, Apr 2015
 */
public class WindowExtractor {

    public ArrayList<Window> detectWindows(Vector<Double> values, Vector<String> timestamps, double threshold) {

        ArrayList<Window> windows = new ArrayList<Window>();
        Boolean expectIncreasing = true; // Toto mi sluzi na to, aby som vedel oddelit jednotlive okna

        double lastValue = values.get(0);
        Window currentWindow = new Window();
        for (Iterator<Double> iterator = values.iterator(); iterator.hasNext();) {
            double value = iterator.next();

            if (Math.abs(value - lastValue) > threshold) { // Nieco sa deje
                if (value > lastValue) { // Som na stupajucom kopci...
                    if (expectIncreasing) { // ...a ocakavam stupanie...

                        if (currentWindow.size() == 0) { // Do windowu vlozim aj tu predoslu hodnotu
                            currentWindow.add(lastValue);
                        }

                        currentWindow.add(value); // ...tak si tu hodnotu ulozim.
                    } else { // som sice na stupani, ale neocakavam ho, znamena to zaciatok noveho okna

                        if (currentWindow.size() > 0) {
                            windows.add(currentWindow); // stare okno ulozim
                        }
                        currentWindow = new Window(); // vytvorim nove okno
                        currentWindow.add(value);
                        expectIncreasing = true; // a budem znova ocakavat stupanie
                    }
                } else { // Som na klesajucom kopci
                    expectIncreasing = false;

                    if (currentWindow.size() > 0) { // Kazdy window musi zacinat stupanim
                        currentWindow.add(value);

                        if (value < currentWindow.get(0)) {
                            windows.add(currentWindow);
                            currentWindow = new Window();
                            expectIncreasing = true;
                        }
                    }
                }
            } else {
                if (currentWindow.size() > 0) { // Som uprostred detekcie
                    currentWindow.add(value); // Ulozim si hodnotu, aj ked akurat nestupam ani neklesam, lebo aj toto patri do windowu
                }
            }
            lastValue = value;
        }

        return windows;
    }

}
