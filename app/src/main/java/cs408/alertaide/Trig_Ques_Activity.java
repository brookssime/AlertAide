package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cs408.alertaide.backend.AAException;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class Trig_Ques_Activity extends Activity {
    LinearLayout myLayout;
    JSONObject tq;
    JSONObject tqAnswers;
    AA_Manager myManager;
    Bundle myBundle;
    ArrayList<TQView> myTQViews;
    TitleView titleView;
    LinearLayout.LayoutParams tqPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_trig__ques);
            myLayout = (LinearLayout) findViewById(R.id.linear);
            myBundle = getIntent().getExtras();
            titleView = new TitleView(this, "Please Answer Questions");
            myLayout.addView(titleView);

            tqPadding = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tqPadding.setMargins(25, 30, 25, 30);

            tqAnswers = new JSONObject();
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
                throw_Error("Error in AskQ "+e.getMessage());
            }
        }
        finish_TQ();
    }


    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    /*
    private void set_Question_Layout(String question) {
        TextView myText = new TextView(this);
        myText.setText(question);
        myText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linear.addView(myText);
    }

    private void set_Answer_Layout(String question, JSONArray answer) throws JSONException {
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        try{
        for (int i = 0; i < answer.length(); i++) {
            AnswerButton myButton = new AnswerButton(this, question, answer.getString(i));
            String buttonText = answer.getString(i);
            myButton.setText(buttonText);
            myButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    AnswerButton b = (AnswerButton) view;
                    try {

                        tqAnswers.put(b.getKey(), b.getValue());
//                      text.setText(tqAnswers.toString());

                    } catch (JSONException e) {
                       throw_Error(e.getMessage());
                    }
                }
            });
            buttonLayout.addView(myButton);
        }}
        catch(Exception e){
            throw_Error(e.getMessage());
        }

        linear.addView(buttonLayout);
//        linear.addView(text);
    }
    */

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
                    myManager.logInfo(myBundle.getString("file"), "tqAnswers", tqAnswers);
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

    /**
     * Goes through maximum of two nested linear layouts, only checks Yes No answers.
     * If click Done Button goes to patient management.
     * @param
     */
//    public void isClicked(LinearLayout layout) {
//        for (int i = 0; i<layout.getChildCount(); i++){
//            View v = layout.getChildAt(i);
//            if (v instanceof LinearLayout){
//                for (int j = 0; j<((LinearLayout) v).getChildCount(); j++){
//                    View k = ((LinearLayout) v).getChildAt(j);
//                    if (k instanceof Button) {
//                        k.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View view) {
//                                Button b = (Button) view;
//                                try {
//                                    JSONObject question = ((LinearLayout) ).getChildAt(0);
//                                    tq.put("ans", b.getText().toString());
//                                } catch (JSONException e) {
//                                    throw_Error(e.getMessage());
//                                }
//                            }
//                        });
//                    }
//
//                }
//            }
//        }
//
//    }

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
