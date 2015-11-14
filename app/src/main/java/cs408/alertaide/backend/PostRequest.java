package cs408.alertaide.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Negatu on 11/13/15.
 */
public class PostRequest {

    String url_link = new String();

    public PostRequest(String link){
        url_link = link;
    }

    public String getPostResponse() throws IOException{
        URL url = new URL(url_link);
        URLConnection conn = url.openConnection();

        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line = null;

        // Read Server Response
        while((line = reader.readLine()) != null)
        {
            sb.append(line);
            break;
        }
        return sb.toString();

    }

}
