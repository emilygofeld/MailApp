package data.datasource;

import domain.datasource.ChatDataSource;
import domain.models.Chat;
import domain.models.Message;
import domain.models.User;

import java.util.*;

public class MemoryChatDataSource implements ChatDataSource {
    private final Map<String, Chat> chats = new HashMap<>();

    @Override
    public List<Chat> getUserChats(final User user) {
        List<Chat> userChats = new ArrayList<>();

        for (Chat chat : chats.values()) {
            if (chat.usersInChat().contains(user)) {
                userChats.add(chat);
            }
        }

        return userChats;
    }

    @Override
    public void createChat(final User user1, final User user2) {
        String id = generateChatId(user1, user2);
        Chat chat = new Chat(id, List.of(user1, user2), new ArrayList<>());
        chats.put(id, chat);
    }

    @Override
    public void insertMessage(final String chatId, final Message message) {
        if (chatId == null)
            return;

        Chat chat = getChatById(chatId);
        if (chat == null)
            return;

        List<Message> updatedMessages = new ArrayList<>(chat.messages());
        updatedMessages.add(message);
        chats.put(chat.id(), new Chat(chat.id(), chat.usersInChat(), updatedMessages));
    }

    @Override
    public Chat getChatById(final String chatId) {
        return chats.get(chatId);
    }

    @Override
    public Chat getChatByUsers(final User user1, final User user2) {
        for (Chat chat : chats.values()) {
            if (chat.usersInChat().contains(user1) && chat.usersInChat().contains(user2))
                return chat;
        }
        return null;
    }

    private String generateChatId(final User user1, final User user2) {
        return user1.id() + "/" + user2.id();
    }
}
