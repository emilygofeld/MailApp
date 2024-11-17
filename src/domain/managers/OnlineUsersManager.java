package domain.managers;

import di.DependencyInjection;
import domain.datasource.UserDataSource;
import domain.models.User;

import java.util.HashMap;
import java.util.Map;

public class OnlineUsersManager {
    private final UserDataSource userDataSource;

    private final Map<Integer,User> onlineUsers;

    public OnlineUsersManager() {
        this.userDataSource = DependencyInjection.getUserDataSource();
        onlineUsers = new HashMap<>();
    }

    private static OnlineUsersManager instance = null;

    public static OnlineUsersManager getInstance() {
        if (instance == null)
            instance = new OnlineUsersManager();

        return instance;
    }

    public User signup(String username, String password) {
        if (userDataSource.getUserByName(username) != null)  // username taken or user exists already
            return null;

        userDataSource.insertUser(new User(username, password, -1));
        User user = userDataSource.getUserByName(username);
        onlineUsers.put(user.id(), user);

        return user;
    }

    public User login(String username, String password) {
        final User user = userDataSource.getUserByName(username);
        if (user == null) // username doesn't exist
            return null;

        if (user.password().equals(password)) {
            onlineUsers.put(user.id(), user);
            return user;
        }
        return null;
    }

    public void logout(int id) {
        onlineUsers.remove(id);
    }
}
