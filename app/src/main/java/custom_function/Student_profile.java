package custom_function;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;

public class Student_profile {



    public static JSONObject getProfile() throws JSONException {




        JSONObject student_profile = new JSONObject();

        student_profile.put("roll","111");

        return student_profile;

    }


}
