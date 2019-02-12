package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartDataFragment extends MvpAppCompatFragment implements StartDataView, DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    @BindView(R.id.start_data_date_tv) TextView dateTV;
    @BindView(R.id.start_data_ancient_one_spinner) Spinner ancientOneSpinner;
    @BindView(R.id.start_data_prelude_spinner) Spinner preludeSpinner;
    @BindView(R.id.start_data_players_count_spinner) Spinner playersCountSpinner;
    @BindView(R.id.start_data_easy_mythos) Switch easyMythosSwitch;
    @BindView(R.id.start_data_normal_mythos) Switch normalMythosSwitch;
    @BindView(R.id.start_data_hard_mythos) Switch hardMythosSwitch;
    @BindView(R.id.start_data_starting_rumor) Switch startingRumorSwitch;
    @BindView(R.id.start_data_prelude_text_row) TableRow preludeTextRow;
    @BindView(R.id.start_data_prelude_text) TextView preludeTextTV;
    @BindView(R.id.start_data_date_row) TableRow dateRow;

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private Unbinder unbinder;
    private DatePickerDialog datePickerDialog;
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

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(getContext(), MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        View view = inflater.inflate(R.layout.fragment_start_data, null);
        unbinder = ButterKnife.bind(this, view);

        ancientOneAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ancientOneSpinner.setAdapter(ancientOneAdapter);
        preludeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        preludeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preludeSpinner.setAdapter(preludeAdapter);
        playersCountAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        playersCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersCountSpinner.setAdapter(playersCountAdapter);

        return view;
    }

    @Override
    public void showDatePicker(Calendar date) {
        Calendar currentDate;
        if (startDataPresenter.getTempDate() != null) currentDate = startDataPresenter.getTempDate();
        else currentDate = date;
        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), this, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    @Override
    public void setDateToField(Calendar date) {
        dateTV.setText(formatter.format(date.getTime()));
    }

    @Override
    public void dismissDatePicker() {
        dateRow.setEnabled(true);
        datePickerDialog = null;
        startDataPresenter.clearTempDate();
        //Delete showDatePicker() from currentState with DismissDialogStrategy
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        startDataPresenter.setDate(year, monthOfYear, dayOfMonth);
        startDataPresenter.setDateToField();
        datePickerDialog.cancel();
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
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

    @OnItemSelected({R.id.start_data_ancient_one_spinner, R.id.start_data_prelude_spinner, R.id.start_data_players_count_spinner})
    public void onItemSelected() {
        startDataPresenter.setCurrentAncientOne((String) ancientOneSpinner.getSelectedItem());
        startDataPresenter.setCurrentPrelude((String) preludeSpinner.getSelectedItem());
        startDataPresenter.setCurrentPlayersCount((String) playersCountSpinner.getSelectedItem());
        startDataPresenter.setSpinnerPosition();
    }

    @OnClick({R.id.start_data_date_row, R.id.ancient_one_row, R.id.prelude_row, R.id.players_count_row})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_data_date_row:
                dateRow.setEnabled(false);
                startDataPresenter.showDatePicker();
                break;
            case R.id.ancient_one_row:
                ancientOneSpinner.performClick();
                break;
            case R.id.prelude_row:
                preludeSpinner.performClick();
                break;
            case R.id.players_count_row:
                playersCountSpinner.performClick();
                break;
        }
    }

    @OnCheckedChanged ({R.id.start_data_easy_mythos, R.id.start_data_normal_mythos, R.id.start_data_hard_mythos, R.id.start_data_starting_rumor})
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.start_data_easy_mythos:
                if (!b && !normalMythosSwitch.isChecked() && !hardMythosSwitch.isChecked())
                    normalMythosSwitch.setChecked(true);
                break;
            case R.id.start_data_normal_mythos:
                if (!b && !easyMythosSwitch.isChecked() && !hardMythosSwitch.isChecked())
                    easyMythosSwitch.setChecked(true);
                break;
            case R.id.start_data_hard_mythos:
                if (!b && !easyMythosSwitch.isChecked() && !normalMythosSwitch.isChecked())
                    easyMythosSwitch.setChecked(true);
                break;
            default:
                break;
        }
        startDataPresenter.setSwitchValueToGame(easyMythosSwitch.isChecked(), normalMythosSwitch.isChecked(), hardMythosSwitch.isChecked(), startingRumorSwitch.isChecked());
        startDataPresenter.setSwitchValue();
    }

    @Override
    public void setPreludeText(String text) {
        preludeTextTV.setText(text);
    }

    @Override
    public void setPreludeTextRowVisibility(boolean visible) {
        preludeTextRow.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (datePickerDialog != null) {
            startDataPresenter.setTempDate(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth());
            datePickerDialog.dismiss();
        }
        unbinder.unbind();
    }
}