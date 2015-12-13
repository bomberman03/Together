package mobileprogramming.koreatech.together.Activity;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.GetUserListRequest;
import mobileprogramming.koreatech.together.View.ProfileView;
import mobileprogramming.koreatech.together.Wrapper.UserDataWrapper;

public class TeamSearchActivity extends AppCompatActivity implements HttpUpdater, View.OnClickListener {

    private LinearLayout select_team;
    private LinearLayout team_list;

    private EditText search_keyword;

    private TextView ok;
    private TextView cancel;

    private ArrayList<UserData> userDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);

        Intent intent = getIntent();

        UserDataWrapper userDataWrapper = (UserDataWrapper) intent.getSerializableExtra("userDatas");
        userDatas = userDataWrapper.getUserDatas();

        init();
        initListener();
    }

    public void init(){
        select_team = (LinearLayout) findViewById(R.id.selected_user);
        team_list = (LinearLayout) findViewById(R.id.team_list);

        for( UserData userData : userDatas) {
            ProfileView profileView = new ProfileView(this, userData);
            LinearLayout small_layout = (LinearLayout) profileView.getSmallLayout(select_team);
            select_team.addView(small_layout);
            if(userData.name.equals(AuthManager.getInstance().getUserData().name)) continue;
            small_layout.setOnClickListener(smallItemListener);
        }

        search_keyword = (EditText) findViewById(R.id.search_keyword);

        ok = (TextView) findViewById(R.id.submit_button);
        cancel = (TextView) findViewById(R.id.cancel_button);
    }

    public void initListener(){
        search_keyword.setOnClickListener(this);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public void searchTeam(View view){
        String search = search_keyword.getText().toString();
        GetUserListRequest getUserListRequest = new GetUserListRequest(this, search);
        getUserListRequest.execute();
    }

    public boolean isSelected(String name){
        boolean res = false;
        for(UserData userData : userDatas){
            if(userData.name.equals(name)) return true;
        }
        return res;
    }

    private View.OnClickListener smallItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserData userData = (UserData) v.getTag();
            userDatas.add(userData);
            select_team.removeView(v);
            userDatas.remove(userData);
            ProfileView profileView = new ProfileView(getApplicationContext(), userData);
            LinearLayout profile_layout = profileView.getListLayout(select_team);
            profile_layout.setTag(userData);
            profile_layout.setOnClickListener(listItemListener);
            team_list.addView(profile_layout,0);
        }
    };

    private View.OnClickListener listItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserData userData = (UserData) v.getTag();
            userDatas.add(userData);
            team_list.removeView(v);
            ProfileView profileView = new ProfileView(getApplicationContext(), userData);
            LinearLayout profile_layout = profileView.getSmallLayout(select_team);
            profile_layout.setTag(userData);
            profile_layout.setOnClickListener(smallItemListener);
            select_team.addView(profile_layout);
        }
    };

    @Override
    public void httpUpdate(String response) {
        team_list.removeAllViews();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                UserData userData = new UserData(jsonObject);

                if(isSelected(userData.name)) continue;

                ProfileView profileView = new ProfileView(this, userData);
                LinearLayout profile_layout = profileView.getListLayout(team_list);
                profile_layout.setTag(userData);
                profile_layout.setOnClickListener(listItemListener);

                team_list.addView(profile_layout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void httpError(String response) {

    }

    public void selected(){
        Intent intent = new Intent();

        UserDataWrapper userDataWrapper = new UserDataWrapper(userDatas);

        intent.putExtra("userDatas",userDataWrapper);

        setResult(RESULT_OK, intent);

        finish();
    }

    public void canceled(){
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_keyword:
                searchTeam(v);
                break;
            case R.id.submit_button:
                selected();
                break;
            case R.id.cancel_button:
                canceled();
                break;
        }
    }
}
