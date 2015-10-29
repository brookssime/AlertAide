package cs408.alertaide.backend;

import android.content.Context;

import org.json.JSONArray;
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

    public AA_Log(Context context){
        myContext = context;
    }

    public String createFile() throws AAException{
        Long timeStamp = System.currentTimeMillis();
        String fileName = timeStamp.toString().substring(timeStamp.toString().length() - 4);
        try {
            JSONObject sessionObject = new JSONObject();
            sessionObject.put("session", new JSONObject());
            writeToFile(fileName, sessionObject.toString());
            return fileName;
        } catch (Exception e){
            throw new AAException("Failed to log new file \n"+e.getMessage());
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

    public void writeToFile(String fileName, String logLine) throws AAException {
        try {
            FileOutputStream outputStream ;
            outputStream = myContext.openFileOutput( fileName ,  Context.MODE_PRIVATE );
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

