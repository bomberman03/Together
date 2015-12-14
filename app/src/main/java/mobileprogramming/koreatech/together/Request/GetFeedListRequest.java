package mobileprogramming.koreatech.together.Request;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.AuthManager;

/**
 * Created by user on 2015-12-13.
 */
public class GetFeedListRequest extends AsyncTask<Void, Void, String> {

    private String URL = AuthManager.getInstance().URI +  "api-auth/feeds/?task=";
    private HttpUpdater httpUpdater;
    private TaskData taskData;
    private int statusCode;

    public GetFeedListRequest(HttpUpdater httpUpdater, TaskData taskData) {
        this.httpUpdater = httpUpdater;
        this.taskData = taskData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(URL + taskData.id);
            HttpResponse responsePost = http.execute(httpGet);
            statusCode = responsePost.getStatusLine().getStatusCode();
            responseString = EntityUtils.toString(responsePost.getEntity(), HTTP.UTF_8);
        }catch(Exception e){
            e.printStackTrace();
        }
        return responseString;
    }

    protected void onPostExecute(String result) {
        if(statusCode >= 200 && statusCode < 300) {
            httpUpdater.httpUpdate(result);
        } else {
            httpUpdater.httpError(result);
        }
    }

}