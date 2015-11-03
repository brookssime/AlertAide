package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cs408.alertaide.backend.AA_Data;
import cs408.alertaide.backend.AA_Manager;


public class Condition_Activity extends Activity {

    private String selectedCondition;
    private String logFileID;
    private Spinner mySpinner;
    private LinearLayout myLayout;
    private AA_Manager myManager;
    private TextView promptView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        promptView = new TextView(this);
        promptView.setText("PLEASE SELECT A CONDITION");
        myLayout.addView(promptView);

        try {
            myManager = new AA_Manager(this);
        } catch (Exception e) {
            throwError("Failed to load AA Manger \n" + e.getMessage());
        }

        try {
            JSONArray conditionsJA = myManager.getConditions();
            String[] spinnerArray = new String[conditionsJA.length()];
            for(int i=0; i<conditionsJA.length(); i++) {
                spinnerArray[i] = conditionsJA.getString(i);
            }
            mySpinner = new Spinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(spinnerArrayAdapter);

            Button selectButton = new Button(this);
            selectButton.setText("Select Condition");
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goto_trigger_questions();
                }
            });

            LinearLayout selectLayout = new LinearLayout(this);
            selectLayout.setOrientation(LinearLayout.HORIZONTAL);
            selectLayout.addView(mySpinner);
            selectLayout.addView(selectButton);
            myLayout.addView(selectLayout);

        } catch (Exception e) {
            throwError("Failed to create list of conditions \n" + e.getMessage());
        }


    }

    private void goto_trigger_questions(){
        try {
            selectedCondition = mySpinner.getSelectedItem().toString();
            logFileID = myManager.getLogSession();
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
