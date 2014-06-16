package fr.unice.polytech.calendarmodule;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.CalendarContract.*;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import fr.unice.polytech.datasources.EmptySlotDataSource;

/**
 * Created by Hakim on 5/6/2014.
 */
public class FreeTimeCalendarService extends Service {

    public class FreeTimeBinder extends Binder {
        public FreeTimeCalendarService getService() {
            return FreeTimeCalendarService.this;
        }
    }

    private final IBinder binder = new FreeTimeBinder();

    private long freeTimeCalendarId;

    @Override
    public IBinder onBind(Intent intent) {
       freeTimeCalendarId = getFreeTimeCalendarId(); return binder;
    }

    /*
        Methods that are called by the Application Activities.
     */

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public Cursor getAllCalendars() {

        String[] projection = new String[]{ CalendarContract.Calendars._ID,
                                            CalendarContract.Calendars.NAME,
                                            CalendarContract.Calendars.ACCOUNT_NAME };

        Cursor calCursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                projection, null, null, Calendars.NAME + " ASC");

        return calCursor;
    }

    public void importEventsFromCalendar(long calId){
        Cursor ec = getAllEventsFromCalendar(calId);

        if(ec.moveToFirst()) {
            do{
                String title = ec.getString(ec.getColumnIndex(Events.TITLE));
                long start = ec.getLong(ec.getColumnIndex(Events.DTSTART));
                long end = ec.getLong(ec.getColumnIndex(Events.DTEND));
                String timeZone = ec.getString(ec.getColumnIndex(Events.EVENT_TIMEZONE));
                String location = ec.getString(ec.getColumnIndex(Events.EVENT_LOCATION));
                int allDay = ec.getInt(ec.getColumnIndex(Events.ALL_DAY));
                String rRule = ec.getString(ec.getColumnIndex(Events.RRULE));
                int availability = ec.getInt(ec.getColumnIndex(Events.AVAILABILITY));

                new EventBuilder(freeTimeCalendarId).createEvent(title).startDT(start).endDT(end)
                    .timeZone(timeZone).location(location).allDay(allDay==1).rRule(rRule)
                    .availability(availability).finalizeEvent(getContentResolver());

            }while(ec.moveToNext());

            ec.close();
        }
    }

    public void addFreeTimeBlock(String day, long startTime, long endTime) {

    }

    public void createRecurringTask(String title, int[] days, long startTime, long endTime) {

    }

    /*
        Methods that are called by the TaskOptimisationModule
     */

    public long createEvent(String title, int startYear, int startMonth, int startDay,
                            int endYear, int endMonth, int endDay) {
        //long calId = getFreeTimeCalendarId();

        EventBuilder eb = new EventBuilder(freeTimeCalendarId);
        return eb.createEvent(title)
                .startY(startYear).startM(startMonth).startD(startDay)
                .endY(endYear).endM(endMonth).endD(endDay)
                .timeZone(TimeZone.getDefault().getID())
                .finalizeStartTime().finalizeEndTime()
                .allDay(true).description("description goes here")
                .availability(Events.AVAILABILITY_BUSY)
                .organizer("demo@freetime.com")
                .finalizeEvent(getContentResolver());
    }

    public long createEvent(String title, String description, boolean available,
                            int startYear, int startMonth, int startDay,
                            int startHour, int startMinute,
                            int endYear, int endMonth, int endDay,
                            int endHour, int endMinute, boolean allDay) {

        long calId = getFreeTimeCalendarId();

        EventBuilder eb = new EventBuilder(calId);
        return eb.createEvent(title)
                .startY(startYear).startM(startMonth).startD(startDay)
                .startH(startHour).startMin(startMinute).startS(0)
                .endY(endYear).endM(endMonth).endD(endDay)
                .endH(endHour).endMin(endMinute).endS(0)
                .timeZone(TimeZone.getDefault().getID())
                .finalizeStartTime().finalizeEndTime()
                .allDay(allDay).description(description==null? "":description)
                .availability(available? Events.AVAILABILITY_FREE:Events.AVAILABILITY_BUSY)
                //.organizer("demo@freetime.com")
                .finalizeEvent(getContentResolver());
    }


    public long getFreeTimeCalendarId() {
        String[] projection = new String[]{Calendars._ID};
        String selection = Calendars.ACCOUNT_NAME + " = ? AND " + Calendars.ACCOUNT_TYPE +  " = ? ";
        String[] selArgs = new String[]{"freetime", CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor = getContentResolver().query(Calendars.CONTENT_URI, projection, selection, selArgs, null);
        if (!cursor.moveToFirst()) {
            Uri uri = createCalendar();
            cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
        }

        long calId = cursor.getLong(0);
        cursor.close();
        return calId;
    }

    public void findUnoccupiedTimeSlots(long beginRange, long endRange, EmptySlotDataSource ds) {

        EmptySlotDataSource emptySlotDS;

        if(ds == null) { emptySlotDS = new EmptySlotDataSource(getApplicationContext()); }
        else { emptySlotDS = ds; }

        Cursor instancesInTimeRange = findInstancesInTimeRange(beginRange, endRange);

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
        Log.d("findUnoccupiedTimeSlots", "calling with beginRange = "
                + df.format(new Date(beginRange)) + " endRange = " + df.format(new Date(endRange)));
        Log.d("findUnoccupiedTimeSlots", "nb Events found = " + instancesInTimeRange.getCount());

        findUnoccupiedTimeSlots(instancesInTimeRange, beginRange, endRange, emptySlotDS);
    }

    /*
        Private methods
     */

    private Uri createCalendar(){
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, "freetime");
        values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.NAME, "FreeTime Calendar");
        values.put(Calendars.CALENDAR_DISPLAY_NAME, "FreeTime Calendar");
        values.put(Calendars.CALENDAR_COLOR, 0xffff0000);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.OWNER_ACCOUNT, "freetime@gmail.com");
        values.put(Calendars.CALENDAR_TIME_ZONE, "Europe/Paris");
        Uri.Builder builder = Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(Calendars.ACCOUNT_NAME, "com.freetime");
        builder.appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER,"true");
        Uri uri = getContentResolver().insert(builder.build(), values);

        System.out.println(uri);
        Toast.makeText(getApplicationContext(), "Created FreeTime Calendar" , Toast.LENGTH_LONG).show();
        return uri;
    }

    //TODO change back to private later.
    public Cursor getAllEventsFromCalendar(long calId) {
        Cursor cursor = getContentResolver().query(Events.CONTENT_URI, null, Events.CALENDAR_ID + " = ? ",
                new String[]{Long.toString(calId)}, Events.DTSTART + " ASC");
        return cursor;
    }

    private Cursor findInstancesInTimeRange(long beginRange, long endRange) {
        String[] projection = new String[] {
                Instances._ID, Instances.BEGIN, Instances.END, Instances.EVENT_ID, Instances.CALENDAR_ID, Instances.TITLE
        };
        String selection = CalendarContract.Instances.CALENDAR_ID + " = ? AND " + Instances.BEGIN + " > ? " ;
        String[] selArgs = new String[]{String.valueOf(freeTimeCalendarId), String.valueOf(beginRange)};

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, beginRange);
        ContentUris.appendId(builder, endRange);

        Cursor cursor = getContentResolver().query(builder.build(), projection, selection, selArgs, Instances.BEGIN + " ASC");

        return cursor;
    }

    private void findUnoccupiedTimeSlots(Cursor instancesInTimeRange, long beginRange, long endRange,
                                         EmptySlotDataSource emptySlotDS) {

        final int BEGIN = instancesInTimeRange.getColumnIndex(Instances.BEGIN);
        final int END = instancesInTimeRange.getColumnIndex(Instances.END);
        final int TITLE = instancesInTimeRange.getColumnIndex(Instances.TITLE);

        //TODO: ignore instances of AllDay Events, and perhaps Events with availability = available.
        if(instancesInTimeRange.getCount() == 0) {
            //If there are no event instances in a given time range, that whole time range is an empty slot
            emptySlotDS.createEmptySlot(beginRange, endRange);
            instancesInTimeRange.close();
        }

        else {
            instancesInTimeRange.moveToFirst();
            if(instancesInTimeRange.getLong(BEGIN) != beginRange) {
                //the starttime of the first empty slot in a given time range is equal to the start time of that time range (beginRange)
                //if there is no event instance that starts at time=beginRange. The endTime of the EmptySlot is the BEGIN time of the
                //found instance.
                emptySlotDS.createEmptySlot(beginRange, instancesInTimeRange.getLong(BEGIN));

                long newBeginRange = instancesInTimeRange.getLong(END);
                instancesInTimeRange.close();
                findUnoccupiedTimeSlots(newBeginRange, endRange, emptySlotDS);
            }
            else {
                long newBeginRange = instancesInTimeRange.getLong(END);
                instancesInTimeRange.close();
                findUnoccupiedTimeSlots(newBeginRange, endRange, emptySlotDS);
            }
        }
    }

}
