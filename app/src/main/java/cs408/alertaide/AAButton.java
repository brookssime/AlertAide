package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

/**
 * Created by Negatu on 11/15/15.
 */
class AAButton extends Button {

    public AAButton(Context context, String prompt){
        super(context);
        this.setText(prompt);
        this.setStyleDefault();
    }


    public void setStyleDefault(){
        this.setTextColor(Color.WHITE);
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        this.setBackgroundResource(R.drawable.done);
        this.setHeight(70);
        this.setWidth(230);

    }
}
