package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class RandomCardCategoryPresenter extends MvpPresenter<RandomCardCategoryView> {

    @Inject
    Repository repository;

    public RandomCardCategoryPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCategoryList(repository.getConditionTypeList());
    }
}
