package presentation;

import di.DependencyInjection;
import domain.managers.ChatManager;
import domain.managers.OnlineUsersManager;
import domain.models.Chat;
import domain.models.Message;
import domain.models.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Mail {

    private final Scanner scanner = new Scanner(System.in);
    private User user;

    public Mail() {
        user = null;
    }

    public void run() {
        boolean close = false;
        while (!close) {
            if (user == null) {
                auth();
            }

            showMenu();

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> printUsers();
                    case 2 -> sendMessage();
                    case 3 -> seeChat();
                    case 4 -> seeAllChats();
                    case 5 -> {
                        OnlineUsersManager.getInstance().logout(user.id());
                        user = null;
                    }
                    case 6 -> close = true;
                    default -> System.out.println("Option doesn't exist.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private void showMenu() {
        System.out.print("""
                
                Welcome to the note app!
                
                Select one of the following options (by number):
                1. show all users
                2. send message
                3. see certain chat
                4. see all chats
                5. logout
                6. exit
                """);
    }

    private void auth() {
        System.out.println("\n1. signup\n2. login\n");
        int choice;
        do {
            choice = scanner.nextInt();
            switch (choice) {
                case 1, 2 -> {
                    System.out.println("Enter username and password");
                    String username = scanner.next();
                    String password = scanner.next();
                    scanner.nextLine();
                    final User user = (choice == 1) ? OnlineUsersManager.getInstance().signup(username, password) : OnlineUsersManager.getInstance().login(username, password);
                    if (user == null) {
                        System.out.println("Error occurred.\n");
                        return;
                    }
                    this.user = user;
                }
                default -> {
                    System.out.println("Enter choice again: ");
                }
            }
        } while (choice != 1 && choice != 2);
    }

    private void printUsers() {
        for (final User user : DependencyInjection.getUserDataSource().getALlUsers()) {
            System.out.println(user.username());
        }
    }

    private void sendMessage() {
        System.out.println("Enter name of user you'd like to send a message to: ");
        final User receiver = DependencyInjection.getUserDataSource().getUserByName(scanner.next());
        scanner.nextLine();
        if (receiver == null) {
            return;
        }

        System.out.println("Enter message: ");
        final String text = scanner.nextLine();
        ChatManager.getInstance().sendMessage(
                new Message(user, text),
                receiver
        );
    }

    private void seeChat() {
        System.out.println("Enter name of user you'd like to watch your chat with: ");
        final User otherUser = DependencyInjection.getUserDataSource().getUserByName(scanner.nextLine());
        if (otherUser == null) {
            return;
        }

        Chat chat = DependencyInjection.getChatDataSource().getChatByUsers(user, otherUser);
        if (chat == null) {
            System.out.println("Error finding chat.");
            return;
        }

        for (final Message message : chat.messages()) {
            System.out.printf("\n%s:\n%s\n", message.user().username(), message.text());
        }
    }

    private void seeAllChats() {
        final List<Chat> chats = DependencyInjection.getChatDataSource().getUserChats(user);
        for (final Chat chat : chats) {
            final User userInChat = (chat.usersInChat().get(0).username().equals(user.username())) ? chat.usersInChat().get(0) : chat.usersInChat().get(1);
            final Message currMessage = chat.messages().get(chat.messages().size() - 1);
            System.out.printf("\nChat with %s\nLast message: %s said: %s\n", userInChat.username(), currMessage.user().username(), currMessage.text());
        }
    }
}
