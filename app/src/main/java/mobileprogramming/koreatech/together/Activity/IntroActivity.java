package mobileprogramming.koreatech.together.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Util.SimpleToast;

public class IntroActivity extends Activity implements HttpUpdater{

    private IntroActivity introActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SimpleToast.getInstance().setContext(getApplicationContext());
        checkWifiState();
    }

    public void checkWifiState(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("인터넷 연결 상태 확인")
                    .setMessage("인터넷 연결이 필요한 서비스입니다. 연결 상태를 확인하세요")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton){
                            checkWifiState();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton){
                            finish();
                        }
                    });
            AlertDialog dialog = builder.create();    // 알림창 객체 생성
            dialog.show();    // 알림창 띄우기
        }
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("테스팅 서버 IP 입력");
            alert.setMessage("서버의 IP를 입력하세요");
            final EditText input = new EditText(this);
            alert.setView(input);
            alert.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    AuthManager.getInstance().URI = "http://" + value + ":8080/";
                    TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    AuthManager.getInstance().setPhoneNumber(tMgr.getLine1Number());
                    AuthManager.getInstance().requestLogin(introActivity);
                }
            });
            alert.setNegativeButton("종료",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });
            alert.show();
        }
    }

    public void startApp(){
        Handler mHandler = new Handler();
        mHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    }
                }, 500);
    }

    @Override
    public void httpUpdate(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            UserData userData = new UserData(jsonObject);
            AuthManager.getInstance().setUserData(userData);
            startApp();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("등록되 회원이 아니시군요");
        startApp();
    }
}
