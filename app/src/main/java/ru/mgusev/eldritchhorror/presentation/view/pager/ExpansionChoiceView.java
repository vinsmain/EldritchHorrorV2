package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Expansion;

public interface ExpansionChoiceView extends MvpView {

    void initExpansionList(List<Expansion> list);
}
