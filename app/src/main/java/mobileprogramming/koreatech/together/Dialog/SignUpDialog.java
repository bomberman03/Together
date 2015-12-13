package mobileprogramming.koreatech.together.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.InsertUserRequest;

/**
 * Created by user on 2015-11-26.
 */
public class SignUpDialog extends Dialog implements View.OnClickListener {

    public EditText student_id;
    public EditText department;
    public EditText grade;
    public EditText name;
    public EditText phone_number;

    public TextView submit_button;
    public TextView cancel_button;

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
        grade = (EditText) findViewById(R.id.grade);
        name = (EditText) findViewById(R.id.name);
        phone_number = (EditText) findViewById(R.id.phone_number);

        String mPhoneNumber = AuthManager.getInstance().getPhoneNumber();
        phone_number.setText(mPhoneNumber);

        submit_button = (TextView) findViewById(R.id.submit_button);
        cancel_button = (TextView) findViewById(R.id.cancel_button);
    }

    public void initListener(){
        submit_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
    }

    public SignUpDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_button:
                signUpRequest();
                break;
            case R.id.cancel_button:
                hide();
                break;
        }
    }

    public void signUpRequest(){
        String student_id = this.student_id.getText().toString();
        String department = this.department.getText().toString();
        String grade = this.grade.getText().toString();
        String name = this.name.getText().toString();
        String phone = this.phone_number.getText().toString();

        UserData userData = new UserData(student_id, department, grade, name, phone);

        InsertUserRequest insertUserRequest = new InsertUserRequest((HttpUpdater)context, userData);
        insertUserRequest.execute();
    }
}
