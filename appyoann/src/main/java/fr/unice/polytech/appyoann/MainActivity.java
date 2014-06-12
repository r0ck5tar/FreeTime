package fr.unice.polytech.appyoann;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;
import fr.unice.polytech.taskmodule.Task;
import fr.unice.polytech.taskmodule.WrongEndTaskException;
import fr.unice.polytech.taskmodule.WrongStartTaskException;


public class MainActivity extends Activity {
    private FreeTimeCalendarService ftcService;
    private long idCalendar;


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

    public void onClickCreate(View view){
        //Appel de free time calendar
        System.out.println("Reading Calendar");
        idCalendar = ftcService.getFreeTimeCalendarId();
        System.out.println(idCalendar);

        //Recherche du type choisi
        String chosen = ((RadioButton)findViewById(((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId())).getText().toString();
        System.out.println("Chosen "+ chosen);
        Task.TASK_TYPE type =Task.stringToEnum(chosen);

        EditText title = (EditText)findViewById(R.id.titleEditText);
        if(title.getText() == null || "".equals(title.getText().toString())){
            ((TextView)findViewById(R.id.errorView)).setTextColor(Color.RED);
            ((TextView)findViewById(R.id.errorView)).setText("Put a title plz");
            return;
        }else{
            System.out.println("#"+((EditText)findViewById(R.id.titleEditText)).getText());
            ((TextView)findViewById(R.id.errorView)).setText("");
        }
        boolean allday = (((CheckBox)findViewById(R.id.allDayCheckBox)).isChecked())? true:false;
        String beginT = ((EditText)findViewById(R.id.editText)).getText().toString();
        String endT = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String desc = ((EditText)findViewById(R.id.editText3)).getText().toString();

        System.out.println(beginT+" to "+endT);
        System.out.println(desc);

        Task result= new Task(type,title.getText().toString());
        result.setAllDay(allday);
        result.setDescription(desc);

        try {
            result.setTaskStart(stringToCalendar(beginT));
            result.setTaskEnd(stringToCalendar(endT));
        } catch (WrongStartTaskException e) {
            e.printStackTrace();
            return;
        } catch (WrongEndTaskException e) {
            e.printStackTrace();
            return;
        }

        long idEvent = result.publish(idCalendar,getContentResolver());
    }

    public GregorianCalendar stringToCalendar(String s){
        String[] d = s.split("/");
        return new GregorianCalendar(Integer.parseInt(d[2]),Integer.parseInt(d[1])-1,Integer.parseInt(d[0]));
    }
}
