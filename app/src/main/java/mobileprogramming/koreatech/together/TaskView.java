package mobileprogramming.koreatech.together;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2015-11-30.
 */
public class TaskView {

    public String title;
    public int dueDay;
    private ArrayList<FeedView> feedViews;

    public Context context;

    public LayoutInflater layoutInflater;

    public TaskView(Context context, String title, int dueDay){
        this.context = context;
        this.title = title;
        this.dueDay = dueDay;
        this.feedViews = new ArrayList<>();

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TaskView(Context context, String title, int dueDay, ArrayList<FeedView> feedViews){
        this.context = context;
        this.title = title;
        this.dueDay = dueDay;
        this.feedViews = feedViews;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LinearLayout getLayout(ViewGroup parent){
        final LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.task_layout, parent, false);

        TextView title_text = (TextView) res_layout.findViewById(R.id.title_text);
        title_text.setText(title);
        TextView dueDay_text = (TextView) res_layout.findViewById(R.id.dueDay_text);
        dueDay_text.setText("D-" + dueDay);

        LinearLayout task_layout = (LinearLayout) res_layout.findViewById(R.id.task_layout);
        task_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout feed_layout = (LinearLayout) res_layout.findViewById(R.id.feed_layout);
                boolean isExpand = feed_layout.getChildCount() > 0;
                feed_layout.removeAllViews();
                if (!isExpand) {
                    for (FeedView feedView : feedViews) {
                        feed_layout.addView(feedView.getLayout(feed_layout));
                    }
                    feed_layout.addView(getInsertLayout(feed_layout));
                }
            }
        });

        return res_layout;
    }

    public LinearLayout getInsertLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.feed_insert_layout, parent, false);
        return res_layout;
    }

}
