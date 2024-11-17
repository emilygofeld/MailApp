import di.DependencyInjection;
import presentation.Mail;

public class Main {
    public static void main(String[] args) {
        DependencyInjection.setup();
        Mail mail = new Mail();
        mail.run();
    }
}