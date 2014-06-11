package fr.unice.polytech.freetime.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;


public class RecurenteEvent extends ActionBarActivity {

    private User user;
    private ArrayList<String> tabDay= new ArrayList<String>();
    private TimePicker start;
    private TimePicker end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurente_event);
        user = (User) getIntent().getSerializableExtra("userParam");

        tabDay.add("MONDAY");
        tabDay.add("TUESDAY");
        tabDay.add("WEDNESDAY");
        tabDay.add("THURSDAY");
        tabDay.add("FRIDAY");
        Spinner spinner = (Spinner) findViewById(R.id.spinner_day);
        // spinner.ad

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tabDay);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        start= (TimePicker) findViewById(R.id.timePicker_start);
        end= (TimePicker) findViewById(R.id.timePicker_end);
        start.setIs24HourView(true);
        end.setIs24HourView(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recurente_event, menu);
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

    public void onClick_okValid(View view){
        user.setSetDailiesAct(true);
        int step=user.getStep();
        user.setStep(++step);
        Intent intent= new Intent(this, FirstParam2.class);
        intent.putExtra("user",user);
        startActivity(intent);

    }
}
