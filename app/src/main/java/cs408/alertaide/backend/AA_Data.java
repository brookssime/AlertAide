package cs408.alertaide.backend;

import android.content.Context;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A class with multiple static methods that fetch different data
 * Created by Negatu on 10/20/15.
 */
public class AA_Data {

    Context myContext;
    JSONObject myData;

    public AA_Data(Context context) throws AAException{
        myContext = context;
        myData = readInDataFile();
    }

    private JSONObject readInDataFile() throws AAException{
        StringBuilder sBuilder = new StringBuilder();
        try {
            InputStream is = myContext.getResources().getAssets().open("app_data.txt");
            BufferedReader isr = new BufferedReader(new InputStreamReader(is));
            char[] data = new char[10];
            while (isr.read(data)>0){
                sBuilder.append(data);
            }
            return new JSONObject(sBuilder.toString());
        } catch (Exception e){
            throw new AAException("Failed to read app_data.txt \n" + e.getMessage());
        }
    }

    public JSONArray getConditions() throws AAException{
        try {
            return myData.getJSONObject("Conditions Array").getJSONArray("conditions_array");
        } catch (Exception e) {
            throw new AAException("Failed to fetch conditions \n" + e.getMessage());
        }
    }

    public JSONObject get_TQs(String condition) throws AAException{
        try {
            JSONArray conditionTQs = myData.getJSONObject("Condition").getJSONObject(condition).getJSONArray("TQ_Array");
            JSONObject TQObjects = new JSONObject();
            for (int i=0; i<conditionTQs.length(); i++){
                String tq = conditionTQs.getString(i);
                JSONObject tqObj = myData.getJSONObject("Trigger Questions").getJSONObject(tq);
                TQObjects.put(tq, tqObj);
            }
            return TQObjects;
        } catch (Exception e) {
            throw new AAException("Failed to fetch TQs for condition="+condition+"\n"+e.getMessage());
        }
    }

    public JSONObject get_PMs(String condition) throws AAException {
        try {
            JSONArray conditionPMs = myData.getJSONObject("Condition").getJSONObject(condition).getJSONArray("PM_Array");
            JSONObject PMObjects = new JSONObject();
            for (int i=0; i<conditionPMs.length(); i++){
                String tq = conditionPMs.getString(i);
                JSONObject tqObj = myData.getJSONObject("Patient Management").getJSONObject(tq);
                PMObjects.put(tq, tqObj);
            }
            return PMObjects;
        } catch (Exception e) {
            throw new AAException("Failed to fetch PMs for condition="+condition+"\n"+e.getMessage());
        }
    }
}
