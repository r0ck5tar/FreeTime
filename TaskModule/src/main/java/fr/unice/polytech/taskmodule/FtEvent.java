package fr.unice.polytech.taskmodule;

import android.provider.CalendarContract;
import android.provider.ContactsContract;

/**
 * Created by Clement on 11/06/2014.
 */
public class FtEvent {
    private Long eventId;
    private TYPE type;
    private CalendarContract.Events event;

    public FtEvent(CalendarContract.Events event, Long eventId, TYPE type){
        this.event = event;
        this.eventId = eventId;
        this.type = type;
    }

}
