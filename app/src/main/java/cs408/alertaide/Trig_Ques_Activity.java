package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class Trig_Ques_Activity extends Activity {

    LinearLayout myLayout;
    JSONObject tq;
    JSONObject tqLog;
    JSONObject tqAnswers;
    AA_Manager myManager;
    Bundle myBundle;
    ArrayList<TQView> myTQViews;
    LayoutParams tqPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_trig__ques);
            myLayout = (LinearLayout) findViewById(R.id.linear);

            myBundle = getIntent().getExtras();

            tqPadding = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            tqAnswers = new JSONObject();
            tqLog = new JSONObject();

            myTQViews = new ArrayList<>();

            if (myBundle.getString("condition") == null || myBundle.getString("file") == null) {
                throw_Error("You have not chosen an appropriate condition.");
            }

            try {
                startTime();
                myManager = new AA_Manager(this);
                String condition = myBundle.getString("condition");
                tq = myManager.getTQs(condition);
                ask_Question(tq);
            } catch (Exception e) {
                throw_Error("Error starting TQ: "+e.getMessage());
                Button condition = new Button(this);
                condition.setText("Go To Conditions");
                condition.setGravity(Gravity.CENTER);
                condition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goto_conditions();
                    }
                });
                LayoutParams error = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                myLayout.addView(condition, error);


            }
    }


    private void ask_Question(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject tq = (JSONObject) json.get(key);
                TQView tqView = new TQView(this, tq);
                myTQViews.add(tqView);
                myLayout.addView(tqView, tqPadding);
            } catch (JSONException e) {
                throw_Error("Error in method"+e.getMessage());


            }
        }
        finish_TQ();
    }


    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);

    }


    private void goto_conditions(){
        Intent intent = new Intent(this, Condition_Activity.class);
        Bundle extras = new Bundle();
        startActivity(intent);
    }

    private void finish_TQ() {
        AAButton done = new AAButton(this, "Done");


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    for(int i=0; i<myTQViews.size(); i++){
                        TQView tqView = myTQViews.get(i);
                        if (tqView.getIndex()!=-1) {
                            tqAnswers.put(tqView.getQuestion(), tqView.getAnswer());
                        }
                    }
                    tqLog.put("tqLog", tqAnswers);
                    myManager.logInfo(myBundle.getString("file"), "tqLog", tqAnswers);
                    int clinicalExpertID = 0;
                    myManager.send_Initial_SMS(myBundle.getString("file"), clinicalExpertID);
                    goto_pmanagement();
                    endTime();

                } catch (Exception e) {
                    throw_Error(e.getMessage());
                }


            }
        });
        myLayout.addView(done);
    }



    private void startTime() throws JSONException {
        Long start = System.currentTimeMillis();
        String startTime = start.toString();
        tqAnswers.put("startTimeStamp", startTime);
    }

    private void endTime() throws JSONException {
        Long end = System.currentTimeMillis();
        String endTime = end.toString();
        tqAnswers.put("endTimeStamp", endTime);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trig__ques, menu);
        return true;
    }

    private void goto_pmanagement(){
        Intent intent = new Intent(this, PManagement_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("condition", myBundle.getString("condition"));
        extras.putString("file", myBundle.getString("file"));
        intent.putExtras(extras);
        startActivity(intent);
    }

}
