package mobileprogramming.koreatech.together.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Util.SimpleToast;

/**
 * Created by user on 2015-12-13.
 */
public class ModifyTaskDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private LinearLayout delete_button;
    private LinearLayout post_button;
    private LinearLayout team_button;

    public ModifyTaskDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.modify_task_dialog);

        init();
        initListener();
    }

    public void init(){
        delete_button = (LinearLayout) findViewById(R.id.delete_button);
        post_button = (LinearLayout) findViewById(R.id.post_button);
        team_button = (LinearLayout) findViewById(R.id.team_button);
    }

    public void initListener(){
        delete_button.setOnClickListener(this);
        post_button.setOnClickListener(this);
        team_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SimpleToast.getInstance().makeToast("문제가 있군요, 인터넷을 확인해보세요");
    }
}
