package mobileprogramming.koreatech.together.View;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Activity.MainActivity;
import mobileprogramming.koreatech.together.Data.FeedData;
import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.Dialog.ModifyTaskDialog;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Manager.ProjectManager;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.GetFeedListRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;

/**
 * Created by user on 2015-11-30.
 */
public class TaskView implements HttpUpdater{

    public String title;
    public int dueDay;
    public ArrayList<FeedData> feedDatas;
    public Context context;
    public LayoutInflater layoutInflater;
    public TaskData taskData;
    public LinearLayout res_layout;

    public TaskView(Context context, TaskData taskData) {
        this.context = context;
        this.taskData = taskData;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Time getTime(String date){
        String[] date_arr = date.split("-");

        int year = Integer.parseInt(date_arr[0]);
        int month = Integer.parseInt(date_arr[1]);
        int day = Integer.parseInt(date_arr[2]);

        Time time = new Time();
        time.set(day, month-1, year);

        return time;
    }

    public int getDueDay(){
        Time time = getTime(taskData.end_date);
        Time now =  new Time();
        now.setToNow();
        long diff = time.toMillis(true) - now.toMillis(true);
        return (int)( diff / (1000 * 60 * 60 * 24) );
    }

    public LinearLayout getLayout(ViewGroup parent){
        res_layout = (LinearLayout) layoutInflater.inflate(R.layout.task_layout, parent, false);

        TextView task_user = (TextView) res_layout.findViewById(R.id.task_user);
        task_user.setText(taskData.userData.name);

        TextView title_text = (TextView) res_layout.findViewById(R.id.title_text);
        title_text.setText(taskData.name);

        TextView dueDay_text = (TextView) res_layout.findViewById(R.id.dueDay_text);
        dueDay = getDueDay();
        if(dueDay > 0) {
            dueDay_text.setText("D-" + dueDay);
            if(dueDay < 10) {
                dueDay_text.setTextColor(Color.RED);
            }
        }
        else if(dueDay==0) {
            dueDay_text.setText("오늘");
        }
        else{
            dueDay_text.setText("만료");
        }


        TextView task_summary = (TextView) res_layout.findViewById(R.id.task_summary);
        task_summary.setText(taskData.summary);

        final LinearLayout task_layout = (LinearLayout) res_layout.findViewById(R.id.task_layout);
        task_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                if (!mainActivity.hideInsertLayout(res_layout)) {
                    mainActivity.showInsertLayout(res_layout);
                    requestFeedList();
                }
            }
        });
        task_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ModifyTaskDialog modifyTaskDialog = new ModifyTaskDialog(context);
                modifyTaskDialog.show();
                return false;
            }
        });

        return res_layout;
    }

    public void requestFeedList(){
        GetFeedListRequest getFeedListRequest = new GetFeedListRequest(this, taskData);
        getFeedListRequest.execute();
    }

    @Override
    public void httpUpdate(String response) {
        if(feedDatas == null) feedDatas = new ArrayList<>();
        feedDatas.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                TaskData taskData = ProjectManager.getInstance().getTaskData(String.valueOf(jsonObject.get("task")));
                UserData userData = ProjectManager.getInstance().getUserData(String.valueOf(jsonObject.get("user")));
                String id = String.valueOf(jsonObject.get("id"));
                String summary = (String) jsonObject.get("summary");
                FeedData feedData = new FeedData(taskData, userData, id, summary);
                feedDatas.add(feedData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout feed_layout = (LinearLayout) res_layout.findViewById(R.id.feed_layout);
        feed_layout.removeAllViews();
        for(FeedData feedData : feedDatas) {
            FeedView feedView = new FeedView(context, feedData);
            LinearLayout new_layout = feedView.getLayout(feed_layout);
            new_layout.setTag(feedView);
            feed_layout.addView(new_layout);
        }
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("뭔가 잘못됬어! 인터넷도 확인해봐");
    }
}
