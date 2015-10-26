package cs408.alertaide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import cs408.alertaide.backend.AAException;
import cs408.alertaide.backend.AA_Data;
import cs408.alertaide.backend.AA_Log;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONObject;


public class Login_Activity extends Activity {
    Button loginButton;
    AA_Manager manager;AA_Data myData;AA_Log myLog;




    protected void Login_Activity(Context context) throws AAException {
        manager = new AA_Manager(context);
        myData = new AA_Data(context);
        myLog = new AA_Log(context);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (manager.check_HW_Info()){
            goto_conditions();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_conditions();
                //Set info in backend
                manager.put_HW_Info(JSONObject info);

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
