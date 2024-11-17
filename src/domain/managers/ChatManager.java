package domain.managers;

import di.DependencyInjection;
import domain.datasource.ChatDataSource;
import domain.models.Chat;
import domain.models.Message;
import domain.models.User;

public class ChatManager {
    private final ChatDataSource chatDataSource;

    private static ChatManager instance = null;

    private ChatManager() {
        this.chatDataSource = DependencyInjection.getChatDataSource();
    }

    public static ChatManager getInstance() {
        if (instance == null)
            instance = new ChatManager();

        return instance;
    }

    public void sendMessage(Message message, User receiver) {
        Chat chat = chatDataSource.getChatByUsers(message.user(), receiver);
        if (chat == null) {
            chatDataSource.createChat(message.user(), receiver);
            chat = chatDataSource.getChatByUsers(message.user(), receiver);
        }

        chatDataSource.insertMessage(chat.id(), message);
    }
}
