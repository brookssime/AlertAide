package cs408.alertaide;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cs408.alertaide.backend.AA_Log;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONObject;

/**
 * Created by Negatu on 10/11/15.
 */
public class TestBackend_Activity extends Activity {

    private LinearLayout myLayout;
    private TextView titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbackend);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        titleView = new TextView(this);
        titleView.setText("Testing AA Backend");
        myLayout.addView(titleView);

        testAA_Log();
    }

    private void testAA_Log() {
        final AA_Log myLogger = new AA_Log(this);

        LinearLayout line1 = new LinearLayout(this);
        line1.setOrientation(LinearLayout.HORIZONTAL);
        Button getID = new Button(this);
        getID.setText("GET FileName");
        final TextView showID = new TextView(this);
        getID.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         String viewTxt = new String();
                                         try {
                                             viewTxt = myLogger.createFile("sample");
                                         } catch (Exception e) {
                                             viewTxt = e.getMessage();
                                         }
                                         showID.setText( viewTxt);
                                     }
                                 }
        );
        line1.addView(getID);
        line1.addView(showID);
        myLayout.addView(line1);

        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(250, 150);
        LinearLayout line2 = new LinearLayout(this);
        line2.setOrientation(LinearLayout.HORIZONTAL);
        final EditText logFile = new EditText(this);
        final EditText contentKey = new EditText(this);
        final EditText contentValue = new EditText(this);
        logFile.setLayoutParams(lparams);
        contentKey.setLayoutParams(lparams);
        contentValue.setLayoutParams(lparams);
        Button logContent = new Button(this);
        logContent.setText("LOG");
        logContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myLogger.logToFile(logFile.getText().toString(), contentKey.getText().toString(), contentValue.getText().toString());
                } catch (Exception e) {
                    titleView.setText(e.getMessage());
                }
            }
        });
        line2.addView(logContent);
        line2.addView(logFile);
        line2.addView(contentKey);
        line2.addView(contentValue);
        myLayout.addView(line2);

        LinearLayout line3 = new LinearLayout(this);
        line3.setOrientation(LinearLayout.HORIZONTAL);
        final TextView content = new TextView(this);
        final EditText fileName = new EditText(this);
        fileName.setLayoutParams(lparams);
        Button viewContent = new Button(this);
        viewContent.setText("VIEW");
        viewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    content.setText(myLogger.readFile(fileName.getText().toString()));
                } catch (Exception e) {
                    content.setText(e.getMessage());
                }
            }
        });
        line3.addView(viewContent);
        line3.addView(fileName);
        myLayout.addView(line3);
        myLayout.addView(content);
    }

    private void testAA_Data(){
        TextView viewConds = new TextView(this);
        try {
            AA_Manager myManager = new AA_Manager(this);
            JSONObject myPMs = myManager.getPMs("pphem");
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
