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

    public TaskBuilder taskStartDate(Calendar start) throws WrongStartTaskException {
        task.setTaskStart(start); return this;
    }

    public TaskBuilder taskEndDate(Calendar end) throws WrongEndTaskException {
        task.setTaskEnd(end);return this;
    }

    public TaskBuilder isAllDay(boolean isAllDayBool){
        task.setAllDay(isAllDayBool);return this;
    }

    public TaskBuilder priority(int priority){
        task.setPriority(priority);return this;
    }

    public TaskBuilder hourEstimation(int hour){
        task.setHourEstimation(hour);return this;
    }

    public TaskBuilder taskType(String type){
        Task.TASK_TYPE typeEnum = task.stringToEnum(type);
        task.setType(typeEnum);
        return this;
    }

    public TaskBuilder description(String description){
        task.setDescription(description);return this;
    }

    //you have to call this methode after setting all the parameters;
    public void createTask(Long calendarId, ContentResolver contentResolver){
        task.publish(calendarId,contentResolver);
    }

}
