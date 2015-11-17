package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.*;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Condition_Activity extends Activity {

    private String selectedCondition;
    private String logFileID;
    private LinearLayout myLayout;
    private AA_Manager myManager;
    private AAView promptView;
    private Button myButton;
    JSONObject myCondition;

    private LinearLayout.LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        myCondition = new JSONObject();

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        LinearLayout myLinear = new LinearLayout(this);
        myLinear.setOrientation(LinearLayout.VERTICAL);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 75, 25, 75);

        promptView = new AAView(this, "SELECT A CONDITION", 1);
        myLayout.addView(promptView, layoutParams);

        try {
            myManager = new AA_Manager(this);
        } catch (Exception e) {
            throwError("Failed to load AA Manager \n" + e.getMessage());
        }

        try {
            JSONArray conditionsJA = myManager.getConditions();
            String[] nameArray = new String[conditionsJA.length()];

            for(int i=0; i<conditionsJA.length(); i++) {
                nameArray[i] = conditionsJA.getString(i);
            }

            for(int i=0; i<nameArray.length; i++) {
                myButton = new Button(this);
                String text = nameArray[i];
                myButton.setText(text);
                myButton.setPadding(20, 30, 20, 30);
                myButton.setTextColor(Color.BLACK);
                myButton.setTextSize(20);
                myButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button rb = (Button) view;
                        selectedCondition = (String) rb.getText();
                        goto_trigger_questions();
                    }
                });
                myLinear.addView(myButton);
            }


            myLayout.addView(myLinear, layoutParams);

        } catch (Exception e) {
            throwError("Failed to create list of conditions\n" + e.getMessage());
        }


    }

    private void timeStamp () throws JSONException {
        Long end = System.currentTimeMillis();
        String time = end.toString();
        myCondition.put("timeStamp", time);

    }


    private void goto_trigger_questions(){
        try {
            //Logging
            timeStamp();
            myCondition.put("conditionName", selectedCondition);


            //selectedCondition = mySpinner.getSelectedItem().toString();
            logFileID = myManager.getLogSession(selectedCondition);

            //myManager.logInfo(logFileID, "condition", myCondition );
            /*Toast.makeText(Condition_Activity.this,
                    myCondition.toString(), Toast.LENGTH_LONG).show();*/
            Intent intent = new Intent(this, Trig_Ques_Activity.class);
            Bundle extras = new Bundle();
            extras.putString("condition", selectedCondition);
            extras.putString("file", logFileID);
            intent.putExtras(extras);
            startActivity(intent);
        } catch (Exception e) {
            throwError("Failed to select the condition \n" +  e.getMessage());
        }


    }

    private void throwError(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_condition, menu);
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
