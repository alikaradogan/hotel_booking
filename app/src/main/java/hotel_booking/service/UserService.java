package hotel_booking.service;

import java.io.IOException;
import java.util.List;

import hotel_booking.dao.UserDAO;
import hotel_booking.model.User;

public class UserService {
    private static UserService instance;
    private UserDAO userDAO;
    public User currentUser;

    private UserService() {
        this.userDAO = new UserDAO();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User loginUser(String username, String password) throws RuntimeException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        } else {
            return null;
        }
    }

    public void registerUser(User user) throws IOException {
        if (user.getUsername().equals("") || user.getPassword().equals("")) {
            throw new IOException("Kullanıcı adı veya şifre boş olamaz.");
        }
        if (user.getPassword().length() < 6) {
            throw new IOException("Şifre en az 6 karakter olmalıdır.");
        }
        if (isUserExists(user.getUsername())) {
            throw new IOException("Kullanıcı adı zaten alınmış.");
        }
        userDAO.addUser(user);
        currentUser = user;
    }

    public boolean isUserExists(String username) {
        return userDAO.getUserByUsername(username) != null;
    }

    public User getUser(int userID) {
        return userDAO.getUserById(userID);
    }

    public void updateUser(User user) throws IOException {
        List<User> users = userDAO.getUsers();

        for (User any : users) {
            if (any.getUserID() == user.getUserID()) {
                continue;
            }
            if (any.getUsername().equals(user.getUsername())) {
                throw new IOException("Kullanıcı adı zaten alınmış.");
            }
        }
        userDAO.updateUser(user);
    }

    public void deleteUser(int userID) {
        userDAO.deleteUser(userID);
    }
}
