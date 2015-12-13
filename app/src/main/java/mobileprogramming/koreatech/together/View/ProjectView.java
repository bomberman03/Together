package mobileprogramming.koreatech.together.View;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.ProjectManager;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.GetTaskListRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;

/**
 * Created by user on 2015-11-30.
 */
public class ProjectView implements HttpUpdater{

    public ProjectData projectData;
    public ArrayList<TaskData> taskDatas;
    public Context context;
    public LayoutInflater layoutInflater;
    public LinearLayout res_layout;

    public ProjectView(Context context, ProjectData projectData){
        this.context = context;
        this.projectData = projectData;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LinearLayout getLayout(ViewGroup parent){
        res_layout = (LinearLayout) layoutInflater.inflate(R.layout.project_layout, parent, false);
        TextView project_title =  (TextView) res_layout.findViewById(R.id.project_title);
        project_title.setText(projectData.name);

        requestTaskList();

        return res_layout;
    }

    public void requestTaskList(){
        GetTaskListRequest getTaskListRequest = new GetTaskListRequest(this, projectData);
        getTaskListRequest.execute();
    }

    public Time getTime(String date){
        String[] date_arr = date.split("-");

        int year = Integer.parseInt(date_arr[0]);
        int month = Integer.parseInt(date_arr[1]);
        int day = Integer.parseInt(date_arr[2]);

        Time time = new Time();
        time.set(day, month - 1, year);

        return time;
    }

    public int getDueDay(TaskData taskData){
        Time time = getTime(taskData.end_date);
        Time now =  new Time();
        now.setToNow();
        long diff = time.toMillis(true) - now.toMillis(true);
        return (int)( diff / (1000 * 60 * 60 * 24) );
    }

    @Override
    public void httpUpdate(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            if(taskDatas == null) taskDatas = new ArrayList<>();
            taskDatas.clear();

            for(int i=0; i < jsonArray.length(); i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String id = String.valueOf(jsonObject.get("id"));

                TaskData taskData = ProjectManager.getInstance().getTaskData(id);

                if(taskData == null) {
                    UserData userData = ProjectManager.getInstance().getUserData(String.valueOf(jsonObject.get("user")));

                    String name = (String) jsonObject.get("name");
                    String summary = (String) jsonObject.get("summary");
                    String end_date = (String) jsonObject.get("end_date");

                    taskData = new TaskData(projectData, userData, id, name, summary, end_date);
                    ProjectManager.getInstance().addTaskData(id, taskData);
                }

                taskDatas.add(taskData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout task_layout = (LinearLayout) res_layout.findViewById(R.id.task_layout);
        task_layout.removeAllViews();
        for(TaskData taskData: taskDatas){
            switch (ProjectManager.getInstance().display_type) {
                case ProjectManager.ONLY_USER_TASK:
                    if(!taskData.userData.name.equals(AuthManager.getInstance().getUserData().name)) {
                        continue;
                    }
                    break;
                case ProjectManager.RECENT_DUE_TASK:
                    int diff = getDueDay(taskData);
                    if(diff > 10) continue;
                    break;
            }
            TaskView taskView = new TaskView(context, taskData);
            LinearLayout new_layout = taskView.getLayout(task_layout);
            new_layout.setTag(taskView);
            task_layout.addView(new_layout);
        }
        SimpleToast.getInstance().makeToast("프로젝트 정보를 성공적으로 받았습니다.");
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("뭔가 잘못됬어! 인터넷도 확인해봐");
    }
}
