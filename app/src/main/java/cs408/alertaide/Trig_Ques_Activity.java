package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
    //int [] check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trig__ques);
        linear = (LinearLayout) findViewById(R.id.linear);
        try {
            AA_Manager myManager = new AA_Manager(this);
            JSONObject tq = myManager.getTQs("pphem");
            ask_Question(tq);
        } catch (AAException e) {
            throw_Error(e.getMessage());
        }

    }


    private void throw_Error(String errorMessage) {

        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void set_Question_Layout(String question) {
        TextView myText = new TextView(this);
        myText.setText(question);
        linear.addView(myText);
    }

    private void set_Answer_Layout(JSONArray answer) throws JSONException {
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < answer.length(); i++) {
            Button myButton = new Button(this);
            String buttonText = answer.getString(i);
            myButton.setText(buttonText);
            buttonLayout.addView(myButton);
        }

        linear.addView(buttonLayout);
    }

    private void finish_TQ() {
        Button done = new Button(this);
        done.setText("Done");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_pmanagement();
            }
        });
        linear.addView(done);
    }

        /**
         * Goes through maximum of two nested linear layouts, only checks Yes No answers.
         * If click Done Button goes to patient management.
         * @param layout
         */
    public void is_Clicked(LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) v).getChildCount(); j++) {

                    View k = ((LinearLayout) v).getChildAt(j);
                    if (k instanceof Button) {
                        if (((Button) k).getText() == "Yes") {

                            k.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View k) {
                                       k.setBackgroundColor(Color.parseColor("#00FF00"));
                                    //TODO: Log choice
                                }
                            });
                        }

                        if (((Button) k).getText() == "No")

                        {
                            k.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View k) {
                                    k.setBackgroundColor(Color.parseColor("#ff0000"));
                                    //TODO: Log choice
                                }
                            });
                        }


                    }
                }
            }









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
                set_Answer_Layout(answer);





            } catch (JSONException e) {
                throw_Error(e.getMessage());

            }
        }
         is_Clicked(linear);
         finish_TQ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trig__ques, menu);
        return true;
    }

    private void goto_pmanagement(){
        Intent intent = new Intent(this, PManagement_Activity.class);
        Bundle extras = new Bundle();
        startActivity(intent);
    }

}
