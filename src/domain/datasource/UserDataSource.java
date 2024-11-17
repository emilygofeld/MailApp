package domain.datasource;

import domain.models.User;

import java.util.List;

public interface UserDataSource {
	void insertUser(final User user);
	User getUserByName(final String name);
	List<User> getALlUsers();
	void deleteUser(final int id);
}