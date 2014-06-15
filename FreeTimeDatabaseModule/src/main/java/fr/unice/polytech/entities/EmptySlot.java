package fr.unice.polytech.entities;

/**
 * Created by Hakim on 15/06/2014.
 */
public class EmptySlot {
    private long id;
    private long startTime;
    private long endTime;

    public EmptySlot(long id, long startTime, long endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId()        { return id; }
    public long getStartTime() { return startTime; }
    public long getEndTime()   { return endTime; }
}
