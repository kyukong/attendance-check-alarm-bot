package scheduler;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import notification.Alarm;

public class Scheduler {

    private final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private final Alarm alarm;

    public Scheduler(final Alarm alarm) {
        this.alarm = alarm;
    }

    public void start() {
        log.info("Scheduler start!!!");

        for (AlarmTime alarm : AlarmTime.values()) {
            setAlarm(alarm);
        }
    }

    private void setAlarm(final AlarmTime alarm) {
        final TimerTask timerTask = getTimerTask(alarm.getMessage());
        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(timerTask, alarm.getFirstTime(), alarm.getPeriod());
        log.info("{} 알람이 설정되었습니다. (start: {}, period: {})", alarm.name(), alarm.getFirstTime(), alarm.getPeriod());
    }

    private TimerTask getTimerTask(final String message) {
        return new TimerTask() {
            @Override
            public void run() {
                alarm.run(message);
            }
        };
    }
}
