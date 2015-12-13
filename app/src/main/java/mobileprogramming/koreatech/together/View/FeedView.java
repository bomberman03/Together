package mobileprogramming.koreatech.together.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobileprogramming.koreatech.together.Data.FeedData;
import mobileprogramming.koreatech.together.R;

/**
 * Created by user on 2015-11-30.
 */
public class FeedView {

    public FeedData feedData;
    public Context context;
    public LayoutInflater layoutInflater;

    public FeedView(Context context, FeedData feedData){
        this.context = context;
        this.feedData = feedData;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LinearLayout getLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.feed_layout, parent, false);
        TextView feed_content = (TextView) res_layout.findViewById(R.id.feed_content);
        feed_content.setText(feedData.userData.name + " : " + feedData.summary);
        return res_layout;
    }
}
