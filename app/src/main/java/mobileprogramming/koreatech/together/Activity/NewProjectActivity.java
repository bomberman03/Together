package mobileprogramming.koreatech.together.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.InsertProjectRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;
import mobileprogramming.koreatech.together.View.ProfileView;
import mobileprogramming.koreatech.together.Wrapper.UserDataWrapper;

public class NewProjectActivity extends AppCompatActivity implements HttpUpdater {

    private EditText project_title;
    private EditText project_summary;

    private TextView project_start_date;
    private TextView project_end_date;

    private Button submit;

    private LinearLayout team_layout;

    private ArrayList<UserData> userDatas;

    private final int TEAM_SEARCH_ACTIVITY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        project_title = (EditText) findViewById(R.id.project_title);
        project_summary = (EditText) findViewById(R.id.project_summary);
        project_start_date = (TextView) findViewById(R.id.project_start_date);
        project_end_date = (TextView) findViewById(R.id.project_end_date);

        team_layout = (LinearLayout) findViewById(R.id.team_layout);

        userDatas = new ArrayList<>();

        UserData userData = AuthManager.getInstance().getUserData();
        userDatas.add(userData);

        ProfileView profileView = new ProfileView(this, userData);

        team_layout.addView(profileView.getSmallLayout(team_layout));

        submit = (Button) findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProjectRequest();
            }
        });
    }

    public ProjectData extractProjectData(){
        String name = project_title.getText().toString();
        String summary = project_summary.getText().toString();
        String start_date = project_start_date.getText().toString();
        String end_date = project_end_date.getText().toString();
        ProjectData projectData = new ProjectData(name, summary, start_date, end_date, userDatas);
        return projectData;
    }

    public void insertProjectRequest(){
        ProjectData projectData = extractProjectData();
        InsertProjectRequest insertProjectRequest = new InsertProjectRequest(this, projectData);
        insertProjectRequest.execute();
    }

    public void refreshTeam(){
        team_layout.removeAllViews();
        for (UserData userData : userDatas){
            ProfileView profileView = new ProfileView(this, userData);
            team_layout.addView(profileView.getSmallLayout(team_layout));
        }
    }

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d-%d-%d", year,monthOfYear+1, dayOfMonth);
            project_end_date.setText(msg);
        }
    };

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d-%d-%d", year,monthOfYear+1, dayOfMonth);
            project_start_date.setText(msg);
        }
    };

    public void selectStartDate(View view){
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, startDateSetListener, year, month, day).show();
    }

    public void selectEndDate(View view){
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, endDateSetListener, year, month, day).show();
    }

    public void showTeamSelectActivity(View view){
        Intent intent = new Intent(this, TeamSearchActivity.class);
        intent.putExtra("userDatas",new UserDataWrapper(userDatas));
        startActivityForResult(intent, TEAM_SEARCH_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TEAM_SEARCH_ACTIVITY:
                if(resultCode == RESULT_OK){
                    UserDataWrapper userDataWrapper = (UserDataWrapper) data.getSerializableExtra("userDatas");
                    userDatas = userDataWrapper.getUserDatas();
                    refreshTeam();
                }
                break;
        }
    }

    @Override
    public void httpUpdate(String response) {
        SimpleToast.getInstance().makeToast("프로젝트가 성공적으로 등록됬어요!");
        finish();
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("잘못 입력한거 같아요, 인터넷도 확인해보세요");
    }
}
