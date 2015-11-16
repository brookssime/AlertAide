package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Negatu on 11/15/15.
 */
class AAView extends TextView {

    public AAView(Context context, String title){
        super(context);
        this.setText(title);
        this.setStyleDefault();
    }

    public void setStyleDefault(){
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        this.setTextSize(20);
        this.setTextColor(Color.WHITE);
        this.setGravity(Gravity.CENTER);
        this.setShadowLayer(1, 1, 1, Color.BLACK);

        GradientDrawable shape =  new GradientDrawable();
        shape.setColor(Color.parseColor("#cc0042a0"));
        shape.setCornerRadius(20);
        this.setBackground(shape);
    }
}
