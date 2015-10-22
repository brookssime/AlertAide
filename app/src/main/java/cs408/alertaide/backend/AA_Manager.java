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

    public AA_Manager(Context context) throws AAException{
        myContext = context;
        myData = new AA_Data(myContext);
    }

    public boolean check_HW_Info() {
        return false;
    }

    public boolean put_HW_Info() {
        return false;
    }

    /**
     * A static method that returns a JSONArray of known conditions
     * @return
     */
    public JSONArray getConditions() throws AAException{
        return myData.getConditions();
    }
    public JSONObject get_TQs (String condition_Name)throws AAException {
        return myData.get_TQs(condition_Name);
    }
    public JSONObject get_PMs(String condition_Name) throws AAException{
        return myData.get_PMs(condition_Name);
    }

    public String get_Next_CE() {
        return null;
    }

    public boolean send_Initial_SMS(String file_Name) {
        return false;
    }

    public boolean send_Response_SMS(String file_Name) {
        return false;
    }

    //TODO: INCLUDE CALL NOW?

   public boolean log_Info(String file_Name, String key, String value){
       return false;
   }

    public String log_Session(){
        return null;
    }


}
