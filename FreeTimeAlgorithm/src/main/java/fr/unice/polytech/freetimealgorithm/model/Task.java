package fr.unice.polytech.freetimealgorithm.model;

import java.util.ArrayList;

/**
 * Created by Hakim on 13/06/14.
 */
public class Task {
    private String title;
    private long timeEstimation;
    private long startDate;
    private long endDate;
    private int priority;
    private double weight;
    private ArrayList<FtEvent> ftEvents = new ArrayList<FtEvent>();
    private ArrayList<FtEvent> currentFtEvents = new ArrayList<FtEvent>();

    public static final int LOW_PRIORITY = 1;
    public static final int NORMAL_PRIORITY = 2;
    public static final int HIGH_PRIORITY = 3;

    public Task(String title, long timeEstimation, long startDate, long endDate, int priority) {
        this.title = title;
        this.timeEstimation = timeEstimation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
    }

    public long timeLeftToDueDate(long now){ return now - endDate; }
    public long estimatedRequiredTimeRemaining(long now) {
        return calculateTotalCompletedEventsTime(now) - timeEstimation;
    }

    /*
        Just providing another name for the findCurrentFtEvents method, because actually that method has two functions:
          # update the currentFtEvents list.
          # sum up the total time of the FtEvents that have already been completed (And that's why this method is here)
     */
    private long calculateTotalCompletedEventsTime(long now) { return findCurrentFtEvents(now);}

    public long findCurrentFtEvents(long now) {
        currentFtEvents.clear();
        long totalCompletedEventsTime = 0;
        for(FtEvent e : ftEvents) {
            //if the FtEvent starts in the future, it's a currentFtEvent
            if(e.getStartTime() > now) {
                currentFtEvents.add(e);
            }
            //if the FtEvent is in the past, check if it's completed. If it is, add it to the totalCompletedEventsTime.
            else if(e.getEndTime() < now && e.isCompleted()) {
                totalCompletedEventsTime += e.getDuration();
            }
        }

        return totalCompletedEventsTime;
    }

    /*
        This method is called by the optimiser when the currentFtEvents of a Task need to be changed. This could happen
        because a new task is added which requires modifying the user's schedule, so the FtEvents that have been planned
        for this Task need to be moved around, for example.
     */
    public void replaceCurrentFtEvents(long now, ArrayList<FtEvent> newCurrentFtEvents) {
        //call the findCurrentFtEvents method to update the currentFtEvents lists. Might not be necessary, since it should
        //have been called by the optimiser not long before this replaceCurrentFtEvents method was called.
        //I'm calling it anyway, just in case.
        findCurrentFtEvents(now);
        ftEvents.removeAll(currentFtEvents);
        ftEvents.addAll(newCurrentFtEvents);
    }

    public String getTitle() { return title; }
    public long getStartDate() { return startDate; }
    public long getEndDate() { return endDate; }
    public long getTimeEstimation() { return timeEstimation; }
    public int getPriority() { return priority; }
    public ArrayList<FtEvent> getFtEvents() { return ftEvents; }

    public void setWeight(double weight) { this.weight = weight; }
    public double getWeight() { return weight; }
}
