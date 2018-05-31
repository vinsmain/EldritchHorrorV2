package ru.mgusev.eldritchhorror.interfaces;

import android.support.v4.app.DialogFragment;

public interface DialogFragmentListener {
    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);
}