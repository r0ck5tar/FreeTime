package fr.unice.polytech.freetime.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;


public class ImportCalendarActivity extends Activity {
    private FreeTimeCalendarService ftcService;
    private boolean bound = false;
    private Cursor calCursor;
    private ListView calendarList;

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
        setContentView(R.layout.activity_import_calendar);
        do {
            bound = getApplicationContext().bindService(new Intent(getApplicationContext(), FreeTimeCalendarService.class), ftcServiceConnection, Context.BIND_AUTO_CREATE);
        } while(bound == false);
        calCursor = ftcService.getAllCalendars();
        System.out.println("about to crash");
        int[] to = new int[] {R.id.calendar_name, R.id.account_name};
        final CursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.calendar_info, calCursor, calCursor.getColumnNames(), to, 0);

        calendarList = (ListView)findViewById(R.id.calendarListView);
        calendarList.setAdapter(adapter);
        calendarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) calendarList.getItemAtPosition(position);

                String calendarName = cursor.getString(cursor.getColumnIndexOrThrow("calendar_name"));

                Toast.makeText(getApplicationContext(), "Selected calendar " + calendarName, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.import_calendar, menu);
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

    public void onCalendarItemClick() {

    }
}
