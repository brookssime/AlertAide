package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.Iterator;

import cs408.alertaide.backend.AA_Manager;


public class PManagement_Activity extends Activity {

    private LinearLayout myLayout;
    private AA_Manager myManager;
    private String myCondition;
    private String myFile;
    private JSONObject myPMJson;

    private int nextCE;
    private LinearLayout.LayoutParams layoutParams;

    private AAView myTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmanagement);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        myLayout.setPadding(0, 100, 0, 100);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 75, 25, 75);

        myTitle = new AAView(this, "PATIENT MANAGEMENT");
        myLayout.addView(myTitle, layoutParams);

        nextCE = 1;
        try {
            if (getIntent().getExtras().getString("condition") == null || getIntent().getExtras().getString("file") == null) {
                throw_Error("Failed to get condition and file ");
            } else {
                myCondition = getIntent().getExtras().getString("condition");
                myFile = getIntent().getExtras().getString("file");
            }
            myManager = new AA_Manager(this);
            myPMJson = myManager.getPMs(myCondition);
            createPMViews();
        } catch (Exception e){
            throw_Error("Failed to start Patient Management \n" + e.getMessage());
        }


        AAButton done = new AAButton(this, "DONE");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_checkout();
            }
        });
        myLayout.addView(done, layoutParams);

        AAButton newSms = new AAButton(this, "Send New SMS");
        newSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsAgain();
            }
        });
        myLayout.addView(newSms, layoutParams);

        AAButton callCE = new AAButton(this, "Call CE");
        callCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTheCE();
            }
        });
        myLayout.addView(callCE, layoutParams);
    }

    private void sendSmsAgain(){
        try {
            myManager.send_Initial_SMS(myFile, nextCE);
        } catch (Exception e){
            throw_Error(e.getMessage());
        }
    }

    private void callTheCE(){
        try {
            myManager.callCE(nextCE);
            nextCE++;
        } catch (Exception e){
            throw_Error(e.getMessage());
        }
    }

    private void createPMViews(){
        try {
            Iterator<String> iterator = myPMJson.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                JSONObject pm = myPMJson.getJSONObject(key);
                PMView pmView = new PMView(this, pm);
                myLayout.addView(pmView, layoutParams);
                //add view to layout and arraylist for log grabbing;
            }
        } catch (Exception e){
            throw_Error("Error while creating PM views \n" + e.getMessage());
        }
    }
    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void goto_checkout(){
        Intent intent = new Intent(this, Checkout_Activity.class);
        Bundle extras = new Bundle();
        //extras.putString("username", username);
        //extras.putString("password", password);
        //intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pmanagement, menu);
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


}
