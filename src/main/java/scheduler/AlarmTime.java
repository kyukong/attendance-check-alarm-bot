package scheduler;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public enum AlarmTime {

    KDT_START(9, 50, 0, "입실하세요!"),
    KDT_END(18, 0, 0, "퇴실하세요!"),
    ATTENDANCE_START(9, 50, 0, "왔다감 작성하세요!"),
    ATTENDANCE_END(18, 0, 0, "왔다감 작성하세요!"),
    ;

    private final int hour;
    private final int minutes;
    private final int seconds;
    private final String message;

    AlarmTime(final int hour, final int minutes, final int seconds, final String message) {
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        this.message = message;
    }

    public Date getFirstTime() {
        final LocalDateTime today = LocalDateTime.now();
        final LocalTime time = LocalTime.of(hour, minutes, seconds);
        final LocalDate date = LocalDate.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        final LocalDateTime firstTime = LocalDateTime.of(date, time);
        if (firstTime.isBefore(today)) {
            final LocalDateTime tomorrow = firstTime.plusDays(1);
            return Timestamp.valueOf(tomorrow);
        }
        return Timestamp.valueOf(firstTime);
    }

    public long getPeriod() {
        return 1000L * 60 * 60 * 24;
    }

    public String getMessage() {
        return message;
    }
}
