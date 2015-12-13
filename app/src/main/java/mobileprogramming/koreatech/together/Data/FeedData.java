package mobileprogramming.koreatech.together.Data;

/**
 * Created by user on 2015-12-12.
 */
public class FeedData {

    public TaskData taskData;
    public UserData userData;
    public String id;
    public String summary;

    public FeedData(TaskData taskData, UserData userData, String id, String summary){
        this.taskData = taskData;
        this.userData = userData;
        this.id = id;
        this.summary = summary;
    }

    public FeedData(TaskData taskData, UserData userData, String summary){
        this.taskData = taskData;
        this.userData = userData;
        this.id = "";
        this.summary = summary;
    }
}
