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
import android.widget.Button;
import android.widget.LinearLayout;
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

    AAView myTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myCheckout = new JSONObject();

        checkoutanswer = "default";

        ImageButton btn = (ImageButton)findViewById(R.id.contact_treatment);
        btn.setOnClickListener(this); // calling onClick() method
        ImageButton btn1 = (ImageButton)findViewById(R.id.nocontact_treatment);
        btn1.setOnClickListener(this); // calling onClick() method
        ImageButton btn2 = (ImageButton)findViewById(R.id.contact_notreatment);
        btn2.setOnClickListener(this); // calling onClick() method
        ImageButton btn3 = (ImageButton)findViewById(R.id.nocontact_notreatment);
        btn3.setOnClickListener(this); // calling onClick() method


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


    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..


        switch(v.getId())
        {
            case R.id.contact_treatment:
                if(v.getBackground().equals(Color.GREEN))
                {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }

                else {
                    v.setBackgroundColor(Color.GREEN);
                }
                checkoutanswer = "Successful CE Contact, Successful Patient Treatment";

                break;

            case R.id.contact_notreatment:
                // Code for button 3 click
                v.setBackgroundColor(Color.GREEN);
                checkoutanswer = "Successful CE Contact, Unsuccessful Patient Treatment";

                break;

            case R.id.nocontact_treatment:
                // Code for button 3 click
                v.setBackgroundColor(Color.GREEN);
                checkoutanswer = "Unsuccessful CE Contact, Successful Patient Treatment";
                break;

            case R.id.nocontact_notreatment:
                // Code for button 3 click
                v.setBackgroundColor(Color.GREEN);
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
