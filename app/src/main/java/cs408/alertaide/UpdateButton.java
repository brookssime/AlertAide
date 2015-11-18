package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;


/**
 * Created by brookssime on 11/18/15.
 */
public class UpdateButton extends Button {

    public UpdateButton(Context context, String prompt){
        super(context);
        this.setText(prompt);
        this.setStyleDefault();
    }


    public void setStyleDefault(){
        this.setTextColor(Color.WHITE);
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        this.setPadding(5,5,5,5);
        this.setBackgroundResource(R.drawable.pm_button);



    }
}
