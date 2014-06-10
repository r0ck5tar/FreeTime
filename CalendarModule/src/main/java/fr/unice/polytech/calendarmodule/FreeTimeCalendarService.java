package fr.unice.polytech.calendarmodule;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.CalendarContract.*;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private long freeTimeCalendarId;

    @Override
    public IBinder onBind(Intent intent) {
       freeTimeCalendarId = getFreeTimeCalendarId(); return binder;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public Cursor getAllCalendars() {

        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        //CalendarContract.Calendars.ACCOUNT_TYPE
                };

        Cursor calCursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                projection, null, null, Calendars.NAME + " ASC");

        return calCursor;
    }

    public Uri createCalendar(){
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

    public long getFreeTimeCalendarId() {
        String[] projection = new String[]{Calendars._ID};
        String selection = Calendars.ACCOUNT_NAME + " = ? AND "
                + Calendars.ACCOUNT_TYPE +  " = ? ";
        String[] selArgs = new String[]{"freetime", CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor = getContentResolver().query(Calendars.CONTENT_URI,
                projection, selection, selArgs, null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        else{
            Uri uri = createCalendar();
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getLong(0);
            }
            return 0;
        }
    }

    public Cursor getAllEventsFromCalendar(long calId) {
        //String[] projection = new String[] {Events._ID, Events.TITLE, Events.DTSTART, Events.DTEND, Events.RRULE};
        Cursor cursor = getContentResolver().query(Events.CONTENT_URI, null, Events.CALENDAR_ID + " = ? ", new String[]{Long.toString(calId)}, Events.DTSTART + " ASC");
        return cursor;
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
        }
    }

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

    public long createEvent(String title, int startYear, int startMonth, int startDay,
                            int startHour, int startMinute, int startSecond,
                            int endYear, int endMonth, int endDay,
                            int endHour, int endMinute, int endSecond) {

        long calId = getFreeTimeCalendarId();

        EventBuilder eb = new EventBuilder(calId);
        return eb.createEvent(title)
               .startY(startYear).startM(startMonth).startD(startDay)
               .startH(startHour).startMin(startMinute).startS(startSecond)
               .endY(endYear).endM(endMonth).endD(endDay)
               .endH(endHour).endMin(endMinute).endS(endSecond)
               .timeZone(TimeZone.getDefault().getID())
               .finalizeStartTime().finalizeEndTime()
               .allDay(false).description("description goes here")
               .availability(Events.AVAILABILITY_BUSY)
               .organizer("demo@freetime.com")
               .finalizeEvent(getContentResolver());
    }
}
