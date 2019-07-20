package ru.mgusev.eldritchhorror.interfaces;

import androidx.fragment.app.DialogFragment;

public interface DialogFragmentListener {
    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);
}