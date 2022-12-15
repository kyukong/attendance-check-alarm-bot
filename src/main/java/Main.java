import notification.Slack;
import scheduler.Scheduler;

public class Main {
    public static void main(String[] args) {
        new Scheduler(new Slack()).start();
    }
}
