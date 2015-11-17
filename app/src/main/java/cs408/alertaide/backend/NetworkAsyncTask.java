package cs408.alertaide.backend;

import android.os.AsyncTask;

/**
 * Created by Negatu on 11/13/15.
 */
class NetworkAsyncTask extends AsyncTask<String, Void, String> {
    private PostRequest myRequest;
    private String myResponse;

    public NetworkAsyncTask(PostRequest request){
        myRequest = request;
    }



    @Override
    protected String doInBackground(String... arg0){
        String response = new String();
        try {
            response = myRequest.getPostResponse();
        } catch (Exception e){
            response = "Error: " + e.toString();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result){
        myResponse = result;
    }

    private String getResponse(){
        while(getResponse()== null){
            try {
                Thread.sleep(100);
            }catch (Exception e){

            }
        }
        return myResponse;
    }


}
