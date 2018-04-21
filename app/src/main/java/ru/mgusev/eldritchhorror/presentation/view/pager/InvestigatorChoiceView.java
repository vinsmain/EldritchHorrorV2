package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;

public interface InvestigatorChoiceView extends MvpView {

    void showItems(List<Investigator> list);
}
