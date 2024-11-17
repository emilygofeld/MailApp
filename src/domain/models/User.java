package domain.models;


public record User(
		String username,
		String password,
		int id
) {}