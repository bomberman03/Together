package mobileprogramming.koreatech.together.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.View.ProfileView;

public class NewProjectActivity extends AppCompatActivity {

    private EditText project_title;
    private EditText project_summary;
    private TextView project_start_date;
    private TextView project_end_date;
    private GregorianCalendar start_date;
    private GregorianCalendar end_date;

    private LinearLayout team_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        project_title = (EditText) findViewById(R.id.project_title);
        project_summary = (EditText) findViewById(R.id.project_summary);
        project_start_date = (TextView) findViewById(R.id.project_start_date);
        project_end_date = (TextView) findViewById(R.id.project_end_date);

        team_layout = (LinearLayout) findViewById(R.id.team_layout);

        ProfileView profileView = new ProfileView(this, "컴퓨터공학부", "정재현", "");
        team_layout.addView(profileView.getLayout(team_layout));
    }

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d 년 %d 월 %d 일", year,monthOfYear+1, dayOfMonth);
            project_end_date.setText(msg);
            end_date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        }
    };

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d 년 %d 월 %d 일", year,monthOfYear+1, dayOfMonth);
            project_start_date.setText(msg);
            start_date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
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
}
