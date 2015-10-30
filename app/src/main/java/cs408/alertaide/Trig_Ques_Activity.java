package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cs408.alertaide.backend.AAException;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class Trig_Ques_Activity extends Activity {
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trig__ques);
        linear = (LinearLayout) findViewById(R.id.linear);
        try {
            AA_Manager myManager = new AA_Manager(this);
            JSONObject tq = myManager.getTQs("pphem");
            askQuestion(tq);
        } catch (AAException e) {
           throwError(e.getMessage());
        }

    }

    private void askQuestion(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject value = (JSONObject) json.get(key);
                String question = value.getString("question");
                askYesNo(question);


            } catch (JSONException e) {
                throwError(e.getMessage());

            }
        }
    }

    private void throwError(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void askYesNo(String question) {
        //TODO: add yes no through app_data xml (JSONARRAY)
        TextView myText = new TextView(this);
        myText.setText(question);
        linear.addView(myText);
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button yes = new Button(this);
        Button no = new Button (this);

        yes.setText("Yes");
        no.setText("No");


        buttonLayout.addView(yes);
        buttonLayout.addView(no);

        linear.addView(buttonLayout);


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
