package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.FragmentStartDataBinding;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartDataFragment extends MvpAppCompatFragment implements StartDataView, DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    private FragmentStartDataBinding binding;

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
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

        binding = FragmentStartDataBinding.inflate(inflater, container, false);

        ancientOneAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.startDataAncientOneSpinner.setAdapter(ancientOneAdapter);
        preludeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        preludeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.startDataPreludeSpinner.setAdapter(preludeAdapter);
        playersCountAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner);
        playersCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.startDataPlayersCountSpinner.setAdapter(playersCountAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                StartDataFragment.this.onItemSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        binding.startDataAncientOneSpinner.setOnItemSelectedListener(itemSelectedListener);
        binding.startDataPreludeSpinner.setOnItemSelectedListener(itemSelectedListener);
        binding.startDataPlayersCountSpinner.setOnItemSelectedListener(itemSelectedListener);
        binding.startDataDateRow.setOnClickListener(this::onClick);
        binding.ancientOneRow.setOnClickListener(this::onClick);
        binding.preludeRow.setOnClickListener(this::onClick);
        binding.playersCountRow.setOnClickListener(this::onClick);
        binding.startDataEasyMythos.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.startDataNormalMythos.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.startDataHardMythos.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.startDataStartingRumor.setOnCheckedChangeListener(this::onCheckedChanged);

        return binding.getRoot();
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
        binding.startDataDateTv.setText(formatter.format(date.getTime()));
    }

    @Override
    public void dismissDatePicker() {
        binding.startDataDateRow.setEnabled(true);
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
        binding.startDataAncientOneSpinner.setSelection(ancientOnePosition, false);
        binding.startDataPreludeSpinner.setSelection(preludePosition, false);
        binding.startDataPlayersCountSpinner.setSelection(playersCountPosition, false);
    }

    @Override
    public void setSwitchValue(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue) {
        binding.startDataEasyMythos.setChecked(easyMythosValue);
        binding.startDataNormalMythos.setChecked(normalMythosValue);
        binding.startDataHardMythos.setChecked(hardMythosValue);
        binding.startDataStartingRumor.setChecked(startingRumorValue);
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

    public void onItemSelected() {
        startDataPresenter.setCurrentAncientOne((String) binding.startDataAncientOneSpinner.getSelectedItem());
        startDataPresenter.setCurrentPrelude((String) binding.startDataPreludeSpinner.getSelectedItem());
        startDataPresenter.setCurrentPlayersCount((String) binding.startDataPlayersCountSpinner.getSelectedItem());
        startDataPresenter.setSpinnerPosition();
    }

    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.start_data_date_row) {
                binding.startDataDateRow.setEnabled(false);
                startDataPresenter.showDatePicker();
        } else if (viewId == R.id.ancient_one_row) {
                binding.startDataAncientOneSpinner.performClick();
        } else if (viewId == R.id.prelude_row) {
                binding.startDataPreludeSpinner.performClick();
        } else if (viewId == R.id.players_count_row) {
                binding.startDataPlayersCountSpinner.performClick();
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int viewId = compoundButton.getId();
        if (viewId == R.id.start_data_easy_mythos) {
            if (!b && !binding.startDataNormalMythos.isChecked() && !binding.startDataHardMythos.isChecked())
                binding.startDataNormalMythos.setChecked(true);
        } else if (viewId == R.id.start_data_normal_mythos) {
            if (!b && !binding.startDataEasyMythos.isChecked() && !binding.startDataHardMythos.isChecked())
                binding.startDataEasyMythos.setChecked(true);
        } else if (viewId == R.id.start_data_hard_mythos) {
            if (!b && !binding.startDataEasyMythos.isChecked() && !binding.startDataNormalMythos.isChecked())
                binding.startDataEasyMythos.setChecked(true);
        }
        startDataPresenter.setSwitchValueToGame(binding.startDataEasyMythos.isChecked(), binding.startDataNormalMythos.isChecked(), binding.startDataHardMythos.isChecked(), binding.startDataStartingRumor.isChecked());
        startDataPresenter.setSwitchValue();
    }

    @Override
    public void setPreludeText(String text) {
        binding.startDataPreludeText.setText(text);
    }

    @Override
    public void setPreludeTextRowVisibility(boolean visible) {
        binding.startDataPreludeTextRow.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        if (datePickerDialog != null) {
            startDataPresenter.setTempDate(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth());
            datePickerDialog.dismiss();
        }
        binding = null;
        super.onDestroyView();
    }
}
