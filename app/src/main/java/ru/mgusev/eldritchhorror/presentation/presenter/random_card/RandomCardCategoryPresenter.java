package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.CardType;
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
        getViewState().setScreenLightFlags(repository.getScreenLight());
    }

    private void updateCategoryList(List<Expansion> list) {
        getViewState().setCategoryList(getCardTypeList());
    }

    private List<CardType> getCardTypeList() {
        int typeCategoryID = -1;
        String typeCategoryName = "";
        List<CardType> modifiedCardTypeList = new ArrayList<>();
        for (CardType cardType : repository.getCardTypeList()) {
            if (!cardType.getCategoryResourceID().equals(typeCategoryName)) {
                modifiedCardTypeList.add(new CardType(typeCategoryID--, cardType.getCategoryResourceID(), cardType.getCategoryResourceID()));
                typeCategoryName = cardType.getCategoryResourceID();
            }
            modifiedCardTypeList.add(cardType);
        }
        return modifiedCardTypeList;
    }

    public void onCategoryClick(CardType item) {
        repository.setCardType(item);
        getViewState().startRandomCardActivity();
    }

    public boolean getScreenLight() {
        return repository.getScreenLight();
    }

    public void onScreenLightClick(boolean isOn) {
        repository.setScreenLight(isOn);
        getViewState().setVisibilityScreenLightButtons();
        getViewState().setScreenLightFlags(isOn);
    }

    @Override
    public void onDestroy() {
        expansionSubscribe.dispose();
        super.onDestroy();
    }
}