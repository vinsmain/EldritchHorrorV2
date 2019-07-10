package ru.mgusev.eldritchhorror.presentation.view.pager;

import java.util.List;

import moxy.MvpView;
import ru.mgusev.eldritchhorror.model.Specialization;

public interface SpecializationChoiceView extends MvpView {

    void initSpecializationList(List<Specialization> list);
}
