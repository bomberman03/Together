package mobileprogramming.koreatech.together.Request;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import mobileprogramming.koreatech.together.HttpUpdater;

/**
 * Created by user on 2015-12-10.
 */
public class GetUserRequest extends AsyncTask<Void, Void, String> {

    private String URL = "http://1.214.224.33:8080/api-auth/users/";
    private HttpUpdater httpUpdater;
    private int statusCode;

    public GetUserRequest(HttpUpdater httpUpdater, String phone_number) {
        this.httpUpdater = httpUpdater;
        this.URL = this.URL + phone_number + "/";
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse responsePost = http.execute(httpGet);
            Log.d("TAG",""+responsePost.getStatusLine().getStatusCode());
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
