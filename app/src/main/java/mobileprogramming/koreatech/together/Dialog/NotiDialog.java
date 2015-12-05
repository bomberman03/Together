package mobileprogramming.koreatech.together.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import mobileprogramming.koreatech.together.R;

/**
 * Created by Billy on 2015-12-05.
 */
public class NotiDialog extends Dialog implements View.OnClickListener{

    private Context context;

    private LayoutInflater layoutInflater;

    private LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.noti_dialog);

        init();
        initListener();
    }

    public void init(){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout = (LinearLayout) findViewById(R.id.noti_layout);

        for(int i=0; i<5; i++){
            LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.noti_layout, main_layout, false);
            main_layout.addView(res_layout);
        }
    }

    public void initListener(){

    }

    public NotiDialog(Context context, View.OnClickListener leftListener , View.OnClickListener rightListener) {
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
    }
}
