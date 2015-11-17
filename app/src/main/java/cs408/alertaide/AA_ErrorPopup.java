package cs408.alertaide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Negatu on 10/29/15.
 */
public class AA_ErrorPopup {

    public  AA_ErrorPopup (Context context, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error!");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public  AA_ErrorPopup (Context context, String msg, String method) throws NoSuchMethodException {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error!");
        alertDialog.setMessage(msg);
        Method myMethod = null;
        try {
            myMethod = AA_ErrorPopup.class.getMethod(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        final Method finalMyMethod = myMethod;


        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            if (finalMyMethod != null) {
                                dialog.dismiss();
                                finalMyMethod.invoke(null);
                            }
                            else{
                                dialog.dismiss();
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.show();
    }




}
