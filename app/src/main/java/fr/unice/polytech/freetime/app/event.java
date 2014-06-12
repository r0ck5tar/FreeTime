package fr.unice.polytech.freetime.app;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by user on 09/06/2014.
 */
public class event implements Serializable{

    private String title;
    private int startTime;
    private int endTime;
    private String[] day;
    private GregorianCalendar dateStart;
    private GregorianCalendar dateEnd;

    public event(String title,int startDay,int endDay,String[] day,GregorianCalendar start,GregorianCalendar end){
        super();
        this.day=day;
        this.endTime=endDay;
        this.startTime=startDay;
        this.title=title;
        this.dateStart=start;
        this.dateEnd=end;
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

    public String[] getDay() {
        return day;
    }

    public void setDay(String[] day) {
        this.day = day;
    }

    public GregorianCalendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(GregorianCalendar dateStart) {
        this.dateStart = dateStart;
    }

    public GregorianCalendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(GregorianCalendar dateEnd) {
        this.dateEnd = dateEnd;
    }
}
