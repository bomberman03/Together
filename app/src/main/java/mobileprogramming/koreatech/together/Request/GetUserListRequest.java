package mobileprogramming.koreatech.together.Request;

import android.os.AsyncTask;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.AuthManager;

/**
 * Created by user on 2015-12-10.
 */
public class GetUserListRequest extends AsyncTask<Void, Void, String> {

    private String URL = AuthManager.getInstance().URI + "api-auth/users/?search=";
    private HttpUpdater httpUpdater;
    private String search;
    private int statusCode;

    public GetUserListRequest(HttpUpdater httpUpdater, String search) {
        this.httpUpdater = httpUpdater;
        this.search = search;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(URL+search);
            HttpResponse responsePost = http.execute(httpGet);
            statusCode = responsePost.getStatusLine().getStatusCode();
            responseString = EntityUtils.toString(responsePost.getEntity(), HTTP.UTF_8);
        }catch(Exception e){e.printStackTrace();}
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
