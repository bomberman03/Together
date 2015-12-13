package mobileprogramming.koreatech.together.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.View.ProfileView;

public class ProjectDetailActivity extends AppCompatActivity {

    private TextView project_title;
    private TextView project_summary;

    private TextView project_start_date;
    private TextView project_end_date;

    private LinearLayout team_layout;

    private ProjectData projectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        project_title = (TextView) findViewById(R.id.project_title);
        project_summary = (TextView) findViewById(R.id.project_summary);
        project_start_date = (TextView) findViewById(R.id.project_start_date);
        project_end_date = (TextView) findViewById(R.id.project_end_date);

        team_layout = (LinearLayout) findViewById(R.id.team_layout);

        Intent intent = getIntent();
        projectData = (ProjectData) intent.getSerializableExtra("projectData");

        fillForm();
    }

    public void fillForm(){
        project_title.setText(projectData.name);
        project_summary.setText(projectData.summary);
        project_start_date.setText(projectData.start_date);
        project_end_date.setText(projectData.end_date);

        refreshTeam(projectData.users);
    }

    public void refreshTeam(ArrayList<UserData> userDatas){
        team_layout.removeAllViews();
        for (UserData userData : userDatas){
            ProfileView profileView = new ProfileView(this, userData);
            team_layout.addView(profileView.getSmallLayout(team_layout));
        }
    }

}
