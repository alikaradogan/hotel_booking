package hotel_booking.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hotel_booking.model.User;

public class UserDAO {
    private String filePath = "/Users/ali/Developer/resmgmt/db/users.json";

    public List<User> getUsers() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray usersArray = new JSONArray(content);
            List<User> users = new ArrayList<>();

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                User user = new User();
                user.setUserID(userObject.getInt("userID"));
                user.setUsername(userObject.getString("username"));
                user.setPassword(userObject.getString("password"));
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addUser(User user) {
        List<User> users = getUsers();
        users.sort((a, b) -> a.getUserID() - b.getUserID());
        user.setUserID(users.get(users.size() - 1).getUserID() + 1);
        JSONObject userObject = new JSONObject();
        userObject.put("userID", user.getUserID());
        userObject.put("username", user.getUsername());
        userObject.put("password", user.getPassword());
        users.add(user);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(new JSONArray(users).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userID) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == userID) {
                users.remove(i);
                break;
            }
        }
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(new JSONArray(users).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == user.getUserID()) {
                users.set(i, user);
                break;
            }
        }
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(new JSONArray(users).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userID) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == userID) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

}
