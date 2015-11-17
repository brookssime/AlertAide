package cs408.alertaide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import cs408.alertaide.backend.AA_Manager;

import android.content.DialogInterface.OnClickListener;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class PManagement_Activity extends Activity {

    LinearLayout myLayout;
    ArrayList<PMView> myPMViews;
    AA_Manager myManager;
    String myCondition;
    String myFile;


    JSONObject myPMJson;

    JSONObject pmLog;
    JSONObject myLog;

    int nextCE;
    LinearLayout.LayoutParams layoutParams;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmanagement);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        myLayout.setPadding(0, 100, 0, 100);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 75, 25, 75);

        myPMViews = new ArrayList<>();

        nextCE = 1;
        myLog = new JSONObject();
        pmLog = new JSONObject();

        try {
            if (getIntent().getExtras().getString("condition") == null || getIntent().getExtras().getString("file") == null) {
                throw_Error("Failed to get condition and file!");
            } else {
                myCondition = getIntent().getExtras().getString("condition");
                myFile = getIntent().getExtras().getString("file");
            }
            myManager = new AA_Manager(this);
            myPMJson = myManager.getPMs(myCondition);
            createPMViews();
        } catch (Exception e){
            throw_Error("Failed to start Patient Management!\n" + e.getMessage());
        }


        AAButton done = new AAButton(this, "DONE");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                onDone();
            }


        });

        myLayout.addView(done, layoutParams);

        Button newSms = (Button) findViewById(R.id.newSms);
        newSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsAgain();
            }
        });

        Button callCE = (Button) findViewById(R.id.callCe);
        callCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTheCE();
            }
        });


    }

    //Double Checking that user is ready to exit Patient Management
    public void onDone() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Patient Management?")
                .setMessage("Are you sure you want to checkout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finishPM();
                    }
                }).create().show();
    }


    public void sendSmsAgain(){
        try {
            myManager.send_Initial_SMS(myFile, nextCE);
        } catch (Exception e){
            throw_Error(e.getMessage());
        }
    }

    public void callTheCE(){
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
                myPMViews.add(pmView);
                myLayout.addView(pmView, layoutParams);
            }
        } catch (Exception e){
            throw_Error("Error while creating PM views!\n" + e.getMessage());
        }
    }
    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void finishPM(){
        try {
            Long end = System.currentTimeMillis();
            String endTime = end.toString();
            myLog.put("endTimeStamp", endTime);

            for (int i=0; i<myPMViews.size(); i++){
                String label = myPMViews.get(i).getLabel();
                String isDone = myPMViews.get(i).getStatus();
                myLog.put(label, isDone);
            }

            pmLog.put("pmLog", myLog);
            myManager.logInfo(myFile, "pmLog", pmLog);
        } catch (Exception e){
            throw_Error("Error logging PM: \n"+e.getMessage());
        }
        goto_checkout();
    }

    private void goto_checkout(){
        Intent intent = new Intent(this, Checkout_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("file", myFile);
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
