package mobileprogramming.koreatech.together;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2015-11-30.
 */
public class ProjectView {

    public String title;
    public ArrayList<TaskView> taskViews;

    public Context context;

    public LayoutInflater layoutInflater;

    public ProjectView(Context context, String title, ArrayList<TaskView> taskViews){
        this.context = context;
        this.title = title;
        this.taskViews = taskViews;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LinearLayout getLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.project_layout, parent, false);
        TextView project_title =  (TextView) res_layout.findViewById(R.id.project_title);
        project_title.setText(title);
        LinearLayout task_layout = (LinearLayout) res_layout.findViewById(R.id.task_layout);
        for(TaskView taskView: taskViews){
            task_layout.addView(taskView.getLayout(task_layout));
        }
        return res_layout;
    }
}
