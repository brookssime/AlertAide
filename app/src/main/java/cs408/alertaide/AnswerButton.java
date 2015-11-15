package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by brookssime on 11/5/15.
 */
class AnswerButton extends Button {

    private String myValue;
    private int myIndex;

    public AnswerButton(Context context, String value, int index) {
        super(context);
        myValue = value;
        myIndex = index;
        setText(value);
        setBackgroundColor(Color.GRAY);
        setTextColor(Color.WHITE);
    }

    public String getValue(){
        return myValue;
    }

    public void setClicked(){
        setBackgroundColor(Color.GREEN);
    }

    public void undoClicked(){
        setBackgroundColor(Color.GRAY);
    }

    public int getIndex(){
        return myIndex;
    }

}
