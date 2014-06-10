package fr.unice.polytech.freetime.app;

/**
 * Created by user on 09/06/2014.
 */
public class event {

    private String title;
    private int startTime;
    private int endTime;
    private String day;

    public event(String title,int startDay,int endDay,String day){
        this.day=day;
        this.endTime=endDay;
        this.startTime=startDay;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
