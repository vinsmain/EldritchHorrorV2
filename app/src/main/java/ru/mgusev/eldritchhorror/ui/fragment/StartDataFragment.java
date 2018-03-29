package ru.mgusev.eldritchhorror.ui.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartDataFragment extends MvpAppCompatFragment implements StartDataView, View.OnClickListener, DatePickerDialog.OnDateSetListener, DatePickerDialog.OnDismissListener {

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    private ImageView dateButton;
    private TextView dateTV;
    private DatePickerDialog datePickerDialog;
    private Spinner playersCountSpinner;
    private Spinner ancientOneSpinner;
    private Spinner preludeSpinner;

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

        playersCountSpinner = view.findViewById(R.id.start_data_players_count_spinner);
        ancientOneSpinner = view.findViewById(R.id.start_data_ancient_one_spinner);
        preludeSpinner = view.findViewById(R.id.start_data_prelude_spinner);

        startDataPresenter.setSpinnerPosition();
        return view;
    }

    @Override
    public void showDatePicker(Calendar date) {
        datePickerDialog = new DatePickerDialog(getContext(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnDismissListener(this);
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
        startDataPresenter.setDate(year, monthOfYear, dayOfMonth);
        startDataPresenter.setDateToField();
    }

    @Override
    public void onDestroy() {
        System.out.println("DESTROY 1234");
        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            System.out.println("DESTROY");
            //startDataPresenter.setDate(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth());
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
    public void initPlayersCountSpinner(String[] playersCountArray) {
        ArrayAdapter<String> playersCountAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, playersCountArray);
        playersCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playersCountSpinner.setAdapter(playersCountAdapter);

        playersCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startDataPresenter.setCurrentPlayersCountPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void setSpinnerPosition(int ancientOnePosition, int preludePosition, int playersCountPosition) {
        ancientOneSpinner.setSelection(ancientOnePosition);
        preludeSpinner.setSelection(preludePosition);
        playersCountSpinner.setSelection(playersCountPosition);
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneList) {
        ArrayAdapter<String> ancientOneAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, ancientOneList);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ancientOneSpinner.setAdapter(ancientOneAdapter);

        ancientOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startDataPresenter.setCurrentAncientOnePosition(i);
                /*int resourceId;
                AncientOne selectedAncientOne;
                Resources resources = getResources();
                try {
                    selectedAncientOne = HelperFactory.getStaticHelper().getAncientOneDAO().getAncientOneByName(ancientOneArray.get(i));
                    resourceId = resources.getIdentifier(selectedAncientOne.imageResource, "drawable", getActivity().getPackageName());
                    ((ImageView)getActivity().findViewById(R.id.background_pager)).setImageResource(resourceId);
                    String resourceName = HelperFactory.getStaticHelper().getExpansionDAO().getImageResourceByID(selectedAncientOne.expansionID);
                    if (resourceName != null) {
                        resourceId = resources.getIdentifier(resourceName, "drawable", getContext().getPackageName());
                        expansionImage.setImageResource(resourceId);
                        expansionImage.setVisibility(View.VISIBLE);
                    } else expansionImage.setVisibility(View.GONE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addDataToGame();
                initAncientOneArray();
                ancientOneAdapter.notifyDataSetChanged();
                ((ResultGameFragment)activity.getPagerAdapter().getItem(2)).setVisibilityRadioButtons();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void initPreludeSpinner(List<String> preludeList) {
        ArrayAdapter<String> preludeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, preludeList);
        preludeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        preludeSpinner.setAdapter(preludeAdapter);

        preludeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startDataPresenter.setCurrentPreludePosition(i);
                /*addDataToGame();
                initPreludeArray();
                preludeAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}