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
        this.setBackgroundResource(R.drawable.pm_button);
//        this.setShadowLayer(5, 5, 5, Color.BLACK);
//
//        this.setPadding(20, 30, 20, 30);
//
//        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.parseColor("#0099cc"), Color.parseColor("#0000FF")});
//        shape.setStroke(2, Color.WHITE);
//
//        shape.setGradientCenter(0, 0);
//        shape.setGradientRadius(100);
//        shape.setCornerRadius(30);
//
//        //shape.setShape(R.drawable.img_button);
//        this.setBackground(shape);
    }
}
