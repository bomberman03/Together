package mobileprogramming.koreatech.together.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.Dialog.SignUpDialog;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Util.SimpleToast;

public class LoginActivity extends Activity implements View.OnClickListener, HttpUpdater{

    private TextView message;
    private SignUpDialog signUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        message = (TextView) findViewById(R.id.message);

        UserData userData = AuthManager.getInstance().getUserData();
        if(userData == null){
            message.setText("화면을 터치하여 계정을 등록하세요");
        }
        else {
            message.setText(userData.phone_number + "로 접속하기");
        }
    }

    @Override
    public void onClick(View v) {
        UserData userData = AuthManager.getInstance().getUserData();
        if(userData == null){
            signUpDialog = new SignUpDialog(this);
            signUpDialog.show();
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void httpUpdate(String response) {
        try {
            signUpDialog.hide();
            JSONObject jsonObject = new JSONObject(response);
            UserData userData = new UserData(jsonObject);
            AuthManager.getInstance().setUserData(userData);
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("뭔가 잘못 입력한거 같네요. 인터넷도 확인해보세요");
    }
}
