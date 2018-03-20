package ru.mgusev.eldritchhorror.ui.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartDataFragment extends MvpAppCompatFragment implements StartDataView, View.OnClickListener, DatePickerDialog.OnDateSetListener, DatePickerDialog.OnDismissListener, DatePickerDialog.OnKeyListener{

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    private ImageView dateButton;
    private TextView dateTV;
    private DatePickerDialog datePickerDialog;

    public static StartDataFragment newInstance(int page) {
        StartDataFragment pageFragment = new StartDataFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_data, null);
        dateButton = view.findViewById(R.id.start_data_date_button);
        dateTV = view.findViewById(R.id.start_data_date_tv);
        dateButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void showDatePicker(Calendar date) {
        datePickerDialog = new DatePickerDialog(getContext(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnDismissListener(this);
        datePickerDialog.setOnKeyListener(this);
        datePickerDialog.show();
        System.out.println("SHOW");
    }

    @Override
    public void setDateToField(Calendar date) {
        dateTV.setText(DateUtils.formatDateTime(getContext(), date.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void dismissDatePicker() {
        if (datePickerDialog != null) datePickerDialog.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_data_date_button:
                startDataPresenter.showDatePicker();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("DATE SET");
        startDataPresenter.setDateToField(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDestroy() {
        System.out.println("DESTROY 1234");
        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            System.out.println("DESTROY");
            datePickerDialog.setOnDismissListener(null);
            datePickerDialog.dismiss();
        }

        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        System.out.println("CANCEL " + datePickerDialog.isShowing());
        startDataPresenter.dismissDatePicker();
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        System.out.println("CHANGE");
        return false;
    }
}