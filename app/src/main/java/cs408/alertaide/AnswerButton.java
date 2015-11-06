package cs408.alertaide;

import android.content.Context;
import android.widget.Button;

/**
 * Created by brookssime on 11/5/15.
 */
public class AnswerButton extends Button {
    String myKey;
    String myValue;

    public AnswerButton(Context context, String key, String value) {
        super(context);
        myKey = key;
        myValue = value;

    }

    protected String getKey(){
        return myKey;
    }

    protected String getValue(){
        return myValue;
    }


}
