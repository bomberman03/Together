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

import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.AuthManager;

/**
 * Created by user on 2015-12-10.
 */
public class InsertUserRequest extends AsyncTask<Void, Void, String> {

    private String URL = AuthManager.getInstance().URI + "api-auth/users/";
    private HttpUpdater httpUpdater;
    private UserData userData;
    private int statusCode;

    public InsertUserRequest(HttpUpdater httpUpdater, UserData userData) {
        this.httpUpdater = httpUpdater;
        this.userData = userData;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = "";
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("student_id", userData.student_id));
            nameValuePairs.add(new BasicNameValuePair("department_name", userData.department_name));
            nameValuePairs.add(new BasicNameValuePair("grade", userData.grade));
            nameValuePairs.add(new BasicNameValuePair("name", userData.name));
            nameValuePairs.add(new BasicNameValuePair("phone_number", userData.phone_number));

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
