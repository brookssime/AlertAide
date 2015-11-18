package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

/**
 * Created by Negatu on 11/15/15.
 */
class DoneButton extends Button {

    public DoneButton(Context context, String prompt){
        super(context);
        this.setText(prompt);
        this.setTextSize(30);
        this.setStyleDefault();
    }


    public void setStyleDefault(){
        this.setTextColor(Color.BLACK);
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        this.setPadding(5,5,5,5);
        this.setBackgroundResource(R.drawable.button_bg);



    }
}
