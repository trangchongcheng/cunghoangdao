package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cheng.com.android.cunghoangdao.MyApplication;
import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 3/30/2016.
 */
public class TimeAgoUtils {

    public static String getDate(long d, boolean... args) {
        boolean longerVersion = args.length > 0 ? args[0] : false;
        long diff = new Date().getTime() - d;
        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double weeks = days / 7;
        double months = weeks / 4;
        double years = months / 12;

        if (years >= 1) {
            return yearsRemaining(years, longerVersion, MyApplication.getContext());
        } else if (months >= 1) {
            return monthsRemaining(months, longerVersion, MyApplication.getContext());
        } else if (weeks >= 1) {
            return weeksRemaining(weeks, longerVersion, MyApplication.getContext());
        } else if (days >= 1) {
            return daysRemaining(days, longerVersion,MyApplication.getContext());
        } else if (hours >= 1) {
            return hoursRemaining(hours, longerVersion,MyApplication.getContext());
        } else if (minutes >= 1) {
            return minutesRemaining(minutes, longerVersion,MyApplication.getContext());
        } else {
            return secondsRemaining(seconds, longerVersion,MyApplication.getContext());
        }
    }

    /**
     * @param years
     * @param longerVersion
     * @return
     */
    public static String yearsRemaining(double years, boolean longerVersion, Context context) {
        int y = (int) years;
        if (longerVersion) {
            if (y == 1) {
                return context.getResources().getString(R.string.nam_truoc);
            } else {
                return context.getResources().getString(R.string.nam_truoc);
            }
        }
        return Integer.toString(y) + "y";
    }

    public static String monthsRemaining(double months, boolean longerVersion, Context context) {
        int m = (int) months;
        if (longerVersion) {
            if (m == 1) {
                return context.getResources().getString(R.string.thang_truoc);
            } else {
                return Integer.toString(m) + context.getResources().getString(R.string.thang_truoc);
            }
        }
        return Integer.toString(m) + "m";
    }

    public static String weeksRemaining(double weeks, boolean longerVersion, Context context) {
        int w = (int) weeks;
        if (longerVersion) {
            if (w < 2) {
                return context.getResources().getString(R.string.tuan_truoc);
            } else {
                return Integer.toString(w) + context.getResources().getString(R.string.tuan_truoc);
            }
        }
        return Integer.toString(w) + "w";
    }

    public static String daysRemaining(double days, boolean longerVersion, Context context) {
        int d = (int) days;
        if (longerVersion) {
            if (d < 2) {
                return context.getResources().getString(R.string.hom_qua);
            } else {
                return Integer.toString(d) + context.getResources().getString(R.string.ngay_truoc);
            }
        }
        return Integer.toString(d) + "d";
    }

    public static String hoursRemaining(double hours, boolean longerVersion, Context context) {
        int h = (int) hours;
        if (longerVersion) {
            if (h < 2) {
                return context.getResources().getString(R.string.mot_gio);
            } else {
                return Integer.toString(h) + context.getResources().getString(R.string.mot_gio);
            }
        }
        return Integer.toString(h) + "h";
    }

    public static String minutesRemaining(double minutes, boolean longerVersion, Context context) {
        int m = (int) minutes;
        if (longerVersion) {
            if (m < 2) {
                return context.getResources().getString(R.string.mot_phut_truoc);
            } else {
                return Integer.toString(m) + context.getResources().getString(R.string.phut_truoc);
            }
        }
        return Integer.toString(m) + "m";
    }

    public static String secondsRemaining(double seconds, boolean longerVersion, Context context) {
        int s = (int) seconds;
        if (longerVersion) {
            if (s <= 5) {
                return context.getResources().getString(R.string.giay_truoc);
            } else {
                return Integer.toString(s) + context.getResources().getString(R.string.giay_truoc);
            }
        }
        // The difference is the now and just now
        if (s <= 5) {
            return "Now";
        }
        return Integer.toString(s) + "s";
    }


    public static long formatTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.US);
            Date date = format.parse(time);
            long miliseconds = date.getTime();
            return miliseconds;
        } catch (ParseException e) {
            return 0;
        }
    }
}
