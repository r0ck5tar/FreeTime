package fr.unice.polytech.freetimealgorithm.optimiser;

import fr.unice.polytech.freetimealgorithm.gui.listeners.OptimiserListener;
import fr.unice.polytech.freetimealgorithm.model.DummyCalendar;
import fr.unice.polytech.freetimealgorithm.model.Event;
import fr.unice.polytech.freetimealgorithm.optimiser.EmptySlots.EmptySlot;
import fr.unice.polytech.freetimealgorithm.model.Task;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Hakim on 13/06/14.
 */
public class Optimiser {
    private static OptimiserListener listener;
    private static ArrayList<Task> tasks = new ArrayList<Task>();

    /*
        Variables and parameters used in the algorithm
     */
    //the current time (the time when the optimiser is called). This value can be set to any date and time for testing purposes.
    private static long now = Calendar.getInstance().getTimeInMillis();
    private static long latestEndDate;  //the latest due date among all the tasks.
    private static EmptySlots emptySlots = new EmptySlots(); //The list of EmptySlots between now and latestEndDate.
    private static long totalEmptySlotTime; //The total length of EmptySlot time between now and the endDate of a Task.
    /*
       The sum of the requiredEstimatedTimeLeft for all the Tasks.
       Since some Tasks will have Events that are in the past (which might or might not have been completed), only the
       REMAINING estimated time of each Task is added to this sum. To get this value, call the timeLeftToDueDate() method
       of the task.
     */
    private static long totalEstimatedTimeRequired;
    private static ArrayList<Task> currentTasks; //list of Tasks with endDate < now

    /*
        Detects the empty slots in the given time range. And EmptySlot is a range of time that is not occupied by an Event.
        Calling this method clears the previously detected list of EmptySlots, before filling it up again.
     */
    public static ArrayList<EmptySlot> detectEmptySlots(DummyCalendar cal, long startTimeRange, long endTimeRange) {
        emptySlots.clearEmptySlots();

        //all the Events between startTimeRange and endTimeRange (startTimeRange < event.startTime < endTimeRange)
        ArrayList<Event> detectedEvents = new ArrayList<Event>();

        for(Event e : cal.getEvents()) {
            if(e.getStartTime() >= startTimeRange && e.getStartTime() < endTimeRange) {
                detectedEvents.add(e);
            }
        }

        //if there are no Events in the given time range, then [startTimeRange, endTimeRange] is an EmptySlot.
        if(detectedEvents.size() == 0 ){
            emptySlots.addEmptySlot(startTimeRange, endTimeRange);
        }

        //The following loop is only entered if there are Events in the given time range.
        for(int i = 0; i < detectedEvents.size(); i++) {
            Event e = detectedEvents.get(i);
            //for the first event detected, check if there is an EmptySlot before the event.
            if(i==0 && e.getStartTime() > startTimeRange) {
               emptySlots.addEmptySlot(startTimeRange, e.getStartTime());
            }

            //the time after the end of the last event is an EmptySlot if the last event ends within the given time range.
            if(i == (detectedEvents.size()-1) && e.getEndTime() < endTimeRange) {
                emptySlots.addEmptySlot(e.getEndTime(), endTimeRange);
            }

            //for events that are not the first or last Event in the given time range, check if there are EmptySlots
            //between to consecutive Events.
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

    public static void addTaskToCalendar(DummyCalendar cal, String title, long timeEstimation, long startDate, long endDate, int priority) {
        //Create a newTask, but don't add it to the tasks list yet (it should only be added at the end, once it has been
        //split up into Events
        Task newTask = new Task(title, timeEstimation, startDate, endDate, priority);

        /*
            Initialize the variables and parameters needed for the calculations in the algorithm.

            # The value of now can be set by calling Optimiser.setNow() before calling the other methods of this class.
              This can be useful for tests.
            # First we get a list of the currentTasks. We also find the latestEndDate.
            # Then we find all the empty slots between now and latestEndDate, using detectEmptySlots().
         */
        if (now == 0 ) { now = Calendar.getInstance().getTimeInMillis(); }
        latestEndDate = newTask.getEndDate();
        currentTasks = new ArrayList<Task>();

        for(Task t : tasks) {
            //find the currentTasks (Tasks that have a due date that is in the future (i.e. endDate > now)
            if(t.getEndDate() > now) {
                currentTasks.add(t);
            }
            //find the latestEndDate
            if(t.getEndDate() > latestEndDate) {
                latestEndDate = t.getEndDate();
            }
        }

        //Call the detectEmptySlots method to fill up the EmptySlots list. Note that the EmptySlots list is cleared each
        //time the detectEmptySlots method is called.
        detectEmptySlots(cal, now, latestEndDate);

        //We calculate the task weights for the currentTasks.
        calculateAndSetTaskWeights(currentTasks);
    }

    private static void calculateAndSetTaskWeights(ArrayList<Task> tasks) {
        //for now, we're not taking into account the priority.
        for(Task t : tasks) {
            t.setWeight(t.estimatedRequiredTimeRemaining(now)/t.timeLeftToDueDate(now));
            System.out.print("Task" + t.getTitle() + "\t Weight = " + t.getWeight());
        }
    }

    public ArrayList<Task> getTasks() { return tasks; }

    public static void setListener(OptimiserListener listener) {
        Optimiser.listener = listener;
    }
    public static void setNow(long now) { Optimiser.now = now; }
}
