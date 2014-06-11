package fr.unice.polytech.freetime.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurrent_event_list);
        events= new HashMap<String,event>();
        titleEvent=new ArrayList<String>();
        eventList= (List<event>) getIntent().getSerializableExtra("events");
        for(event e:eventList){
            events.put(e.getTitle(),e);
            titleEvent.add(e.getTitle());
        }

        adapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,titleEvent);
        ListView list= (ListView) findViewById(R.id.listview_event);
        list.setAdapter(adapter);

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
    }

    public void onClick_add_event(View view){
        Intent intent= new Intent(this,RecurenteEvent.class);
        intent.putExtra("listEvent", (java.io.Serializable) eventList);
        startActivity(intent);
    }

    public void onClick_finish(View view){

    }

    public void onClick_select_item_event(View view){

    }

}
