package fr.unice.polytech.entities;

import fr.unice.polytech.datasources.TaskDataSource;

/**
 * Created by Clement on 10/06/2014.
 */
public class Task {
    private long id;
    private String title;
    private long startDate;
    private long endDate;
    private long hourEstimation;
    private String description;
    private int priority;
    private double weight;


    public Task(long id, String title, long startDate, long endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weight = TaskDataSource.DEFAULT_WEIGHT;
    }

    public Task(long id, String title, long startDate, long endDate, long hourEstimation,
                String description, int priority) {
        this(id, title, startDate, endDate);
        this.hourEstimation = hourEstimation;
        this.description = description;
        this.priority = priority;
    }

    public Task setId(long id) { this.id = id; return this; }
    public Task setTitle(String title) { this.title = title; return this; }
    public Task setStartDate(long startDate) { this.startDate = startDate; return this; }
    public Task setEndDate(long endDate) { this.endDate = endDate; return this; }
    public Task setHourEstimation(long hourEstimation) { this.hourEstimation = hourEstimation; return this; }
    public Task setDescription(String description) { this.description = description; return this; }
    public Task setPriority(int priority) { this.priority = priority; return this; }
    public Task setWeight(double weight) { this.weight = weight; return this; }

    public long getId() { return id; }
}
