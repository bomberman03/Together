package mobileprogramming.koreatech.together.Manager;

import android.text.format.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mobileprogramming.koreatech.together.Data.ProjectData;
import mobileprogramming.koreatech.together.Data.TaskData;
import mobileprogramming.koreatech.together.Data.UserData;
import mobileprogramming.koreatech.together.HttpUpdater;
import mobileprogramming.koreatech.together.Request.GetProjectListRequest;
import mobileprogramming.koreatech.together.Util.SimpleToast;

/**
 * Created by user on 2015-12-12.
 */
public class ProjectManager implements HttpUpdater {

    public static ProjectManager singleton = new ProjectManager();

    public static ProjectManager getInstance(){
        return singleton;
    }

    private ArrayList<ProjectData> projectDatas;
    private ArrayList<HttpUpdater> updateQueue;
    private Time lastUpdate;

    private HashMap<String, UserData> userDataHashMap = new HashMap<>();
    private HashMap<String, ProjectData> projectDataHashMap = new HashMap<>();
    private HashMap<String, TaskData> taskDataHashMap = new HashMap<>();

    public int display_type = ALL_TASK;

    public static final int ONLY_USER_TASK = 0;
    public static final int RECENT_DUE_TASK = 1;
    public static final int ALL_TASK = 2;

    public void addUserData(String key, UserData userData){
        userDataHashMap.put(key, userData);
    }

    public UserData getUserData(String key){
        return userDataHashMap.get(key);
    }

    public void addProjectData(String key, ProjectData projectData){
        projectDataHashMap.put(key, projectData);
    }

    public ProjectData getProjectData(String key){
        return projectDataHashMap.get(key);
    }

    public void addTaskData(String key, TaskData taskData){
        taskDataHashMap.put(key, taskData);
    }

    public TaskData getTaskData(String key){
        return taskDataHashMap.get(key);
    }

    public ArrayList<ProjectData> getProjectDatas(){
        if(projectDatas == null) projectDatas = new ArrayList<>();
        return projectDatas;
    }

    public void refreshProjectList(HttpUpdater httpUpdater){
        projectDataHashMap.clear();
        userDataHashMap.clear();
        taskDataHashMap.clear();
        if(updateQueue==null) updateQueue = new ArrayList<>();
        updateQueue.add(httpUpdater);
        if(lastUpdate != null) {
            Time now = new Time();
            now.setToNow();
            if (now.toMillis(false) - lastUpdate.toMillis(false) < 5000){
                return;
            }
        }
        else lastUpdate = new Time();
        lastUpdate.setToNow();
        UserData userData = AuthManager.getInstance().getUserData();
        GetProjectListRequest getProjectRequest = new GetProjectListRequest(this, userData);
        getProjectRequest.execute();
    }

    @Override
    public void httpUpdate(String response) {
        if(projectDatas == null) projectDatas = new ArrayList<>();
        projectDatas.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String id = String.valueOf(jsonObject.get("id"));
                ProjectData projectData = getProjectData(id);
                if(projectData == null) {
                    projectData = new ProjectData(jsonObject);
                    addProjectData(id, projectData);
                }
                projectDatas.add(projectData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        while(!updateQueue.isEmpty()) {
            HttpUpdater httpUpdater = updateQueue.get(0);
            updateQueue.remove(0);
            httpUpdater.httpUpdate(response);
        }
        SimpleToast.getInstance().makeToast("데이터 요청이 성공적으로 이루어졌습니다.");
    }

    @Override
    public void httpError(String response) {
        SimpleToast.getInstance().makeToast("뭔가 잘못됬어 인터넷도 확인해보자");
    }
}
