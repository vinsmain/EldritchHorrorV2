package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Specialization;
import ru.mgusev.eldritchhorror.presentation.view.pager.SpecializationChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class SpecializationChoicePresenter extends MvpPresenter<SpecializationChoiceView> {

    @Inject
    Repository repository;
    private List<Specialization> tempList;

    public SpecializationChoicePresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tempList = repository.getSpecializationList();
        getViewState().initSpecializationList(tempList);
    }

    public void setTempList(List<Specialization> list) {
        this.tempList = list;
    }

    @Override
    public void onDestroy() {
        repository.saveSpecializationList(tempList);
        super.onDestroy();
    }
}