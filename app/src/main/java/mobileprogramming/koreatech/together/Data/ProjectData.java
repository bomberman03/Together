package mobileprogramming.koreatech.together.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import mobileprogramming.koreatech.together.Manager.ProjectManager;

/**
 * Created by user on 2015-12-12.
 */
public class ProjectData implements Serializable{

    public String id;
    public String name;
    public String summary;
    public String start_date;
    public String end_date;
    public ArrayList<UserData> users;

    public ProjectData(String name, String summary,
                       String start_date, String end_date, ArrayList<UserData> users)
    {
        this.id = "";
        this.name = name;
        this.summary = summary;
        this.start_date = start_date;
        this.end_date = end_date;
        this.users = users;
    }

    public ProjectData(String id, String name, String summary,
                       String start_date, String end_date, ArrayList<UserData> users)
    {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.start_date = start_date;
        this.end_date = end_date;
        this.users = users;
    }

    public ProjectData(JSONObject jsonObject){
        try {

            this.id = String.valueOf(jsonObject.get("id"));
            this.name = (String) jsonObject.get("name");
            this.summary = (String) jsonObject.get("summary");
            this.start_date = (String) jsonObject.get("start_date");
            this.end_date = (String) jsonObject.get("end_date");

            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            ArrayList<UserData> userDatas = new ArrayList<>();

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject userJson = (JSONObject) jsonArray.get(i);
                String key = String.valueOf(userJson.get("id"));
                UserData userData = ProjectManager.getInstance().getUserData(key);
                if( userData == null) {
                    userData = new UserData((JSONObject) jsonArray.get(i));
                    ProjectManager.getInstance().addUserData(key, userData);
                }
                userDatas.add(userData);
            }
            this.users = userDatas;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
