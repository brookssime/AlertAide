package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs408.alertaide.backend.AA_Manager;
import org.json.JSONObject;


public class Login_Activity extends Activity {

    private AA_Manager myManager;
    private Button loginButton;
    private JSONObject hw_info;
    private EditText editName;
    private EditText editNumber;
    private EditText editCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            myManager = new AA_Manager(this);
        } catch (Exception e) {
            //TODO Brooks, implement what to show if the APP cannot load its backend DATA
        }
        if (myManager.check_HW_Info()){
            goto_conditions();
            //TODO add a line here that terminates this activity. I don't know how to do that yet. Or maybe it goes in the goto_conditions function.
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hw_info = new JSONObject();

        editName = (EditText) findViewById(R.id.editName);
        editNumber = (EditText) findViewById(R.id.editNumber);
        editCountry = (EditText) findViewById(R.id.editCountry);

        loginButton = (Button) findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String number = editNumber.getText().toString();
                String country = editCountry.getText().toString();

                if (name.equals("") || number.equals("") || country.equals("")){
                    //TODO Notify that the fields arenot complete
                    //TODO inaddition we may need to check the number for format.

                } else {
                    JSONObject infoFields = new JSONObject();
                    try {
                        infoFields.put("name", name);
                        infoFields.put("number", number);
                        infoFields.put("country", country);
                        hw_info.put("hw_info", infoFields);
                        Boolean saveInfo = myManager.put_HW_Info(hw_info);
                        if (!saveInfo) {
                            //TODO here, manager failed to save info.
                        }
                    } catch (Exception e) {
                        //TODO here, show that something failed with the creation of the JSON structures.
                    }

                    goto_conditions();
                }
            }
        });

        Button testButton = (Button) findViewById(R.id.testViewButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_testBackend();
            }
        });
    }

    private void goto_conditions(){
        Intent intent = new Intent(this, Condition_Activity.class);
        Bundle extras = new Bundle();
        //extras.putString("username", username);
        //extras.putString("password", password);
        //intent.putExtras(extras);
        startActivity(intent);
    }

    private void goto_testBackend(){
        Intent intent = new Intent(this, TestBackend_Activity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
