package fr.unice.polytech.appyoann;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import fr.unice.polytech.calendarmodule.EventBuilder;
import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;
import fr.unice.polytech.calendarmodule.RecurrenceStringBuilder;
import fr.unice.polytech.entities.Task;
import fr.unice.polytech.entities.TaskBuilder;
import fr.unice.polytech.entities.WrongEndTaskException;
import fr.unice.polytech.entities.WrongStartTaskException;


public class MainActivity extends Activity {
    private FreeTimeCalendarService ftcService;
    private long idCalendar;
    private static final int INIT_ID_CALENDAR = 0;


    private ServiceConnection ftcServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            ftcService = ((FreeTimeCalendarService.FreeTimeBinder)service).getService();

            Toast.makeText(getApplicationContext(), "@string/service_connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ftcService = null;

            Toast.makeText(getApplicationContext(), "@string/service_disconnected", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idCalendar=INIT_ID_CALENDAR;
        bindService(new Intent(getApplicationContext(), FreeTimeCalendarService.class), ftcServiceConnection, Context.BIND_AUTO_CREATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(ftcServiceConnection);

    }

    /**
     * Test 1 : Recurrence Events construct with RecurrenceStringBuilder
     */
    private static final String DELETE_TEST_1 = "DELETE_TEST_1";
    private ArrayList<Long> eventsTest1;
    public ArrayList<String> init_rrule_test_1(){
        return null;
        //todo
    }
    public void onClickTest_1(View view){
        //First we delete the test 1
        onClickDelTest_1(view);

        //Second Call de free time calendar if it isn't
        if(idCalendar == INIT_ID_CALENDAR) {
            System.out.println("Reading Calendar");
            idCalendar = ftcService.getFreeTimeCalendarId();
            System.out.println(idCalendar);
        }

/*
        EventBuilder test1= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
        EventBuilder result= new EventBuilder(idCalendar);
*/


        EventBuilder result= new EventBuilder(idCalendar);
        result.createEvent("TEST");
        result.startY(2014).startM(5).startD(15).startH(8).finalizeStartTime();
        result.description("toto");
        result.timeZone(TimeZone.getDefault().getID());
        //result.rRule(new RecurrenceStringBuilder().freqByDay().repetition(10).getRRule());
        result.rRule("FREQ=DAILY;BYHOUR=8,16;COUNT=5");
        result.duration("PT15M");
        eventsTest1.add(result.finalizeEvent(getContentResolver()));

    }

    public void onClickDelTest_1(View view){
        if(idCalendar == INIT_ID_CALENDAR){
            return;
        }
        for(Long eventID : eventsTest1){
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            int rows = getContentResolver().delete(deleteUri, null, null);
            Log.i(DELETE_TEST_1, "Rows deleted: " + rows);
        }
    }

    public GregorianCalendar stringToCalendar(String s){
        String[] d = s.split("/");
        return new GregorianCalendar(Integer.parseInt(d[2]),Integer.parseInt(d[1])-1,Integer.parseInt(d[0]));
    }
}
