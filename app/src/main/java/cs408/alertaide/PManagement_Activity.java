package cs408.alertaide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import cs408.alertaide.backend.AAException;
import cs408.alertaide.backend.AA_Manager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class PManagement_Activity extends Activity {
    LinearLayout linear;
    ImageView imgView;
    AA_Manager myManager;
    Bundle myBundle;
    Button done;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmanagement);
        linear = (LinearLayout) findViewById(R.id.linear);
        imgView = (ImageView) findViewById(R.id.imgView);
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
            //TODO: Something's up with the bundle - not sending condition
           // String condition = myBundle.getString("condition");
            JSONObject pm = myManager.getPMs("pphem");
            setPM(pm);

        }catch (AAException e) {
            throw_Error(e.getMessage());
        }
    }


    private void throw_Error(String errorMessage) {

        AA_ErrorPopup errorPopup = new AA_ErrorPopup(this, errorMessage);
    }



    private void setPM(JSONObject json) {
        TextView pmText = new TextView(this);
        pmText.setText("PATIENT MANAGEMENT");
        pmText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        pmText.setTextSize(32);
        linear.addView(pmText);

        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject value = (JSONObject) json.get(key);
                String action = value.getString("action");
                String image = value.getString("image");
                setAction(action);
                setImage(image);


            } catch (JSONException e) {
                throw_Error(e.getMessage());
            }
        }
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);

        Button textCEButton = new Button(this);
        String buttonText = "Text CE Again";
        textCEButton.setText(buttonText);
        buttonLayout.addView(textCEButton);

        Button textNewCEButton = new Button(this);
        buttonText = "Text New CE";
        textNewCEButton.setText(buttonText);
        buttonLayout.addView(textNewCEButton);

        Button callCEButton = new Button(this);
        buttonText = "Call CE";
        callCEButton.setText(buttonText);
        buttonLayout.addView(callCEButton);


        /*Button done = new Button(this);
        buttonText = "Done";
        done.setText(buttonText);
        buttonLayout.addView(done);
        linear.addView(buttonLayout);*/

        is_Clicked(linear);
        finish_PM();
    }

    private void setAction(String action)
    {
        TextView myText = new TextView(this);
        myText.setText(action);
        myText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linear.addView(myText);
    }

    private void setImage(String image)
    {
        //LinearLayout buttonLayout = new LinearLayout(this);
        //buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        //buttonLayout.setLayoutParams(new ViewGroup.MarginLayoutParams(250, 150));

        //buttonLayout.setBackgroundColor(R.drawable.img_button);

        //ImageButton myButton = new ImageButton(this);

        //TODO: Set it to do images automatically
        //myButton.setImageResource(R.drawable.iv);
        imgView.setImageResource(R.drawable.iv);
       // myButton.onTouchEvent(setBackgroundColor(00FF66));
        //myButton.setLayoutParams(new ViewGroup.LayoutParams.WRAP_CONTENT);
        //myButton.
       // myButton.setBackgroundColor(R.drawable.img_button);

        //buttonLayout.addView(myButton);

        //imgView.addView(buttonLayout);
    }



    private void goto_checkout(){
        Intent intent = new Intent(this, Checkout_Activity.class);
        Bundle extras = new Bundle();
        //extras.putString("username", username);
        //extras.putString("password", password);
        //intent.putExtras(extras);
        startActivity(intent);
    }


    public void is_Clicked(LinearLayout layout) {
    }


    private void finish_PM(){
        Button done = new Button(this);
        done.setText("Done");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_checkout();
                /*try {
                    //myManager.logInfo(myBundle.getString("file"), "tqAnswers",tqAnswers.toString() );


                } catch (AAException e) {
                    throw_Error("Stop" + e.getMessage());
                }*/


            }
        });
        linear.addView(done);

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
