package fr.unice.polytech.freetimealgorithm.model;

import fr.unice.polytech.freetimealgorithm.gui.listeners.DummyCalendarListener;
import fr.unice.polytech.freetimealgorithm.tools.DateTools;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hakim on 13/06/14.
 */
public class DummyCalendar {
    ArrayList<Event> events = new ArrayList<Event>();
    private DummyCalendarListener listener;

    public void addEvent(Event e) {
        events.add(e);
    }

    public void addEvent(String title, long startTime, long endTime) {
        events.add(new Event(title, startTime, endTime));
        Collections.sort(events);
        System.out.println("Created Event " + title
                         + "\tstart: " + DateTools.print(startTime)
                         + "\tend: " + DateTools.print(endTime));
        listener.updateEvents(events);
    }

    public Event getEvent(String eventTitle) {
        for(Event e : events) {
            if (e.getTitle().equals(eventTitle)) {
                return e;
            }
        }

        return null;
    }

    public void setListener(DummyCalendarListener listener) {
        this.listener = listener;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
