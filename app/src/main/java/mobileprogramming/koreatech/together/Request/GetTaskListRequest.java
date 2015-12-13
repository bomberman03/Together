package mobileprogramming.koreatech.together.Request;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.HttpUpdater;

/**
 * Created by user on 2015-12-13.
 */
public class GetTaskListRequest extends AsyncTask<Void, Void, String> {

    private String URL = "http://1.214.224.33:8080/api-auth/tasks/?project=";
    private HttpUpdater httpUpdater;
    private ProjectData projectData;
    private int statusCode;

    public GetTaskListRequest(HttpUpdater httpUpdater, ProjectData projectData) {
        this.httpUpdater = httpUpdater;
        this.projectData = projectData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(URL + projectData.id);
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
        }
        else {
            httpUpdater.httpError(result);
        }
    }

}
