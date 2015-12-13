package mobileprogramming.koreatech.together.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.Fragment.NewTaskDrawerFragment;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.View.ProfileView;

/**
 * Created by user on 2015-12-10.
 */
public class TeamDialog extends Dialog {

    private Context context;

    private LayoutInflater layoutInflater;

    private LinearLayout main_layout;

    private NewTaskDrawerFragment newTaskDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.team_dialog);

        init();
    }

    public void init(){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout = (LinearLayout) findViewById(R.id.team_layout);

        for (UserData userData : newTaskDrawerFragment.getProjectData().users) {
            ProfileView profileView = new ProfileView(context, userData);
            LinearLayout profile_layout = profileView.getListLayout(main_layout);
            profile_layout.setTag(userData);
            profile_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserData userData = (UserData) v.getTag();
                    newTaskDrawerFragment.setUserData(userData);
                    hide();
                }
            });
            main_layout.addView(profile_layout);
        }
    }

    public TeamDialog(Context context, NewTaskDrawerFragment newTaskDrawerFragment ) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.newTaskDrawerFragment = newTaskDrawerFragment;
    }
}
