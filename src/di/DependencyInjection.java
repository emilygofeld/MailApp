package di;

import data.datasource.MemoryChatDataSource;
import data.datasource.MemoryUserDataSource;
import domain.datasource.ChatDataSource;
import domain.datasource.UserDataSource;

public class DependencyInjection {
    private static UserDataSource userDataSource;
    private static ChatDataSource chatDataSource;

    public static void setup() {
        userDataSource = new MemoryUserDataSource();
        chatDataSource = new MemoryChatDataSource();
    }

    public static UserDataSource getUserDataSource() {
        return userDataSource;
    }

    public static ChatDataSource getChatDataSource() {
        return chatDataSource;
    }
}
