package cs408.alertaide.backend;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A controller class that mainly specifies APIs between AAFrontEnd and AABackEnd
 * Created by brookssime on 10/3/15.
 */
public class AA_Manager {
    Context myContext;
    AA_Data myData;
    AA_Log myLog;
    AA_Comm myComm;

    public AA_Manager(Context context) throws AAException{
        myContext = context;
        myData = new AA_Data(myContext);
        myLog = new AA_Log(myContext);
        myComm = new AA_Comm(myContext);
    }

    public boolean check_HW_Info() {
        try {
            boolean check = myLog.checkHWInfo();
            return check;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean put_HW_Info(JSONObject info) {
        try {
            myLog.logHWInfo(info);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * A static method that returns a JSONArray of known conditions
     * @return
     */
    public JSONArray getConditions() throws AAException{
        return myData.getConditions();
    }
    public JSONObject getTQs (String condition_Name)throws AAException {
        return myData.getTQs(condition_Name);
    }
    public JSONObject getPMs(String condition_Name) throws AAException{
        return myData.getPMs(condition_Name);
    }

    public String get_Next_CE() {
        return null;
    }

    public void send_Initial_SMS(String file_Name, int CEID) throws AAException{
        myComm.sendSMS(file_Name, CEID);
    }


    //TODO: INCLUDE CALL NOW?

    public void callCE(int ceIndex) throws AAException{
        myComm.callCE(ceIndex);
    }

   public void logInfo(String file_Name, String key, String value) throws AAException{
       myLog.logToFile(file_Name, key, value);
   }

    public void logInfo(String file_Name, String key, JSONObject value) throws AAException{
        myLog.logToFile(file_Name, key, value);
    }

    public String getLogSession(String condition) throws AAException{
            return myLog.createFile(condition);
    }


}
