package mobileprogramming.koreatech.together.Manager;

import android.util.Log;

import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Request.GetUserRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;

/**
 * Created by user on 2015-12-10.
 */
public class AuthManager implements HttpUpdater {

    public static AuthManager singleton = new AuthManager();

    private String phoneNumber;
    private UserData userData;
    private HttpUpdater httpUpdater;

    public String URI = "http://1.214.224.68:8080/";

    public void setPhoneNumber(String phone_number){
        this.phoneNumber = phone_number;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public static AuthManager getInstance(){
        return singleton;
    }

    public void requestLogin(HttpUpdater httpUpdater){
        if(this.httpUpdater != null){
            return;
        }
        this.httpUpdater = httpUpdater;
        GetUserRequest getUserRequest = new GetUserRequest(this, phoneNumber);
        getUserRequest.execute();
    }

    public void setUserData(UserData userData){
        this.userData = userData;
    }

    public UserData getUserData(){
        return userData;
    }

    @Override
    public void httpUpdate(String response) {
        httpUpdater.httpUpdate(response);
        httpUpdater = null;
        // process
        SimpleToast.getInstance().makeToast("인증 성공적으로 이루어졌습니다.");
    }

    @Override
    public void httpError(String response) {
        httpUpdater.httpError(response);
        httpUpdater = null;
        SimpleToast.getInstance().makeToast("인증이 뭔가 잘못됬어, 서버좀 확인해봐");
    }
}
