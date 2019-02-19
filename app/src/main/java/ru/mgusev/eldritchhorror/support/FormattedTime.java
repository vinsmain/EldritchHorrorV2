package ru.mgusev.eldritchhorror.support;

import android.content.Context;

import ru.mgusev.eldritchhorror.R;

public class FormattedTime {

    public static String getTime(Context context, int time) {
        String hour;
        String minute;
        if (time / 60 < 10)
            hour = "0" + time / 60;
        else
            hour = String.valueOf(time / 60);

        if (time % 60 < 10)
            minute = "0" + time % 60;
        else
            minute = String.valueOf(time % 60);

        return hour + " " + context.getResources().getString(R.string.time_hour) + " " + minute + " " + context.getResources().getString(R.string.time_minute);
    }
}