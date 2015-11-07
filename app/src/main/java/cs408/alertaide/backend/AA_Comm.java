package cs408.alertaide.backend;

import android.content.Context;
import android.telephony.SmsManager;

import org.apache.http.impl.conn.AbstractPooledConnAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Negatu on 10/28/15.
 */
public class AA_Comm {

    private Context myContext;

    public AA_Comm(Context context) {
        myContext = context;
    }

    public void sendSMS(String fileName, int ceIndex) throws AAException{
        try {
            String message = constructMessage(fileName);
            String ceNumber = getCENumber(ceIndex);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ceNumber, null, message, null, null);
        } catch (Exception e) {
            throw new AAException("Failed to send SMS \n"+e.getMessage());
        }
    }

    private String constructMessage(String fileName) throws AAException{
        try {
            AA_Log myLog = new AA_Log(myContext);
            JSONObject rootObject = new JSONObject(myLog.readFile(fileName));
            JSONObject sessionObject = rootObject.getJSONObject("session");
            String message = constructMessage(sessionObject);
            return message;
        } catch (Exception e) {
            throw new AAException(e.getMessage());
        }

    }

    private String constructMessage(JSONObject sessionObject) throws AAException{
        try {
            StringBuilder messageBuilder = new StringBuilder();
            String name = sessionObject.getJSONObject("hw_info").getString("name");
            messageBuilder.append("Health worker name = "+name+"\n");
            String condition = sessionObject.getJSONObject("condition").getString("name");
            messageBuilder.append("Patient condition = "+condition+"\n");
            JSONObject answers = sessionObject.getJSONObject("tqAnswers");
            Iterator<String> iter = answers.keys();
            int quesNum = 1;
            while (iter.hasNext()) {
                String tqLabel = iter.next();
                try {
                    if(!tqLabel.equals("startTime")){
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

}
