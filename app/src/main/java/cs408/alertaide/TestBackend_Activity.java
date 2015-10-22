package cs408.alertaide;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs408.alertaide.backend.AA_Manager;

/**
 * Created by Negatu on 10/11/15.
 */
public class TestBackend_Activity extends Activity {

    LinearLayout myLayout;
    TextView titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbackend);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        titleView = new TextView(this);
        titleView.setText("Testing AA Backend");
        myLayout.addView(titleView);

        //readInFile();
        TextView viewConds = new TextView(this);
        try {
            AA_Manager myManager = new AA_Manager(this);
            JSONObject myPMs = myManager.get_PMs("pphem");
            viewConds.setText(myPMs.toString());
        } catch (Exception e) {
            viewConds.setText(e.getMessage());
        }
        myLayout.addView(viewConds);

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
