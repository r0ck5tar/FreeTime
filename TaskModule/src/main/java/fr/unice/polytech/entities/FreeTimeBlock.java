package fr.unice.polytech.entities;

import android.content.ContentResolver;
import java.util.Calendar;
import fr.unice.polytech.calendarmodule.EventBuilder;

/**
 * Created by Clement on 11/06/2014.
 */
public class FreeTimeBlock {

    public static void createFreeTimeBlock(Long calendarId, ContentResolver contentResolver, Calendar startFreeTime, Calendar endFreeTime, int day){
        EventBuilder eventBuilder = new EventBuilder(calendarId);
        eventBuilder.createEvent("Free Time")
                .startDT(startFreeTime.getTimeInMillis())
                .endDT(endFreeTime.getTimeInMillis())
                .allDay(false)
                .finalizeEvent(contentResolver);


        //call the recurrenceBuilder.
        //the recurrence rule will be everyweek on the same day;

        //todo call the reccurence builder


    }

}
