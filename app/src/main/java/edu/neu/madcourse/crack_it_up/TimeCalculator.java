package edu.neu.madcourse.crack_it_up;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeCalculator {

    public String getTimeDifference(long duration) {

        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);

        String time_elapsed = "time unknown";
        if (seconds < 60) {
            time_elapsed = "just now";
        } else if(minutes == 1) {
            time_elapsed = "1 minute ago";
        }else if(minutes > 1 && minutes < 60) {
            time_elapsed = minutes + " minutes ago";
        }else if(hours == 1) {
            time_elapsed = "1 hour ago";
        }else if(hours > 1 && hours < 24) {
            time_elapsed = hours + " hours ago";
        }else if(days == 1) {
            time_elapsed = "1 day ago";
        }else if(days > 1) {
            time_elapsed = days + " days ago";
        }

        return time_elapsed;
    }
}
