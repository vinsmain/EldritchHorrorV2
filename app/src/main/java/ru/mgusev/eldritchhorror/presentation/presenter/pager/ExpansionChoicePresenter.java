package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.view.pager.ExpansionChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class ExpansionChoicePresenter extends MvpPresenter<ExpansionChoiceView> {

    @Inject
    Repository repository;
    private List<Expansion> tempList;

    public ExpansionChoicePresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tempList = repository.getExpansionList();
        tempList.remove(0); //Удаляем из списка Core expansion
        getViewState().initExpansionList(tempList);
    }

    public void setTempList(List<Expansion> list) {
        this.tempList = list;
    }

    @Override
    public void onDestroy() {
        repository.saveExpansionList(tempList);
        repository.expansionOnNext();
        super.onDestroy();
    }
}
