package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class RandomCardCategoryPresenter extends MvpPresenter<RandomCardCategoryView> {

    @Inject
    Repository repository;

    private CompositeDisposable expansionSubscribe;

    public RandomCardCategoryPresenter() {
        App.getComponent().inject(this);
        expansionSubscribe = new CompositeDisposable();
        expansionSubscribe.add(repository.getExpansionPublish().subscribe(this::updateCategoryList, Timber::d));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        repository.expansionOnNext();
    }

    private void updateCategoryList(List<Expansion> list) {
        getViewState().setCategoryList(repository.getConditionTypeList());
    }

    public void onCategoryClick(int typeID) {
        Timber.d(String.valueOf(typeID));
        repository.setConditionType(repository.getConditionType(typeID));
        getViewState().startRandomCardActivity();
    }

    @Override
    public void onDestroy() {
        expansionSubscribe.dispose();
        super.onDestroy();
    }
}