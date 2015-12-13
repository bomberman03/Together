package mobileprogramming.koreatech.together.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2015-12-12.
 */
public class TaskData {

    public ProjectData projectData;
    public UserData userData;

    public String id;
    public String name;
    public String summary;
    public String end_date;

    public TaskData(ProjectData projectData, UserData userData, String id,
                         String name, String summary, String end_date)
    {
        this.projectData = projectData;
        this.userData = userData;
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.end_date = end_date;
    }

    public TaskData(ProjectData projectData, UserData userData,
                         String name, String summary, String end_date)
    {
        this.projectData = projectData;
        this.userData = userData;
        this.id = "";
        this.name = name;
        this.summary = summary;
        this.end_date = end_date;
    }
}
