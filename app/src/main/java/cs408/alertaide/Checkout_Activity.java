package cs408.alertaide;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cs408.alertaide.backend.AA_Manager;

import android.widget.TextView;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.Iterator;

import cs408.alertaide.backend.AA_Manager;

import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.Arrays;


public class Checkout_Activity extends Activity implements OnClickListener {


    LinearLayout myLayout;
    AA_Manager myManager;
    JSONObject myCheckout;
    LinearLayout.LayoutParams layoutParams;
    String checkoutanswer;
    Bundle myBundle;
    boolean ce_t;
    boolean noce_t;
    boolean noce_not;
    boolean ce_not;
    ImageButton ce_t_button;
    ImageButton noce_t_button;
    ImageButton noce_not_button;
    ImageButton ce_not_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myCheckout = new JSONObject();

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        myLayout.setPadding(0, 100, 0, 100);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 75, 25, 75);

        checkoutanswer = "default";
        ce_t = false;
        noce_t = false;
        noce_not = false;
        ce_not = false;




        ce_t_button = (ImageButton)findViewById(R.id.contact_treatment);
        ce_t_button.setOnClickListener(this); // calling onClick() method
        noce_t_button = (ImageButton)findViewById(R.id.nocontact_treatment);
        noce_t_button.setOnClickListener(this); // calling onClick() method
        ce_not_button = (ImageButton)findViewById(R.id.contact_notreatment);
        ce_not_button.setOnClickListener(this); // calling onClick() method
        noce_not_button = (ImageButton)findViewById(R.id.nocontact_notreatment);
        noce_not_button.setOnClickListener(this); // calling onClick() method

        setButtonColors();


    }




    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void timeStamp () throws JSONException {
        Long end = System.currentTimeMillis();
        String time = end.toString();
        myCheckout.put("timeStamp", time);

    }

    private void setButtonColors ()
    {
        ce_t_button.setBackgroundColor(Color.LTGRAY);
        ce_not_button.setBackgroundColor(Color.LTGRAY);
        noce_t_button.setBackgroundColor(Color.LTGRAY);
        noce_not_button.setBackgroundColor(Color.LTGRAY);

        ce_t = false;
        ce_not = false;
        noce_t = false;
        noce_not = false;
    }


    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..


        switch(v.getId())
        {

            case R.id.contact_treatment:
                if(ce_t)
                {
                    v.setBackgroundColor(Color.LTGRAY);
                    ce_t = false;
                }

                else {
                    setButtonColors();
                    v.setBackgroundColor(Color.GREEN);
                    ce_t = true;
                }
                checkoutanswer = "Successful CE Contact, Successful Patient Treatment";
                break;

            case R.id.contact_notreatment:
                // Code for button 3 click
                if(ce_not)
                {
                    v.setBackgroundColor(Color.LTGRAY);
                    ce_not = false;
                }

                else {
                    setButtonColors();
                    v.setBackgroundColor(Color.GREEN);
                    ce_not = true;

                }
                checkoutanswer = "Successful CE Contact, Unsuccessful Patient Treatment";

                break;

            case R.id.nocontact_treatment:
                // Code for button 3 click

                if(noce_t)
                {
                    v.setBackgroundColor(Color.LTGRAY);
                    noce_t = false;
                }

                else {
                    setButtonColors();
                    v.setBackgroundColor(Color.GREEN);
                    noce_t = true;

                }

                checkoutanswer = "Unsuccessful CE Contact, Successful Patient Treatment";
                break;

            case R.id.nocontact_notreatment:
                // Code for button 3 click
                if(noce_not)
                {
                    v.setBackgroundColor(Color.LTGRAY);
                    noce_not = false;
                }

                else {
                    setButtonColors();
                    v.setBackgroundColor(Color.GREEN);
                    noce_not = true;

                }
                checkoutanswer = "Unsuccessful CE Contact, Unsuccessful Patient Treatment";


                break;


        }

    }


    /** Called when the user clicks any button on Check out page, ends the session in app */
    public void exit(View view) throws JSONException {
        //AAButton done = new AAButton(this, "Done");

        //Logging
        try {
            timeStamp();
            myCheckout.put("selection", checkoutanswer);

            /* Checking Logging
            Toast.makeText(Checkout_Activity.this,
                    myCheckout.toString(), Toast.LENGTH_LONG).show(); */

            myManager.logInfo(myBundle.getString("file"), "checkOut", myCheckout);
        }
        catch (Exception e){
            throw_Error(e.getMessage());
        }


        //Sends it back to the conditions page
        Intent intent = new Intent(this, Condition_Activity.class);
        Bundle extras = new Bundle();
        startActivity(intent);


    }
}
