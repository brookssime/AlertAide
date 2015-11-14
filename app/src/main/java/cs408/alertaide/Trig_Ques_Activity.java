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

import java.util.Iterator;


public class Trig_Ques_Activity extends Activity {
    LinearLayout linear;
    JSONObject tq;
    JSONObject tqAnswers;
    AA_Manager myManager;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_trig__ques);
            linear = (LinearLayout) findViewById(R.id.linear);
            myBundle = getIntent().getExtras();

            tqAnswers = new JSONObject();



            if (myBundle.getString("condition") == null || myBundle.getString("file") == null) {
                throw_Error("You have not chosen an appropriate condition.");
            }


            try {
                myManager = new AA_Manager(this);
                String condition = myBundle.getString("condition");
                tq = myManager.getTQs(condition);
                ask_Question(tq);
            } catch (AAException e) {
                throw_Error(e.getMessage());
            }
        }
        catch(Exception e){
            throw_Error(e.getMessage());
        }

    }

    private void ask_Question(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject value = (JSONObject) json.get(key);
                String question = value.getString("question");
                JSONArray answer = value.getJSONArray("answer_options");
                set_Question_Layout(question);
                set_Answer_Layout(question,answer);
            } catch (JSONException e) {
                throw_Error(e.getMessage());

            }
        }
        //isClicked(linear);
        finish_TQ();
    }


    private void throw_Error(String errorMessage) {

        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

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

    private void finish_TQ() {
        Button done = new Button(this);
        done.setText("Done");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    myManager.logInfo(myBundle.getString("file"), "tqAnswers", tqAnswers);
                    int clinicalExpertID = 0;
                    myManager.send_Initial_SMS(myBundle.getString("file"), clinicalExpertID);
                    goto_pmanagement();
//                    endTime();

                } catch (Exception e) {
                    throw_Error(e.getMessage());
                }


            }
        });
        linear.addView(done);
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
