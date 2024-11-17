package domain.models;

public record Message(
	User user,
	String text
) {}