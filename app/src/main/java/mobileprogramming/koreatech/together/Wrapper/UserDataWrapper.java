package mobileprogramming.koreatech.together.Wrapper;

import java.io.Serializable;
import java.util.ArrayList;

import mobileprogramming.koreatech.together.Data.UserData;

/**
 * Created by user on 2015-12-12.
 */
public class UserDataWrapper implements Serializable {

    private ArrayList<UserData> userDatas;

    public UserDataWrapper(ArrayList<UserData> userDatas){
        this.userDatas = userDatas;
    }

    public ArrayList<UserData> getUserDatas(){
        return this.userDatas;
    }
}
