package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by vinsm on 20.03.2018.
 */

public class MyDatePicker extends DatePickerDialog {
    public MyDatePicker(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }
}
