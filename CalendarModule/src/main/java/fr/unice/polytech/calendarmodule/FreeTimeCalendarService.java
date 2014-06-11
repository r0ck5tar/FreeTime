package fr.unice.polytech.calendarmodule;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.CalendarContract.*;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import fr.unice.polytech.freetimedatabase.FreeTimeDbContract;
import fr.unice.polytech.freetimedatabase.FreeTimeDbHelper;

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

    public long getFreeTimeCalendarId() {
        String[] projection = new String[]{Calendars._ID};
        String selection = Calendars.ACCOUNT_NAME + " = ? AND "
                + Calendars.ACCOUNT_TYPE +  " = ? ";
        // use the same values as above:
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
        Cursor cursor = getContentResolver().query(Events.CONTENT_URI, null, Events.CALENDAR_ID + " = ? ",
                                             new String[]{Long.toString(calId)}, Events.DTSTART + " ASC");
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

    public long createEvent(String title, Calendar startCal, Calendar endCal) {
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH);
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH);
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        return createEvent(title, startYear, startMonth, startDay, endYear, endMonth, endDay);
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
                            int startHour, int startMinute,
                            int endYear, int endMonth, int endDay,
                            int endHour, int endMinute) {

        long calId = getFreeTimeCalendarId();

        EventBuilder eb = new EventBuilder(calId);
        return eb.createEvent(title)
               .startY(startYear).startM(startMonth).startD(startDay)
               .startH(startHour).startMin(startMinute).startS(0)
               .endY(endYear).endM(endMonth).endD(endDay)
               .endH(endHour).endMin(endMinute).endS(0)
               .timeZone(TimeZone.getDefault().getID())
               .finalizeStartTime().finalizeEndTime()
               .allDay(false).description("description goes here")
               .availability(Events.AVAILABILITY_BUSY)
               .organizer("demo@freetime.com")
               .finalizeEvent(getContentResolver());
    }

    public void findUnoccupiedTimeSlots(long beginRange, long endRange) {
        String[] projection = new String[] {
                Instances._ID, Instances.BEGIN, Instances.END, Instances.EVENT_ID
        };
        //TODO perhaps the search query string should be passed as well, so we can order the query results in ascending order of Instances.BEGIN
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String queryString = queryBuilder.buildQuery(projection, null, null, null, Instances.BEGIN + " ASC", null);
        Cursor cursor = Instances.query(getContentResolver(), projection, beginRange, endRange, queryString);
        ContentValues values = new ContentValues();

        final int BEGIN = cursor.getColumnIndex(Instances.BEGIN);
        final int END = cursor.getColumnIndex(Instances.END);
        final int EVENT_ID = cursor.getColumnIndex(Instances.EVENT_ID);

        FreeTimeDbHelper freeTimeDbHelper = new FreeTimeDbHelper(getApplicationContext());

        //TODO: ignore instances of AllDay Events, and perhaps Events with availability = available.

        if(cursor.getCount() == 0) {
            //If there are no event instances in a given time range, that whole time range is an empty slot
            values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_START_TIME, beginRange);
            values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_END_TIME, endRange);
            freeTimeDbHelper.getWritableDatabase().insert(FreeTimeDbContract.UnoccupiedTime.TABLE_NAME, null, values);
        }

        else {
            values.clear(); //Always remember to clear the values in the ContentValues object before reusing it for putting other values
            cursor.moveToFirst();
            if(cursor.getLong(BEGIN) != beginRange) {
                //the starttime of the first empty slot in a given time range is equal to the start time of that time range (beginRange)
                //if there is no event instance that starts at time=beginRange
                values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_START_TIME, beginRange);
                //add the BEGIN time of the found instance as the endtime of the empty slot
                values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_END_TIME, cursor.getLong(BEGIN));

                findUnoccupiedTimeSlots(cursor.getLong(END), endRange);
            }
            else {
                findUnoccupiedTimeSlots(cursor.getLong(END), endRange);

                //region overly-complicated else case
                /* if there is an event instance that starts at time=beginRange, the startTime of the first empty slot in the given range
                   is equal to the END time of the instance */
                /*
                values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_START_TIME, cursor.getLong(END));
                if (cursor.isLast()) {
                    values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_END_TIME, endRange);
                    freeTimeDbHelper.getWritableDatabase().insert(FreeTimeDbContract.UnoccupiedTime.TABLE_NAME, null, values);
                }

                else {
                    cursor.moveToNext();
                    values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_END_TIME, cursor.getLong(BEGIN));
                }
                */
                //endregion
            }
        }

        //region old code
        /*
        if(cursor.getCount() > 0 ) {

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                System.out.println("Event found: " + cursor.getLong(0));

                ContentValues values = new ContentValues();
                values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_END_TIME, cursor.getLong(cursor.getColumnIndex(Instances.END)));
                values.put(FreeTimeDbContract.UnoccupiedTime.COLUMN_START_TIME, cursor.getLong(cursor.getColumnIndex(Instances.BEGIN)));


                FreeTimeDbHelper freeTimeDbHelper = new FreeTimeDbHelper(getApplicationContext());
                freeTimeDbHelper.getWritableDatabase()
                        .insert(FreeTimeDbContract.UnoccupiedTime.TABLE_NAME, null, values);

            }
        }
       */
        //endregion
    }
}
