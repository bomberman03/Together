package mobileprogramming.koreatech.together.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mobileprogramming.koreatech.together.Dialog.SignUpDialog;
import mobileprogramming.koreatech.together.R;

public class LoginActivity extends Activity implements View.OnClickListener{
    private Button button_sign_in;
    private Button button_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initListener();
    }

    private void init(){
        button_sign_in = (Button) findViewById(R.id.sign_in_button);
        button_sign_up = (Button) findViewById(R.id.sign_up_button);
    }

    private void initListener(){
        button_sign_in.setOnClickListener(this);
        button_sign_up.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_up_button:
                SignUpDialog signUpDialog = new SignUpDialog(this,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "왼쪽버튼 Click!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "오른쪽버튼 Click!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                signUpDialog.show();
                break;
        }
    }
}
