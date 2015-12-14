package mobileprogramming.koreatech.together.Request;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.AuthManager;

/**
 * Created by user on 2015-12-12.
 */
public class InsertTaskRequest extends AsyncTask<Void, Void, String> {

    private String URL = AuthManager.getInstance().URI + "api-auth/tasks/";
    private HttpUpdater httpUpdater;
    private TaskData taskData;
    private int statusCode;

    public InsertTaskRequest(HttpUpdater httpUpdater, TaskData taskData) {
        this.httpUpdater = httpUpdater;
        this.taskData = taskData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("project", taskData.projectData.id));
            nameValuePairs.add(new BasicNameValuePair("user", taskData.userData.id));
            nameValuePairs.add(new BasicNameValuePair("name", taskData.name));
            nameValuePairs.add(new BasicNameValuePair("summary", taskData.summary));
            nameValuePairs.add(new BasicNameValuePair("end_date", taskData.end_date));

            HttpPost httpPost = new HttpPost(URL);
            UrlEncodedFormEntity entityRequest =
                    new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httpPost.setEntity(entityRequest);

            HttpResponse responsePost = http.execute(httpPost);
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
