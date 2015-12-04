package mobileprogramming.koreatech.together;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 2015-11-26.
 */
public class SignUpDialog extends Dialog implements View.OnClickListener{

    public EditText student_id;
    public EditText department;
    public EditText grade;
    public EditText name;
    public EditText phone_number;
    public TextView submit_button;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.sign_up_dialog);

        init();
        initListener();
    }

    public void init(){
        student_id = (EditText) findViewById(R.id.student_id);
        department = (EditText) findViewById(R.id.department);
        grade = (EditText) findViewById(R.id.name);
        name = (EditText) findViewById(R.id.phone_number);
        phone_number = (EditText) findViewById(R.id.phone_number);

        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        phone_number.setText(mPhoneNumber);

        submit_button = (TextView) findViewById(R.id.submit_button);
    }

    public void initListener(){
        submit_button.setOnClickListener(this);
    }

    public SignUpDialog(Context context, View.OnClickListener leftListener , View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_button:
                signUpReqeust();
                break;
        }
    }

    public void signUpReqeust(){
        String student_id = this.student_id.getText().toString();
        String department = this.department.getText().toString();
        String grade = this.grade.getText().toString();
        String name = this.name.getText().toString();
        String phone = this.phone_number.getText().toString();
    }
}
