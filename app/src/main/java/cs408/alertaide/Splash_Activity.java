package cs408.alertaide;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Splash_Activity extends Activity {


    //FRONTEND
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View v = new ImageView(getBaseContext());
        ImageView image;
        image = new ImageView(v.getContext());
        image.setImageResource(R.drawable.alert_aide);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                goto_login_page();
                finish();
            }
        }, 2500);

//        ImageView logo_view = (ImageView) findViewById(R.id.logo);
//        logo_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goto_login_page();
//            }
//        });

        Button testButton = (Button) findViewById(R.id.testViewButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_testBackend();
            }
        });

    }

    private void goto_testBackend(){
        Intent intent = new Intent(this, TestBackend_Activity.class);
        startActivity(intent);
    }

    private void goto_login_page(){
        Intent intent = new Intent(this, Login_Activity.class);
        Bundle extras = new Bundle();
        //extras.putString("username", username);
        //extras.putString("password", password);
        //intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("Test Backend");
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
