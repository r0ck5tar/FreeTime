package fr.unice.polytech.freetime.app;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;

public class MainActivity extends ActionBarActivity {
    private FreeTimeApplication app;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private EditText eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (FreeTimeApplication)getApplication();

        eventTitle = (EditText) findViewById(R.id.eventTitle);
        startDatePicker = (DatePicker) findViewById(R.id.startdatePicker);
        endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onCreateEventButtonClick(View view) {
        System.out.println("Creating Event");
        String title = eventTitle.getText().toString();
        int startDay = startDatePicker.getDayOfMonth();
        int startMonth = startDatePicker.getMonth();
        int startYear = startDatePicker.getYear();
        int endDay = endDatePicker.getDayOfMonth();
        int endMonth = endDatePicker.getMonth();
        int endYear = endDatePicker.getYear();

        System.out.println("Start date :" + startDay + "/"  + startMonth + "/" + startYear);
        System.out.println("End date :" + endDay + "/" + endMonth + "/" + endYear);

        app.getFtcService().createEvent(title, startYear, startMonth, startDay, endYear, endMonth, endDay);
    }

    public void onImportCalendarButton(View view){
        Intent intent = new Intent(getApplicationContext(), ImportCalendarActivity.class);
        startActivity(intent);
    }
}

