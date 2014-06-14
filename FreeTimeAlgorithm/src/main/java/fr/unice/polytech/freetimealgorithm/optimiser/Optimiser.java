package fr.unice.polytech.freetimealgorithm.optimiser;

import fr.unice.polytech.freetimealgorithm.optimiser.EmptySlots;
import fr.unice.polytech.freetimealgorithm.gui.listeners.OptimiserListener;
import fr.unice.polytech.freetimealgorithm.model.DummyCalendar;
import fr.unice.polytech.freetimealgorithm.model.Event;
import fr.unice.polytech.freetimealgorithm.optimiser.EmptySlots.EmptySlot;
import fr.unice.polytech.freetimealgorithm.model.Task;
import fr.unice.polytech.freetimealgorithm.tools.DateTools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Hakim on 13/06/14.
 */
public class Optimiser {
    private static EmptySlots emptySlots = new EmptySlots();
    private static OptimiserListener listener;
    private static ArrayList<Task> tasks = new ArrayList<Task>();
    private static long now = Calendar.getInstance().getTimeInMillis();

    public static ArrayList<EmptySlot> detectEmptySlots(DummyCalendar cal, long startTimeRange, long endTimeRange) {
        emptySlots.clearEmptySlots();
        ArrayList<Event> detectedEvents = new ArrayList<Event>();

        for(Event e : cal.getEvents()) {
            if(e.getStartTime() >= startTimeRange && e.getStartTime() < endTimeRange) {
                detectedEvents.add(e);
                //System.out.println("Event detected: " + e.getTitle());
            }
        }

        if(detectedEvents.size() == 0 ){
            emptySlots.addEmptySlot(startTimeRange, endTimeRange);
        }

        for(int i = 0; i < detectedEvents.size(); i++) {
            Event e = detectedEvents.get(i);
            if(i==0 && e.getStartTime() > startTimeRange) {
               emptySlots.addEmptySlot(startTimeRange, e.getStartTime());
            }

            if(i == (detectedEvents.size()-1)) {
                emptySlots.addEmptySlot(e.getEndTime(), endTimeRange);
            }

            else {
                Event eNext = detectedEvents.get(i+1);
                //System.out.println("detecting empty slot between two events");
                if(e.getEndTime() != eNext.getStartTime()) {
                    //System.out.println("detecting empty slot: " + DateTools.print(e.getEndTime()) + "  " + DateTools.print(eNext.getStartTime()));
                    emptySlots.addEmptySlot(e.getEndTime(), eNext.getStartTime());
                }
            }
        }

        listener.emptySlotsFound(emptySlots.getEmptySlots());
        return emptySlots.getEmptySlots();
    }

    public static void addTaskToCalendar(String title, long timeEstimation, long startDate, long endDate, int priority) {

        Task newTask = new Task(title, timeEstimation, startDate, endDate, priority);

        if (now == 0 ) { now = Calendar.getInstance().getTimeInMillis(); }
        long latestEndDate = newTask.getEndDate();

        for(Task t : tasks) {
            if(t.getEndDate() > latestEndDate) {
                latestEndDate = t.getEndDate();
            }
        }
    }

    public  void calculateTaskWeights(ArrayList<Task> tasks) {

    }

    public ArrayList<Task> getTasks() { return tasks; }

    public static void setListener(OptimiserListener listener) {
        Optimiser.listener = listener;
    }
    public static void setNow(long now) { Optimiser.now = now; }
}
