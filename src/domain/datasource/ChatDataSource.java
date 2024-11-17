package domain.datasource;

import domain.models.Chat;
import domain.models.Message;
import domain.models.User;

import java.util.List;


public interface ChatDataSource {
    void createChat(final User user1, final User user2);
    List<Chat> getUserChats (final User user);
    void insertMessage (final String chatId, final Message message);
    Chat getChatById (final String id);
    Chat getChatByUsers(final User user1, final User user2);
}