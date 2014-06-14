package fr.unice.polytech.freetimealgorithm.model;

import java.util.ArrayList;
import java.util.Calendar;

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

    public long timeLeftToDueDate(){ return Calendar.getInstance().getTimeInMillis() - endDate; }
    public long estimatedTimeRemaining() {
        return timeEstimation;
    }

    public long getStartDate() { return startDate; }
    public long getEndDate() { return endDate; }
    public long getTimeEstimation() { return timeEstimation; }
    public int getPriority() { return priority; }
    public ArrayList<FtEvent> getFtEvents() { return ftEvents; }

    public void setWeight(double weight) { this.weight = weight; }

}
