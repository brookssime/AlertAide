package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Negatu on 11/15/15.
 */
class AAView extends TextView {

    public AAView(Context context, String title, int style){
        super(context);
        this.setText(title);

        switch (style){
            case 1:
                this.setStyleOne();
                break;
            case 2:
                this.setStyleTwo();
                break;
        }
    }

    public void setStyleOne(){
        this.setTextSize(20);
        this.setTextColor(Color.WHITE);
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        this.setShadowLayer(1, 1, 1, Color.BLACK);
        this.setPadding(20,30,20,30);

        GradientDrawable shape =  new GradientDrawable();
        shape.setColor(Color.parseColor("#0099cc"));
        this.setBackground(shape);
    }

    public void setStyleTwo(){
        this.setPadding(20, 30, 20, 30);
        this.setTextColor(Color.BLACK);
        this.setTextSize(20);
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

    }
}
