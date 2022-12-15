package notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slack implements Alarm {

    private final Logger log = LoggerFactory.getLogger(Slack.class);

    @Override
    public void run(final String message) {
        System.out.println(message);
    }
}
