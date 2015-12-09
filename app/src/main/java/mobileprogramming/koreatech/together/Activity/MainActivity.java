package mobileprogramming.koreatech.together.Activity;

import android.app.Activity;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import mobileprogramming.koreatech.together.Dialog.NotiDialog;
import mobileprogramming.koreatech.together.Dialog.TeamDialog;
import mobileprogramming.koreatech.together.NavigationDrawerFragment;
import mobileprogramming.koreatech.together.R;
import mobileprogramming.koreatech.together.Utils2;
import mobileprogramming.koreatech.together.View.FeedView;
import mobileprogramming.koreatech.together.View.ProjectView;
import mobileprogramming.koreatech.together.View.TaskView;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerFragment mNewTaskDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private FrameLayout frameLayout;
    private LinearLayout main_layout;

    private LinearLayout feed_insert_layout;
    private LayoutInflater layoutInflater;

    private LinearLayout selected_task_layout;

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

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        feed_insert_layout = (LinearLayout) findViewById(R.id.feed_insert_layout);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ArrayList<FeedView> feedViews = new ArrayList<>();
        feedViews.add(new FeedView(this, "피드백입니다."));
        feedViews.add(new FeedView(this, "피드백입니다."));
        feedViews.add(new FeedView(this, "피드백입니다."));
        feedViews.add(new FeedView(this, "피드백입니다."));
        feedViews.add(new FeedView(this, "피드백입니다."));
        feedViews.add(new FeedView(this, "피드백입니다."));

        ArrayList<TaskView> taskViews = new ArrayList<>();
        taskViews.add(new TaskView(this, "테스크입니다.", 1, feedViews));
        taskViews.add(new TaskView(this, "테스크입니다.", 10));
        taskViews.add(new TaskView(this, "테스크입니다.", 2));
        taskViews.add(new TaskView(this, "테스크입니다.", 3));
        taskViews.add(new TaskView(this, "테스크입니다.", 5, feedViews));
        taskViews.add(new TaskView(this, "테스크입니다.", 3));

        ProjectView projectView = new ProjectView(this, "Mobile Project", taskViews);
        main_layout.addView(projectView.getLayout(main_layout));

        projectView = new ProjectView(this, "DBP Project", taskViews);
        main_layout.addView(projectView.getLayout(main_layout));

        projectView = new ProjectView(this, "HRD Project", taskViews);
        main_layout.addView(projectView.getLayout(main_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
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

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            MenuItem item = menu.findItem(R.id.action_notifications);
            LayerDrawable icon = (LayerDrawable) item.getIcon();

            // Update LayerDrawable's BadgeDrawable
            Utils2.setBadgeCount(this, icon, 2);
            restoreActionBar();
            return true;
        }


        return super.onCreateOptionsMenu(menu);
    }

    public void newProject(View view){
        Intent intent = new Intent(this, NewProjectActivity.class);
        startActivity(intent);
    }

    public void selectTeam(View view){
        TeamDialog teamDialog = new TeamDialog(this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "왼쪽버튼 Click!!",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "오른쪽버튼 Click!!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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
            NotiDialog notiDialog = new NotiDialog(this,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "왼쪽버튼 Click!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "오른쪽버튼 Click!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            notiDialog.show();
        }

        return super.onOptionsItemSelected(item);
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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
