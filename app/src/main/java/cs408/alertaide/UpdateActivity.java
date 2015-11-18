package cs408.alertaide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import cs408.alertaide.backend.AA_Log;
import cs408.alertaide.backend.AA_Manager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;


public class UpdateActivity extends Activity {
    private LinearLayout myLayout;
    private AAView statusView;

    private LinearLayout.LayoutParams layoutParams;

    private String defaultURL = "http://www.alertaide.com/wp-content/uploads/2015/11/app_data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 75, 15, 75);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
        layoutParams2.setMargins(15, 75, 15, 75);

        statusView = new AAView(this, "Press to update or to restore app data", 1);
        myLayout.addView(statusView, layoutParams);


        LinearLayout updateBox = new LinearLayout(this);
        updateBox.setOrientation(LinearLayout.HORIZONTAL);

        UpdateButton updateMe = new UpdateButton(this, "Update Data");
        updateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
        updateBox.addView(updateMe, layoutParams2);

        UpdateButton sysRestore = new UpdateButton(this, "Restore Original");
        sysRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreData();
            }
        });
        updateBox.addView(sysRestore, layoutParams2);

        myLayout.addView(updateBox);

        final EditText linkInput = new EditText(this);
        linkInput.setHintTextColor(Color.WHITE);
        linkInput.setTextColor(Color.WHITE);
        linkInput.setText("http://");
        myLayout.addView(linkInput);

        final UpdateButton updateLink = new UpdateButton(this, "Set update link");
        updateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = linkInput.getText().toString();
                updateLink(link);
                linkInput.setText("");
            }
        });
        myLayout.addView(updateLink, layoutParams);

    }

    private void callCE(){
        try {
            AA_Manager manager = new AA_Manager(this);
            manager.callCE(0);
        } catch (Exception e){
            statusView.setText("Failed to call "+e.getMessage());
        }

    }

    private void fetchData(){

        try {
            statusView.setText("Fetching data from url");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            // Create a URL for the desired page
            HttpClient httpclient = new DefaultHttpClient();
            //TODO
            HttpPost httpPost = new HttpPost(getLink());
            HttpResponse httpResponse = httpclient.execute(httpPost);

            InputStream inputStream = null;
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                    sb.append(line);
                inputStream.close();
                String response = sb.toString();
                statusView.setText("Checking data for errors...");
                checkData(response);
            }
            else
                statusView.setText("Failed to fetch data from the url");

        }catch (Exception e){
            statusView.setText("Error: " + e.getMessage());
        }
    }

    private void checkData(String data){
        try {
            //Check main JSON objects
            JSONObject rootObject = new JSONObject(data);
            JSONObject condArrayObject = rootObject.getJSONObject("Conditions Array");
            JSONObject cond = rootObject.getJSONObject("Condition");
            JSONObject tqs = rootObject.getJSONObject("Trigger Questions");
            JSONObject pms = rootObject.getJSONObject("Patient Management");
            JSONObject ceArrayObject = rootObject.getJSONObject("Clinical Experts Array");
            JSONObject ces = rootObject.getJSONObject("Clinical Experts");

            //Check conditions array
            JSONArray condArray = condArrayObject.getJSONArray("conditions_array");

            //Check each condition
            for (int i=0; i<condArray.length(); i++){
                String condName = condArray.getString(i);
                JSONObject condition = cond.getJSONObject(condName);
                String name = condition.getString("name");
                boolean status = condition.getBoolean("status");
                JSONArray tqArray = condition.getJSONArray("TQ_Array");
                JSONArray pmArray = condition.getJSONArray("PM_Array");
            }

            //Check each Trigger Question
            Iterator<String> iter = tqs.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                JSONObject tq = tqs.getJSONObject(key);
                String label = tq.getString("label");
                String question = tq.getString("question");
                JSONArray answers = tq.getJSONArray("answer_options");
            }

            //Check each Patient Managment
            iter = pms.keys();
            while(iter.hasNext()) {
                String key = iter.next();
                JSONObject pm = pms.getJSONObject(key);
                String label = pm.getString("label");
                String action = pm.getString("action");
                String imageName = pm.getString("image");
                //Drawable drawable = getResources().getDrawable(getResources().getIdentifier("icon.png", "drawable", getPackageName()));
            }

            //Check each Clinical Expert Array
            JSONArray ceArray = ceArrayObject.getJSONArray("CE_Array");

            //Check each CE
            for (int i=0; i<ceArray.length(); i++){
                String ceid = ceArray.getString(i);
                JSONObject ce = ces.getJSONObject(ceid);
                String number = ce.getString("number");
            }

            statusView.setText("Data Good! \n Writing to file...");

            AA_Log updateLog = new AA_Log(this);
            updateLog.writeToFile("app_data", data);
            updateLog.writeToFile("update_available", "true");

            statusView.setText("Update Complete! Successful!");

        } catch (Exception e){
            statusView.setText("Data Error: "+e.getMessage());
        }
    }

    private void restoreData(){
        try {
            AA_Log updateLog = new AA_Log(this);
            updateLog.writeToFile("update_available", "false");
            statusView.setText("Restore Successful!");
        } catch (Exception e){
            statusView.setText("Failed to restore original data \n" + e.getMessage());
        }
    }

    private void updateLink(String link){
        AA_Log updateLog = new AA_Log(this);
        //Question:- should we error check link here??
        try {
            updateLog.writeToFile("update_link", link);
        } catch (Exception e)
        {
            statusView.setText("Failed to save update link");
        }
    }

    private String getLink(){
        try {
            InputStream is = openFileInput("update_link");
            String link = readStream(is);
            return link.trim();
        } catch (Exception e){
            return defaultURL;
        }
    }

    private String readStream(InputStream is) throws Exception{
        StringBuilder sBuilder = new StringBuilder();
        BufferedReader isr = new BufferedReader(new InputStreamReader(is));
        char[] data = new char[1];
        while (isr.read(data) > 0) {
            sBuilder.append(data);
        }
        return sBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
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
