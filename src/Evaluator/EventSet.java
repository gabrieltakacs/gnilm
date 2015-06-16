package Evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;

public class EventSet {

    private ArrayList<Integer> events;

    public EventSet() {
        this.events = new ArrayList<Integer>();
    }

    public void registerEvent(Integer timestamp) {
        this.events.add(timestamp);
    }

    public ArrayList<Integer> getEvents() {
        Collections.sort(events);
        return this.events;
    }

}
