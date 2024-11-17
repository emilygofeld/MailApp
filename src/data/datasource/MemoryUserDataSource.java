package data.datasource;

import domain.datasource.UserDataSource;
import domain.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryUserDataSource implements UserDataSource {
    private final Map<Integer, User> users;
	private int currId;

	public MemoryUserDataSource() {
		users = new HashMap<>();
		currId = 0;
	}

	@Override
	public void insertUser(final User user) {
		users.put(currId, new User(user.username(), user.password(), currId));
		currId++;
	}

    @Override
	public User getUserByName(final String name) {
		for (User user : users.values()) {
			if (user.username().equals(name))
				return user;
		}
		return null;
	}

	@Override
	public List<User> getALlUsers() {
        return new ArrayList<>(users.values());
	}

	@Override
	public void deleteUser(final int id) {
		users.remove(id);
	}
}