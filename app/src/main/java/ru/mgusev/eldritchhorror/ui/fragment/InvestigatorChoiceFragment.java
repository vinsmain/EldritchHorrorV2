package ru.mgusev.eldritchhorror.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class InvestigatorChoiceFragment extends MvpAppCompatFragment implements InvestigatorChoiceView {

    @InjectPresenter
    InvestigatorChoicePresenter investigatorChoicePresenter;

    public static InvestigatorChoiceFragment newInstance(int page) {
        InvestigatorChoiceFragment pageFragment = new InvestigatorChoiceFragment();
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
        return inflater.inflate(R.layout.fragment_investigator_choice, null);
    }
}
