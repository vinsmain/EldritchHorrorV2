package ru.mgusev.eldritchhorror.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Objects;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.interfaces.DialogFragmentListener;

public class ClearDialogFragment extends DialogFragment {

    DialogFragmentListener listener;

    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
        System.out.println("LISTENER " + this.listener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("CONTEXT " + context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DialogFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement FragmentDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setMessage(R.string.cleanDialogMessage)
                .setPositiveButton(R.string.messageOK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("DIALOG " + listener);
                        System.out.println("FRAGMENT " + ClearDialogFragment.this);
                        listener.onDialogPositiveClick(ClearDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.messageCancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.onDialogNegativeClick(ClearDialogFragment.this);
                        }
                    });
        return builder.create();
    }
}
