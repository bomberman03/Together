package mobileprogramming.koreatech.together.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by user on 2015-12-10.
 */
public class UserData implements Serializable {

    public String id;
    public String student_id;
    public String department_name;
    public String grade;
    public String name;
    public String phone_number;

    public UserData(String student_id, String department_name,
                    String grade, String name, String phone_number)
    {
        this.id = "";
        this.student_id = student_id;
        this.department_name = department_name;
        this.grade = grade;
        this.name = name;
        this.phone_number = phone_number;
    }

    public UserData(String id, String student_id, String department_name,
                    String grade, String name, String phone_number)
    {
        this.id = id;
        this.student_id = student_id;
        this.department_name = department_name;
        this.grade = grade;
        this.name = name;
        this.phone_number = phone_number;
    }

    public UserData(JSONObject jsonObject){
        try {
            this.id = String.valueOf(jsonObject.get("id"));
            this.student_id = (String) jsonObject.get("student_id");
            this.department_name = (String) jsonObject.get("department_name");
            this.grade = (String) jsonObject.get("grade");
            this.name = (String) jsonObject.get("name");
            this.phone_number = (String) jsonObject.get("phone_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
