package mobileprogramming.koreatech.together.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2015-12-13.
 */
public class SimpleToast {

    public static SimpleToast singleton = new SimpleToast();
    private Context context;

    public static SimpleToast getInstance(){
        return singleton;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void makeToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
