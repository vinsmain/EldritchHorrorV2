package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Specialization;

public interface SpecializationChoiceView extends MvpView {

    void initSpecializationList(List<Specialization> list);
}
