package fr.unice.polytech.freetime.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FirstParam2 extends ActionBarActivity {

   // private int step;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_param2);

        user= (User) getIntent().getSerializableExtra("user");


        int step = user.getStep();
        switch (step){
            case 1:LinearLayout l1= (LinearLayout) findViewById(R.id.ll1);
                l1.setVisibility(View.VISIBLE);
                if(user.isImportCal()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button1);
                    ib.setVisibility(View.VISIBLE);

                }
                LinearLayout l21= (LinearLayout) findViewById(R.id.ll2);
                l21.setVisibility(View.VISIBLE);
                break;

            case 2:
                LinearLayout l12= (LinearLayout) findViewById(R.id.ll1);
                l12.setVisibility(View.VISIBLE);
                LinearLayout l2= (LinearLayout) findViewById(R.id.ll2);
                l2.setVisibility(View.VISIBLE);
                if(user.isImportCal()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button1);
                    ib.setVisibility(View.VISIBLE);
                }
                if(user.isSetFreeTime()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button2);
                    ib.setVisibility(View.VISIBLE);

                }
                LinearLayout l32= (LinearLayout) findViewById(R.id.ll3);
                l32.setVisibility(View.VISIBLE);
                break;

            case 3:
                LinearLayout l13= (LinearLayout) findViewById(R.id.ll1);
                l13.setVisibility(View.VISIBLE);
                LinearLayout l23= (LinearLayout) findViewById(R.id.ll2);
                l23.setVisibility(View.VISIBLE);
                LinearLayout l3= (LinearLayout) findViewById(R.id.ll3);
                l3.setVisibility(View.VISIBLE);
                if(user.isImportCal()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button1);
                    ib.setVisibility(View.VISIBLE);
                }
                if(user.isSetFreeTime()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button2);
                    ib.setVisibility(View.VISIBLE);
                }
                if(user.isSetDailiesAct()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button3);
                    ib.setVisibility(View.VISIBLE);
                }
                LinearLayout l43= (LinearLayout) findViewById(R.id.ll4);
                l43.setVisibility(View.VISIBLE);

                break;

            case 4:
                LinearLayout l14= (LinearLayout) findViewById(R.id.ll1);
                l14.setVisibility(View.VISIBLE);
                LinearLayout l24= (LinearLayout) findViewById(R.id.ll2);
                l24.setVisibility(View.VISIBLE);
                LinearLayout l34= (LinearLayout) findViewById(R.id.ll3);
                l34.setVisibility(View.VISIBLE);
                LinearLayout l4= (LinearLayout) findViewById(R.id.ll4);
                l4.setVisibility(View.VISIBLE);
                if(user.isImportCal()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button1);
                    ib.setVisibility(View.VISIBLE);
                }
                if(user.isSetFreeTime()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button2);
                    ib.setVisibility(View.VISIBLE);
                }
                if(user.isSetDailiesAct()){
                ImageButton ib= (ImageButton) findViewById(R.id.done_button3);
                ib.setVisibility(View.VISIBLE);
                }

                if(user.isNotificationParam()){
                    ImageButton ib= (ImageButton) findViewById(R.id.done_button4);
                    ib.setVisibility(View.VISIBLE);
                }

            default:break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_param2, menu);
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


    public void onClick_text(View view){
        TextView textName= (TextView) findViewById(R.id.editText_name);
        String name= textName.getText().toString();
            if(name.equals("Enter your name")) {
                textName.setText("");
            }
    }

    public void onClick_next(View view){
        int step= user.getStep();
        switch (step){
            case 0:
                EditText nameEdit= (EditText) findViewById(R.id.editText_name);

                String name= nameEdit.getText().toString();
                if(name.equals("Enter your name") || name.equals("")){
                    Toast.makeText(getApplicationContext(), "name doesn't valid",3).show();
                }else {
                    user.setName(name);
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.ll1);
                    l1.setVisibility(view.VISIBLE);
                    user.setStep(++step);
                }
                break;

            case 1:
                LinearLayout l2= (LinearLayout) findViewById(R.id.ll2);
                l2.setVisibility(View.VISIBLE);user.setStep(++step);break;

            case 2:LinearLayout l3=(LinearLayout) findViewById(R.id.ll3);
                l3.setVisibility(View.VISIBLE);
                user.setStep(++step);break;

            case 3:LinearLayout l4= (LinearLayout) findViewById(R.id.ll4);
            l4.setVisibility(View.VISIBLE);
                user.setStep(++step);break;

            //prochaine etape
            case 4:;break;

            default:break;
        }
    }


    public void onClick_import(View view){
        Intent intent= new Intent(this, ImportCalendar.class);

        intent.putExtra("userParam",user);
        startActivity(intent);
    }
}
