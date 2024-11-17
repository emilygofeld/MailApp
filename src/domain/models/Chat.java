package domain.models;

import java.util.List;

public record Chat(
    String id,           
    List<User> usersInChat,
    List<Message> messages
) {}
