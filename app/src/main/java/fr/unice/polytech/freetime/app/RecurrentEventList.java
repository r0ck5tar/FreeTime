package fr.unice.polytech.freetime.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RecurrentEventList extends Activity {

    private HashMap<String,event> events;
    private ListAdapter adapter;
    private List<String> titleEvent;
    private event eventSelected;
    private List<event> eventList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurrent_event_list);
        events= new HashMap<String,event>();
        titleEvent=new ArrayList<String>();


            user = (User) getIntent().getSerializableExtra("userParam");


            eventList = user.getEvents();

            for (event e : eventList) {
                events.put(e.getTitle(), e);
                titleEvent.add(e.getTitle());
            }



        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, titleEvent);
        ListView list = (ListView) findViewById(R.id.listview_event);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String titleSelected = titleEvent.get(arg2);
                eventSelected= events.get(titleSelected);
            }

        });
        Button button_remove=(Button) findViewById(R.id.button_remove_event);
        button_remove.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recurrent_event_list, menu);
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

    public void onClick_remove_event(View view){
        events.remove(eventSelected.getTitle());
        eventList.remove(eventSelected);
        titleEvent.remove(eventSelected.getTitle());
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,titleEvent);
        ListView list = (ListView) findViewById(R.id.listview_event);
        list.setAdapter(adapter);
    }

    public void onClick_add_event(View view){
        Intent intent= new Intent(this,RecurenteEvent.class);
        user.setEvents(eventList);
        intent.putExtra("userParam", user);
        startActivity(intent);
    }

    public void onClick_finish(View view){
        user.setSetDailiesAct(true);
        int step=user.getStep();
        user.setStep(++step);
        Intent intent= new Intent(this,FirstParam2.class);
        user.setEvents(eventList);
        intent.putExtra("user", user);
        startActivity(intent);
    }


}
