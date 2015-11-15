package cs408.alertaide.backend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import org.apache.http.impl.conn.AbstractPooledConnAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cs408.alertaide.AA_ErrorPopup;

/**
 * Created by Negatu on 10/28/15.
 */
class AA_Comm {

    private Context myContext;

    public AA_Comm(Context context) {
        myContext = context;
    }

    public void sendSMS(String fileName, int ceIndex) throws AAException{
        try {
            String message = constructMessage(fileName);
            String ceNumber = getCENumber(ceIndex);
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> smsParts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(ceNumber, null, smsParts, null, null);
        } catch (Exception e) {
            throw new AAException("Failed to send SMS \n"+e.getMessage());
        }
    }

    public void callCE(int ceIndex) throws AAException{
        String ceNumber = getCENumber(ceIndex);
        //Use ACTION_DIAL instead to edit number??
        Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ceNumber));
        try{
            myContext.startActivity(in);
        } catch (android.content.ActivityNotFoundException e){
            throw new AAException("Failed to call "+ceNumber+"\n"+e.getMessage());
        }

    }

    private String constructMessage(String fileName) throws AAException{
        try {
            AA_Log myLog = new AA_Log(myContext);
            JSONObject rootObject = new JSONObject(myLog.readFile(fileName));
            String message = constructMessage(rootObject);
            return message;
        } catch (Exception e) {
            throw new AAException(e.getMessage());
        }

    }

    private String constructMessage(JSONObject rootObject) throws AAException{
        try {
            JSONObject hwInfo = rootObject.getJSONObject("hw_info");
            JSONObject sessionObject = rootObject.getJSONObject("session");
            StringBuilder messageBuilder = new StringBuilder();

            String name = hwInfo.getString("name");
            messageBuilder.append("Health worker name = "+name+"\n");
            String condition = rootObject.getString("condition");
            messageBuilder.append("Patient condition = "+condition+"\n");
            JSONObject answers = sessionObject.getJSONObject("tqAnswers");
            Iterator<String> iter = answers.keys();
            int quesNum = 1;
            while (iter.hasNext()) {
                String tqLabel = iter.next();
                try {
                    if(!tqLabel.equals("startTimeStamp") && !tqLabel.equals("endTimeStamp")){
                        messageBuilder.append(quesNum + ") " + tqLabel + " >> ");
                        String answer = answers.getString(tqLabel);
                        messageBuilder.append(answer+"\n");
                        quesNum ++;
                    }
                } catch (JSONException e2) {
                    throw new AAException("Failed to read answer from file \n" + e2.getMessage());
                }
            }
            return messageBuilder.toString();
        } catch (Exception e) {
            throw new AAException("Failed to construct message from log data \n"+e.getMessage());
        }
    }

    private String getCENumber(int index) throws AAException{
        try {
            AA_Data myData = new AA_Data(myContext);
            JSONArray CEs = myData.getObject("Clinical Experts Array").getJSONArray("CE_Array");
            int myIndex = index%CEs.length();
            String CEkey = CEs.getString(myIndex);
            String phoneNum = myData.getObject("Clinical Experts").getJSONObject(CEkey).getString("number");
            return phoneNum;
        } catch (Exception e) {
            throw new AAException("Failed to fetch CE number \n"+e.getMessage());
        }
    }

    private void throw_Error(String errorMessage) {
        AA_ErrorPopup errorPopup = new AA_ErrorPopup(myContext, errorMessage);
    }

}
