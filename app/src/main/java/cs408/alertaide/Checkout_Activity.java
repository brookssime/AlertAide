package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Iterator;

import cs408.alertaide.backend.AA_Manager;


public class Checkout_Activity extends Activity {

    LinearLayout myLayout;
    AA_Manager myManager;
    String myCondition;
    String myFile;
    JSONObject myPMJson;

    int nextCE;
    LinearLayout.LayoutParams layoutParams;

    TitleView myTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        myLayout.setPadding(0, 100, 0, 100);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 75, 25, 75);

        myTitle = new TitleView(this, "CHECK OUT");
        myLayout.addView(myTitle, layoutParams);

        try {

            myManager = new AA_Manager(this);
            myPMJson = myManager.getPMs(myCondition);
            createCheckout();
        } catch (Exception e) {
            throw_Error("Failed to start Patient Management \n" + e.getMessage());
        }
    }

    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void createCheckout() {

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

    /**
     * Called when the user clicks any button on Check out page, ends the session in app
     */
    public void exit(View view) {
        switch (view.getId()) {/*
            case R.id.contact_treatment:


                break;

            case R.id.contact_notreatment:
                // Code for button 3 click
                break;

            case R.id.nocontact_treatment:
                // Code for button 3 click
                break;

            case R.id.nocontact_notreatment:
                // Code for button 3 click
                break;
        }*/


        }

    }
}