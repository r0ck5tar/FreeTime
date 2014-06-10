package fr.unice.polytech.taskmodule;

import android.content.ContentResolver;

import java.util.Calendar;

/**
 * Created by Clement on 10/06/2014.
 */
public class TaskBuilder {
    private Task task;

    public TaskBuilder(){
        this.task = new Task();
    }

    public void taskStartDate(Calendar start) throws WrongStartTaskException {
        task.setTaskStart(start);
    }

    public void taskEndDate(Calendar end) throws WrongEndTaskException {
        task.setTaskEnd(end);
    }

    public void isAllDay(boolean isAllDayBool){
        task.setAllDay(isAllDayBool);
    }

    public void priority(int priority){
        task.setPriority(priority);
    }

    public void hourEstimation(int hour){
        task.setHourEstimation(hour);
    }

    public void taskType(String type){
        Task.TASK_TYPE typeEnum = task.stringToEnum(type);
        task.setType(typeEnum);
    }

    public void description(String description){
        task.setDescription(description);
    }

    //you have to call this methode after setting all the parameters;
    public void createTask(Long calendarId, ContentResolver contentResolver){
        task.publish(calendarId,contentResolver);
    }

}
