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
import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;

/**
 * Created by user on 2015-12-12.
 */
public class InsertProjectRequest extends AsyncTask<Void, Void, String> {

    private String URL = "http://1.214.224.33:8080/api-auth/projects/";
    private HttpUpdater httpUpdater;
    private ProjectData projectData;
    private int statusCode;

    public InsertProjectRequest(HttpUpdater httpUpdater, ProjectData projectData) {
        this.httpUpdater = httpUpdater;
        this.projectData = projectData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name", projectData.name));
            nameValuePairs.add(new BasicNameValuePair("summary", projectData.summary));
            nameValuePairs.add(new BasicNameValuePair("start_date", projectData.start_date));
            nameValuePairs.add(new BasicNameValuePair("end_date", projectData.end_date));
            for(UserData userData : projectData.users) {
                nameValuePairs.add(new BasicNameValuePair("users", userData.id));
            }

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
