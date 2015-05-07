package Utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final Integer GREATER = 1;
    public static final Integer LOWER = -1;
    public static final Integer EQUAL = 0;

    public static Date convertTimestampSecondsToDate(Integer timestampInSeconds) {
        Long timestampInMilis = (long) timestampInSeconds * 1000;
        return new Date(timestampInMilis);
    }

    public static Date convertHoursStringToDate(String str) {
        String parts[] = str.split(":");
        Integer hours = Integer.parseInt(parts[0]);
        Integer minutes = Integer.parseInt(parts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        Date date = calendar.getTime();
        return date;
    }

    public static Integer compareTwoTimes(Date time1, Date time2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        Integer hours1 = calendar1.get(Calendar.HOUR_OF_DAY);
        Integer minutes1 = calendar1.get(Calendar.MINUTE);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);
        Integer hours2 = calendar2.get(Calendar.HOUR_OF_DAY);
        Integer minutes2 = calendar2.get(Calendar.MINUTE);

        if (hours1 < hours2) {
            return TimeUtils.LOWER;
        } else if (hours1 > hours2) {
            return TimeUtils.GREATER;
        } else {
            if (minutes1 < minutes2) {
                return TimeUtils.LOWER;
            } else if (minutes1 > minutes2) {
                return TimeUtils.GREATER;
            } else {
                return TimeUtils.EQUAL;
            }
        }
    }


}
