package mobileprogramming.koreatech.together.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mobileprogramming.koreatech.together.Manager.AuthManager;
import mobileprogramming.koreatech.together.Data.FeedData;
import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.Dialog.TeamDialog;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Fragment.NavigationDrawerFragment;
import mobileprogramming.koreatech.together.Fragment.NewTaskDrawerFragment;
import mobileprogramming.koreatech.together.Manager.ProjectManager;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Request.InsertFeedRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;
import mobileprogramming.koreatech.together.View.ProjectView;
import mobileprogramming.koreatech.together.View.TaskView;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, HttpUpdater {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NewTaskDrawerFragment mNewTaskDrawerFragment;
    private DrawerLayout mDrawerLayout;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private FrameLayout frameLayout;
    private LinearLayout main_layout;

    private LinearLayout feed_insert_layout;
    private LayoutInflater layoutInflater;

    private LinearLayout selected_task_layout;
    private EditText feed_summary;
    private ImageView feed_submit;

    public void showInsertLayout(LinearLayout task_layout){

        if(this.selected_task_layout != null) {
            LinearLayout feed_layout = (LinearLayout) selected_task_layout.findViewById(R.id.feed_layout);
            feed_layout.removeAllViews();
            LinearLayout content_layout = (LinearLayout) selected_task_layout.findViewById(R.id.task_layout);
            content_layout.setBackgroundResource(R.drawable.borders);
        }

        this.selected_task_layout = task_layout;

        LinearLayout content_layout = (LinearLayout) selected_task_layout.findViewById(R.id.task_layout);
        content_layout.setBackgroundResource(R.drawable.selected_borders);

        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.feed_insert_layout, feed_insert_layout, false);
        feed_insert_layout.addView(res_layout);

        feed_summary = (EditText) res_layout.findViewById(R.id.feed_summary);
        feed_submit = (ImageView) res_layout.findViewById(R.id.feed_submit);
        feed_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFeedRequest();
                feed_summary.setText("");
            }
        });
    }

    public void insertFeedRequest(){
        TaskView taskView = (TaskView) selected_task_layout.getTag();
        UserData userData = AuthManager.getInstance().getUserData();
        TaskData taskData = taskView.taskData;
        String summary = feed_summary.getText().toString();

        FeedData feedData = new FeedData(taskData, userData, summary);

        InsertFeedRequest insertFeedRequest = new InsertFeedRequest(new HttpUpdater() {
            @Override
            public void httpUpdate(String response) {
                TaskView taskView = (TaskView) selected_task_layout.getTag();
                taskView.requestFeedList();
            }

            @Override
            public void httpError(String response) {
                SimpleToast.getInstance().makeToast("피드가 잘 안들어갔어 인터넷 확인해봐");
            }

        }, feedData);
        insertFeedRequest.execute();
    }

    public boolean hideInsertLayout(LinearLayout task_layout){
        boolean res = this.selected_task_layout == task_layout;
        if(this.selected_task_layout != null) {
            LinearLayout feed_layout = (LinearLayout) selected_task_layout.findViewById(R.id.feed_layout);
            feed_layout.removeAllViews();
            LinearLayout content_layout = (LinearLayout) selected_task_layout.findViewById(R.id.task_layout);
            content_layout.setBackgroundResource(R.drawable.borders);
        }
        if(res) this.selected_task_layout = null;
        feed_insert_layout.removeAllViews();
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.container);
        frameLayout.setBackgroundColor(Color.WHITE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFocusableInTouchMode(false);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mNewTaskDrawerFragment = (NewTaskDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.new_task_drawer);

        // Set up the drawer.
        mNewTaskDrawerFragment.setUp(
                R.id.new_task_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        feed_insert_layout = (LinearLayout) findViewById(R.id.feed_insert_layout);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        refreshProjectList();
    }

    public void refreshProjectList(){
        ProjectManager.getInstance().refreshProjectList(this);
        ProjectManager.getInstance().refreshProjectList(mNavigationDrawerFragment);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 2))
                .commit();
    }

    public void selectDisplayType(View view){
        switch (view.getId()){
            case R.id.my_duty:
                ProjectManager.getInstance().display_type = ProjectManager.ONLY_USER_TASK;
                refreshProjectList();
                mNavigationDrawerFragment.closeDrawer();
                break;
            case R.id.today_duty:
                ProjectManager.getInstance().display_type = ProjectManager.RECENT_DUE_TASK;
                refreshProjectList();
                mNavigationDrawerFragment.closeDrawer();
                break;
            case R.id.all_duty:
                ProjectManager.getInstance().display_type = ProjectManager.ALL_TASK;
                refreshProjectList();
                mNavigationDrawerFragment.closeDrawer();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.action_bar_bg)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        //BadgeUtil.setBadgeCount(this, icon, 12);
        restoreActionBar();
        return true;
    }

    public void newProject(View view){
        Intent intent = new Intent(this, NewProjectActivity.class);
        startActivity(intent);
    }

    public void selectTeam(View view){
        TeamDialog teamDialog = new TeamDialog(this, mNewTaskDrawerFragment);
        teamDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            refreshProjectList();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void httpUpdate(String response) {
        main_layout.removeAllViews();
        for (final ProjectData projectData : ProjectManager.getInstance().getProjectDatas()) {
            final ProjectView projectView = new ProjectView(this, projectData);
            final LinearLayout mobile_layout = projectView.getLayout(main_layout);
            mobile_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewTaskDrawerFragment.openDrawer(projectData, projectView);
                }
            });
            main_layout.addView(mobile_layout);
        }
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("뭔가 잘못됬어요, 인터넷도 확인해보세요");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    @Override
    public void onBackPressed() {
        if(mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }
        else if(mNewTaskDrawerFragment.isDrawerOpen()){
            mNewTaskDrawerFragment.closeDrawer();
        }
        else super.onBackPressed();
    }
}
