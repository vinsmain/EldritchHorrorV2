package ru.mgusev.eldritchhorror.ui.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartDataFragment extends MvpAppCompatFragment implements StartDataView, View.OnClickListener, DatePickerDialog.OnDateSetListener,
        DatePickerDialog.OnDismissListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private ImageView dateButton;
    private TextView dateTV;
    private DatePickerDialog datePickerDialog;
    private Spinner playersCountSpinner;
    private Spinner ancientOneSpinner;
    private Spinner preludeSpinner;
    private Switch easyMythosSwitch;
    private Switch normalMythosSwitch;
    private Switch hardMythosSwitch;
    private Switch startingRumorSwitch;

    private ArrayAdapter<String> ancientOneAdapter;
    private ArrayAdapter<String> preludeAdapter;
    private ArrayAdapter<String> playersCountAdapter;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_data, null);
        dateButton = view.findViewById(R.id.start_data_date_button);
        dateTV = view.findViewById(R.id.start_data_date_tv);

        playersCountSpinner = view.findViewById(R.id.start_data_players_count_spinner);
        ancientOneSpinner = view.findViewById(R.id.start_data_ancient_one_spinner);
        preludeSpinner = view.findViewById(R.id.start_data_prelude_spinner);

        easyMythosSwitch = view.findViewById(R.id.start_data_easy_mythos);
        normalMythosSwitch = view.findViewById(R.id.start_data_normal_mythos);
        hardMythosSwitch = view.findViewById(R.id.start_data_hard_mythos);
        startingRumorSwitch = view.findViewById(R.id.start_data_starting_rumor);

        ancientOneAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ancientOneSpinner.setAdapter(ancientOneAdapter);

        preludeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        preludeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preludeSpinner.setAdapter(preludeAdapter);

        playersCountAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        playersCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersCountSpinner.setAdapter(playersCountAdapter);

        initListeners();
        return view;
    }

    private void initListeners() {
        dateButton.setOnClickListener(this);
        easyMythosSwitch.setOnCheckedChangeListener(this);
        normalMythosSwitch.setOnCheckedChangeListener(this);
        hardMythosSwitch.setOnCheckedChangeListener(this);
        startingRumorSwitch.setOnCheckedChangeListener(this);
        ancientOneSpinner.setOnItemSelectedListener(this);
        preludeSpinner.setOnItemSelectedListener(this);
        playersCountSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showDatePicker(Calendar date) {
        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnDismissListener(this);
        datePickerDialog.show();
    }

    @Override
    public void setDateToField(Calendar date) {
        dateTV.setText(formatter.format(date.getTime()));
    }

    @Override
    public void dismissDatePicker() {
        //if (datePickerDialog != null) datePickerDialog.dismiss();
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
        startDataPresenter.setDate(year, monthOfYear, dayOfMonth);
        startDataPresenter.setDateToField();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        System.out.println("CANCEL " + datePickerDialog.isShowing());
        startDataPresenter.dismissDatePicker();
    }

    @Override
    public void setSpinnerPosition(int ancientOnePosition, int preludePosition, int playersCountPosition) {
        ancientOneSpinner.setSelection(ancientOnePosition, false);
        preludeSpinner.setSelection(preludePosition, false);
        playersCountSpinner.setSelection(playersCountPosition, false);
    }

    @Override
    public void setSwitchValue(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue) {
        easyMythosSwitch.setChecked(easyMythosValue);
        normalMythosSwitch.setChecked(normalMythosValue);
        hardMythosSwitch.setChecked(hardMythosValue);
        startingRumorSwitch.setChecked(startingRumorValue);
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneList) {
        ancientOneAdapter.clear();
        ancientOneAdapter.addAll(ancientOneList);
        ancientOneAdapter.notifyDataSetChanged();
    }

    @Override
    public void initPreludeSpinner(List<String> preludeList) {
        preludeAdapter.clear();
        preludeAdapter.addAll(preludeList);
        preludeAdapter.notifyDataSetChanged();
    }

    @Override
    public void initPlayersCountSpinner(List<String> playersCountList) {
        playersCountAdapter.clear();
        playersCountAdapter.addAll(playersCountList);
        playersCountAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        startDataPresenter.setCurrentAncientOne((String) ancientOneSpinner.getSelectedItem());
        startDataPresenter.setCurrentPrelude((String) preludeSpinner.getSelectedItem());
        startDataPresenter.setCurrentPlayersCount((String) playersCountSpinner.getSelectedItem());
        startDataPresenter.setSpinnerPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.start_data_easy_mythos:
                if (!b && !normalMythosSwitch.isChecked() && !hardMythosSwitch.isChecked()) normalMythosSwitch.setChecked(true);
                break;
            case R.id.start_data_normal_mythos:
                if (!b && !easyMythosSwitch.isChecked() && !hardMythosSwitch.isChecked()) easyMythosSwitch.setChecked(true);
                break;
            case R.id.start_data_hard_mythos:
                if (!b && !easyMythosSwitch.isChecked() && !normalMythosSwitch.isChecked()) easyMythosSwitch.setChecked(true);
                break;
            default:
                break;
        }
        startDataPresenter.setSwitchValueToGame(easyMythosSwitch.isChecked(), normalMythosSwitch.isChecked(), hardMythosSwitch.isChecked(), startingRumorSwitch.isChecked());
        startDataPresenter.setSwitchValue();
    }
}