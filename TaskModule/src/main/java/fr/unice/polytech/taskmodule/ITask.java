package fr.unice.polytech.taskmodule;

import java.util.List;

import fr.unice.polytech.calendarmodule.EventBuilder;

/**
 * Created by Yoann on 07/06/2014.
 */
public interface ITask {

    public List<EventBuilder> publish(long calendarId);
    public boolean evaluate();
    public List<List<EventBuilder>> getOptionSchedule();
}
