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

import mobileprogramming.koreatech.together.Data.FeedData;
import mobileprogramming.koreatech.together.HttpUpdater;

/**
 * Created by user on 2015-12-13.
 */
public class InsertFeedRequest  extends AsyncTask<Void, Void, String> {

    private String URL = "http://1.214.224.33:8080/api-auth/feeds/";
    private HttpUpdater httpUpdater;
    private FeedData feedData;
    private int statusCode;

    public InsertFeedRequest(HttpUpdater httpUpdater, FeedData feedData) {
        this.httpUpdater = httpUpdater;
        this.feedData = feedData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("task", feedData.taskData.id));
            nameValuePairs.add(new BasicNameValuePair("user", feedData.userData.id));
            nameValuePairs.add(new BasicNameValuePair("summary", feedData.summary));

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
        } else {
            httpUpdater.httpError(result);
        }
    }
}
