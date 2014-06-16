import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.CalendarContract;
import android.test.RenamingDelegatingContext;
import android.test.ServiceTestCase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;
import fr.unice.polytech.calendarmodule.FreeTimeCalendarService.FreeTimeBinder;
import fr.unice.polytech.datasources.EmptySlotDataSource;
import fr.unice.polytech.freetimedatabase.FreeTimeDbHelper;


/**
 * Created by Hakim on 11/06/2014.
 */

public class FreeTimeCalendarServiceTest extends ServiceTestCase<FreeTimeCalendarService>{
    private FreeTimeCalendarService ftcService;

    public FreeTimeCalendarServiceTest() {
        super(FreeTimeCalendarService.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        FreeTimeBinder binder = (FreeTimeBinder)bindService(new Intent(getContext(), FreeTimeCalendarService.class));
        ftcService = binder.getService();
    }

    public void testFindEmptySlots() throws Exception {

        Calendar calStart = new GregorianCalendar(2014, 5, 11, 0, 0, 0);
        Calendar calEnd = new GregorianCalendar(2014, 5, 11, 23, 59, 0);
        //ftcService.createEvent("Test Event", calStart, calEnd);

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        EmptySlotDataSource ds = new EmptySlotDataSource(context);
        //ds.clearEmptySlotTable();
        ftcService.findUnoccupiedTimeSlots(calStart.getTimeInMillis(), calEnd.getTimeInMillis(), ds);

        Cursor cursor = ftcService.findEventByTitle("Recurring");
        cursor.moveToFirst();

        String rrule = cursor.getString(2);

        System.out.println(rrule);
    }

    public void testFindEvents() throws Exception {
        long ftCalId = ftcService.getFreeTimeCalendarId();

        Calendar calStart = new GregorianCalendar(2014, 5, 11, 0, 0, 0);
        Calendar calEnd = new GregorianCalendar(2014, 5, 11, 23, 59, 0);

        String selection = CalendarContract.Instances.CALENDAR_ID + " = ? ";
        String[] selArgs = new String[]{String.valueOf(ftCalId)};

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, calStart.getTimeInMillis());
        ContentUris.appendId(builder, calEnd.getTimeInMillis());
        Cursor cursor = ftcService.getContentResolver().query(builder.build(), null, selection, selArgs, null);


        if(cursor.moveToFirst()) {
            do{
                String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE));
                long startTime = cursor.getLong(cursor.getColumnIndex(CalendarContract.Instances.BEGIN));
                long endTime = cursor.getLong(cursor.getColumnIndex(CalendarContract.Instances.END));
                long calID = cursor.getLong(cursor.getColumnIndex(CalendarContract.Instances.CALENDAR_ID));

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

                String dateInfo = "title: " + title + " calID = " + calID
                        +  " start : "  + dateFormatter.format(new Date(startTime))
                        +  " end : " + dateFormatter.format(new Date(endTime));


                Log.i("FreeTime", "event info : " + dateInfo);

            } while(cursor.moveToNext());
        }

        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}