package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by Negatu on 11/15/15.
 */
class PMView extends LinearLayout {

    Context myContext;
    JSONObject myData;
    TextView myTitle;
    ImageView myImage;

    Boolean status;

    public PMView(Context context, JSONObject json){
        super(context);
        this.setOrientation(VERTICAL);
        this.setPadding(0, 10, 0, 50);

        myContext = context;
        myData = json;
        status = false;
        createMyView();
    }

    private void createMyView(){
        myTitle = new TextView(myContext);
        try {
            myTitle.setText(myData.getString("action"));
        } catch (Exception e){
            myTitle.setText("PM action not found");
        }
        this.addView(myTitle);

        myImage = new ImageView(myContext);
        try {
            String uri = "drawable/" + myData.getString("image");
            int imageResource = getResources().getIdentifier(uri, null, myContext.getPackageName());
            myImage.setImageResource(imageResource);
            if( imageResource==0 ){
                myImage.setImageResource(R.drawable.no_image);
            }
        } catch (Exception e){
            myImage.setImageResource(R.drawable.no_image);
        }

        myImage.setBackgroundColor(Color.parseColor("#990000"));

        myImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked();
            }
        });
        this.addView(myImage);

        setStyle();
    }

    private void clicked(){
        if (status) {
            myImage.setBackgroundColor(Color.parseColor("#990000"));
            //setStyle();
            status = false;
        } else {
            myImage.setBackgroundColor(Color.parseColor("#339900"));
            //setDoneMode();
            status = true;
        }
    }

    public String getStatus(){
        if (status) {
            return "done";
        }
        return "not done";
    }

    public String getAction(){
        try {
            return myData.getString("action");
        } catch (Exception e){
            return "Missing PM label";
        }
    }

    public String getLabel(){
        try {
            return myData.getString("label");
        } catch (Exception e){
            return "Missing label";
        }
    }

    private void setStyle(){
        setMyImage();
        LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(1000, 1000);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        myTitle.setTextSize(24);
        myTitle.setTextColor(Color.WHITE);
        myTitle.setBackgroundColor(Color.RED);
        myTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        myTitle.setLayoutParams(textParams);
        imageParams.gravity= Gravity.CENTER;
        myImage.setLayoutParams(imageParams);
        myImage.setAlpha(1.0f);
    }

    private void setMyImage(){
        try {
            String uri = "drawable/" + myData.getString("image");
            int imageResource = getResources().getIdentifier(uri, null, myContext.getPackageName());
            myImage.setImageResource(imageResource);
            if( imageResource==0 ){
                myImage.setImageResource(R.drawable.no_image);
            }
        } catch (Exception e){
            myImage.setImageResource(R.drawable.no_image);
        }
    }

    private void setDoneMode(){
        LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(500, 500);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        myTitle.setTextSize(20);
        myTitle.setTextColor(Color.WHITE);
        myTitle.setBackgroundColor(Color.GREEN);
        myTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        myTitle.setLayoutParams(textParams);
        imageParams.gravity= Gravity.CENTER;
        myImage.setLayoutParams(imageParams);
        myImage.setAlpha(0.5f);
    }
}
