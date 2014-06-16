package fr.unice.polytech.entities;

import fr.unice.polytech.datasources.TaskDataSource;

/**
 * Created by Clement on 10/06/2014.
 */
public class TaskEntity {
    private long id;
    private String title;
    private long startDate;
    private long endDate;
    private long hourEstimation;
    private String description;
    private int priority;
    private double weight;


    public TaskEntity(long id, String title, long startDate, long endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weight = TaskDataSource.DEFAULT_WEIGHT;
    }

    public TaskEntity(long id, String title, long startDate, long endDate, long hourEstimation,
                      String description, int priority) {
        this(id, title, startDate, endDate);
        this.hourEstimation = hourEstimation;
        this.description = description;
        this.priority = priority;
    }

    public TaskEntity setId(long id) { this.id = id; return this; }
    public TaskEntity setTitle(String title) { this.title = title; return this; }
    public TaskEntity setStartDate(long startDate) { this.startDate = startDate; return this; }
    public TaskEntity setEndDate(long endDate) { this.endDate = endDate; return this; }
    public TaskEntity setHourEstimation(long hourEstimation) { this.hourEstimation = hourEstimation; return this; }
    public TaskEntity setDescription(String description) { this.description = description; return this; }
    public TaskEntity setPriority(int priority) { this.priority = priority; return this; }
    public TaskEntity setWeight(double weight) { this.weight = weight; return this; }

    public long getId() { return id; }
}
