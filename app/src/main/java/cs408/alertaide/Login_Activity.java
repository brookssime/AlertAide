package cs408.alertaide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONObject;


public class Login_Activity extends Activity {

    private AA_Manager myManager;
    private ImageButton loginButton;
    private EditText editName;
    private EditText editLocation;
    private EditText editHospital;
    private TextView welcome;
    private RelativeLayout myLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myLayout = (RelativeLayout) findViewById(R.id.myLayout);

        try {
            myManager = new AA_Manager(this);
        } catch (Exception e) {
            //TODO Brooks, implement what to show if the APP cannot load its backend DATA
        }



        if (myManager.check_HW_Info()){
            goto_conditions();
            finish();

            //TODO add a line here that terminates this activity. I don't know how to do that yet. Or maybe it goes in the goto_conditions function.
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = (TextView) findViewById(R.id.SignUpView);
        Typeface typeFace= FontLoader.getTypeFace(this,"bebas");
        if(typeFace!= null) {
            welcome.setTypeface(typeFace);
        }

        editName = (EditText) findViewById(R.id.editName);
        editLocation = (EditText) findViewById(R.id.editLocation);
        editHospital = (EditText) findViewById(R.id.editHospital);

        loginButton = (ImageButton) findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String location = editLocation.getText().toString();
                String hospital = editHospital.getText().toString();

                TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String phoneNumber = tMgr.getLine1Number();

                if (name.equals("") || location.equals("") || hospital.equals("")){
                    throwError("Please complete all fields");
                } else {
                    JSONObject infoFields = new JSONObject();
                    try {
                        infoFields.put("name", name);
                        infoFields.put("number", phoneNumber);
                        infoFields.put("location", location);
                        infoFields.put("hospital", hospital);
                        Boolean saveInfo = myManager.put_HW_Info(infoFields);
                        if (!saveInfo) {
                            //TODO here, manager failed to save info.
                        }
                        goto_conditions();

                    } catch (Exception e) {
                        //TODO here, show that something failed with the creation of the JSON structures.
                    }


                }
            }
        });


    }

    private void throwError(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }

    private void goto_conditions(){
        Intent intent = new Intent(this, Condition_Activity.class);
        Bundle extras = new Bundle();
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
