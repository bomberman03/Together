package mobileprogramming.koreatech.together.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.View.ProfileView;

/**
 * Created by user on 2015-12-10.
 */
public class TeamDialog extends Dialog {

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

        setContentView(R.layout.team_dialog);

        init();
        initListener();
    }

    public void init(){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout = (LinearLayout) findViewById(R.id.team_layout);

        for(int i=0; i<5; i++){
            ProfileView profileView = new ProfileView(context, "컴퓨터공학부","정재현","");
            main_layout.addView(profileView.getListLayout(main_layout));
        }
    }

    public void initListener(){

    }

    public TeamDialog(Context context, View.OnClickListener leftListener , View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    public void signUpReqeust(){
    }
}
