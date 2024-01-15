package hotel_booking.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int userID;
    private String username;
    private String password;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // JSON'a dönüştürme
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", this.userID);
            json.put("username", this.username);
        } catch (JSONException e) {
            // Handle the exception
            e.printStackTrace();
        }
        // Diğer alanlar için de benzer şekilde
        return json;
    }

    // JSON'dan dönüştürme
    public static User fromJson(JSONObject json) {
        User user = new User();
        try {

            user.userID = json.getInt("userID");
            user.username = json.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
