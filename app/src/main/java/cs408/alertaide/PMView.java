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

    public PMView(Context context, JSONObject json){
        super(context);
        this.setOrientation(VERTICAL);
        this.setPadding(0, 10, 0, 50);
        
        myContext = context;
        myData = json;
        createMyView();
    }

    private void createMyView(){
        myTitle = new TextView(myContext);
        try {
            myTitle.setText(myData.getString("label"));
        } catch (Exception e){
            myTitle.setText("PM label not found");
        }
        this.addView(myTitle);

        myImage = new ImageView(myContext);
        try {
            String uri = "drawable/" + myData.getString("image");
            int imageResource = getResources().getIdentifier(uri, null, myContext.getPackageName());
            myImage.setImageResource(imageResource);
        } catch (Exception e){
            myImage.setImageResource(R.drawable.no_image);
        }

        myImage.setBackgroundColor(Color.RED);

        myImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                blackBG();
            }
        });
        this.addView(myImage);

        setStyle();
    }

    private void blackBG(){
        myImage.setBackgroundColor(Color.GREEN);
    }

    private void setStyle(){
        LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(1000, 1000);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        myTitle.setTextSize(24);
        myTitle.setTextColor(Color.WHITE);
        myTitle.setBackgroundColor(Color.GRAY);
        myTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        myTitle.setLayoutParams(textParams);
        imageParams.gravity= Gravity.CENTER;
        myImage.setLayoutParams(imageParams);
    }
}
