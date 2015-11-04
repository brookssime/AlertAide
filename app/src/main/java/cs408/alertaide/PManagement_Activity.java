package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cs408.alertaide.backend.AAException;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class PManagement_Activity extends Activity {
    LinearLayout linear;
    JSONObject pmAnswers;
    AA_Manager myManager;
    Bundle myBundle;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmanagement);
        linear = (LinearLayout) findViewByID(R.id.linear);
        myBundle = getIntent().getExtras();

        /*done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_checkout();
            }
        });*/

        try {
            myManager = new AA_Manager(this);
            String condition = myBundle.getString("condition");
            JSONObject pm = myManager.getPMs(condition);
            setPM(pm);

        }catch (AAException e) {
            throw_Error(e.getMessage());
        }
    }

    private void throw_Error(String errorMessage) {

        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }



    private void setPM(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject value = (JSONObject) json.get(key);
                String action =

            } catch (JSONException e) {
                throw_Error(e.getMessage());
            }
        }
    }




    private void goto_checkout(){
        Intent intent = new Intent(this, Checkout_Activity.class);
        Bundle extras = new Bundle();
        //extras.putString("username", username);
        //extras.putString("password", password);
        //intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pmanagement, menu);
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
