package james.alarmio.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import james.alarmio.R;

public class FormatUtils {

    public static final String FORMAT_12H = "h:mm:ss";
    public static final String FORMAT_24H = "HH:mm:ss";
    public static final String FORMAT_12H_SHORT = "h:mm a";
    public static final String FORMAT_24H_SHORT = "HH:mm";
    public static final String FORMAT_DATE = "MMMM d yyyy";

    public static String getFormat(Context context) {
        return DateFormat.is24HourFormat(context) ? FORMAT_24H : FORMAT_12H;
    }

    public static String getShortFormat(Context context) {
        return DateFormat.is24HourFormat(context) ? FORMAT_24H_SHORT : FORMAT_12H_SHORT;
    }

    public static String format(Context context, Date time) {
        return format(time, getFormat(context));
    }

    public static String formatShort(Context context, Date time) {
        return format(time, getShortFormat(context));
    }

    public static String format(Date time, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(time);
    }

    public static String formatMillis(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1);
        long micros = TimeUnit.MILLISECONDS.toMicros(millis) % TimeUnit.SECONDS.toMicros(1) / 10000;

        if (hours > 0)
            return String.format(Locale.getDefault(), "%dh %02dm %02ds %02d", hours, minutes, seconds, micros);
        else if (minutes > 0)
            return String.format(Locale.getDefault(), "%dm %02ds %02d", minutes, seconds, micros);
        else return String.format(Locale.getDefault(), "%ds %02d", seconds, micros);
    }

    public static String formatUnit(Context context, int minutes) {
        long hours = TimeUnit.MINUTES.toHours(minutes);
        minutes %= TimeUnit.HOURS.toMinutes(1);
        if (hours > 0)
            return String.format(Locale.getDefault(), "%d " + context.getString(hours > 1 ? R.string.word_hours : R.string.word_hour) + (minutes > 0 ? " " + context.getString(R.string.word_join) + " %02d" + context.getString(minutes > 1 ? R.string.word_minutes : R.string.word_minute) : ""), hours, minutes);
        else
            return String.format(Locale.getDefault(), "%d " + context.getString(minutes > 1 ? R.string.word_minutes : R.string.word_minute), minutes);
    }
}
