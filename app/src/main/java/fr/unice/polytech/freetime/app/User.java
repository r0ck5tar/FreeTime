package fr.unice.polytech.freetime.app;

import android.provider.CalendarContract;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 09/06/2014.
 */
public class User implements Serializable{

    private String name;
    private CalendarContract cal;
    private List<event> events;
    private List<event> freeTime;
    private boolean notificationParam;
    private int step;
    private boolean importCal;
    private boolean setFreeTime;
    private boolean setDailiesAct;
    private boolean setNotif;

    public User(){
        super();
        this.step=0;
        importCal=false;
        setFreeTime=false;
        setDailiesAct=false;
        setNotif=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CalendarContract getCal() {
        return cal;
    }

    public void setCal(CalendarContract cal) {
        this.cal = cal;
    }

    public List<event> getEvents() {
        return events;
    }

    public void setEvents(List<event> events) {
        this.events = events;
    }

    public List<event> getFreeeTime() {
        return freeTime;
    }

    public void setFreeeTime(List<event> freeeTime) {
        this.freeTime = freeeTime;
    }

    public boolean isNotificationParam() {
        return notificationParam;
    }

    public void setNotificationParam(boolean notificationParam) {
        this.notificationParam = notificationParam;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isImportCal() {
        return importCal;
    }

    public void setImportCal(boolean importCal) {
        this.importCal = importCal;
    }

    public boolean isSetFreeTime() {
        return setFreeTime;
    }

    public void setSetFreeTime(boolean setFreeTime) {
        this.setFreeTime = setFreeTime;
    }

    public boolean isSetDailiesAct() {
        return setDailiesAct;
    }

    public void setSetDailiesAct(boolean setDailiesAct) {
        this.setDailiesAct = setDailiesAct;
    }

    public boolean isSetNotif() {
        return setNotif;
    }

    public void setSetNotif(boolean setNotif) {
        this.setNotif = setNotif;
    }
}
