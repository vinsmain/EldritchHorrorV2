package ru.mgusev.eldritchhorror.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Random;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class StartingDataFragment extends MvpAppCompatFragment implements StartDataView {

    @InjectPresenter
    StartDataPresenter startDataPresenter;

    int pageNumber;
    int backColor;

    public static StartingDataFragment newInstance(int page) {
        StartingDataFragment pageFragment = new StartingDataFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starting_data, container, false);
        System.out.println(view);

        /*TextView tvPage = (TextView) view.findViewById(R.id.dateField);
        tvPage.setText("Page " + pageNumber);
        tvPage.setBackgroundColor(backColor);*/

        return view;
    }
}
