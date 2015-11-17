package cs408.alertaide.backend;

import android.content.Context;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by Negatu on 10/23/15.
 */
public class AA_Log {

    private Context myContext;
    private final String hwFile = "hw_info";

    public AA_Log(Context context){
        myContext = context;
    }

    public String createFile(String condition) throws AAException{
        Long timeStamp = System.currentTimeMillis();
        String fileName = "session_" + timeStamp.toString();
        try {
            JSONObject rootObject = new JSONObject();
            rootObject.put("condition", condition);
            rootObject.put("session", new JSONObject());
            rootObject.put("hw_info", getHWInfo());
            writeToFile(fileName, rootObject.toString());
            return fileName;
        } catch (Exception e){
            throw new AAException("Failed to log new file \n"+e.getMessage());
        }
    }

    public boolean checkHWInfo() {
        try {
            getHWInfo();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logHWInfo(JSONObject object) throws AAException {
        try {
            JSONObject rootObject = new JSONObject();
            rootObject.put("hw_info", object);
            FileOutputStream outputStream ;
            outputStream = myContext.openFileOutput( hwFile ,  Context.MODE_PRIVATE );
            outputStream.write(rootObject.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            throw new AAException("Failed to put HW info \n" + e.getMessage());
        }
    }

    private JSONObject getHWInfo() throws AAException{
        try {
            JSONObject rootObject = new JSONObject(readFile(hwFile));
            JSONObject hw_info = rootObject.getJSONObject("hw_info");
            return hw_info;
        } catch (Exception e) {
            throw new AAException("Failed to read HW info \n"+e.getMessage());
        }
    }

    public void logToFile(String fileName, String key, String value) throws AAException{
        try {
            JSONObject rootObject = new JSONObject(readFile(fileName));
            JSONObject sessionObject = rootObject.getJSONObject("session");
            sessionObject.put(key, value);
            writeToFile(fileName, rootObject.toString());
        } catch (Exception e) {
            throw new AAException("Failed to add log "+key+" to file "+fileName+"\n"+e.getMessage());
        }
    }

    public void logToFile(String fileName, String key, JSONObject value) throws AAException{
        try {
            JSONObject rootObject = new JSONObject(readFile(fileName));
            JSONObject sessionObject = rootObject.getJSONObject("session");
            sessionObject.put(key, value);
            writeToFile(fileName, rootObject.toString());
        } catch (Exception e) {
            throw new AAException("Failed to add log "+key+" to file "+fileName+"\n"+e.getMessage());
        }
    }

    public void writeToFile(String fileName, String logLine) throws AAException {
        try {
            FileOutputStream outputStream ;
            outputStream = myContext.openFileOutput( fileName,  Context.MODE_PRIVATE );
            outputStream.write(logLine.getBytes());
            outputStream.close();
        } catch (Exception e) {
            throw new AAException("Failed to write to file named "+fileName+"\n"+e.getMessage());
        }
    }

    public String readFile(String fileName) throws AAException {
        try {
            FileInputStream inputStream = myContext.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            throw new AAException("Failed to read file named "+fileName+"\n"+e.getMessage());
        }
    }

}

