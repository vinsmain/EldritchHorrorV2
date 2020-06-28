package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import android.net.Uri;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;


@InjectViewState
public class RandomCardPresenter extends MvpPresenter<RandomCardView> {

    @Inject
    Repository repository;

    private List<Card> cardList;
    private Card currentCard;
    private PublishSubject<Boolean> showAlertPublish;
    private CompositeDisposable showAlertSubscribe;

    public RandomCardPresenter() {
        App.getComponent().inject(this);
        showAlertPublish = PublishSubject.create();
        showAlertSubscribe = new CompositeDisposable();
        showAlertSubscribe.add(showAlertPublish.throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe( v -> getViewState().showAlertIfOtherCardNone(v), Timber::e));
        initCardList();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initCard();
    }

    private void initCardList() {
        CardType cardType = repository.getCardType();
        if (cardType.getId() < 0)
            cardList = repository.getCardList(cardType.getCategoryResourceID());
        else
            cardList = repository.getCardList(cardType.getId());
    }

    private void initCard() {
        selectRandomCard();
        getViewState().setCategory(repository.getCardType().getCategoryResourceID());
        getViewState().setType(repository.getCardType(currentCard.getTypeID()).getNameResourceID());
        getViewState().setTitle(currentCard.getNameResourceID());
        String cardUrl = BuildConfig.FileBaseUrl + "/eh_images/" + Localization.getInstance().getPrefix() + "/" + repository.getCardType().getCategoryResourceID() + "/" + repository.getCardType(currentCard.getTypeID()).getNameResourceID() + "/" + currentCard.getNameResourceID() + ".png";
        getViewState().loadImage(Uri.parse(cardUrl));
    }

    private void selectRandomCard() {
        int index = (int) (Math.random()* cardList.size());
        currentCard = cardList.get(index);
    }

    private void deleteCurrentCardFromCardList() {
        cardList.remove(currentCard);
    }

    public void onClickLogBtn() {
        getViewState().showLogDialog(cardList);
    }

    public void onClickOtherBtn() {
        if (cardList.size() > 1) {
            deleteCurrentCardFromCardList();
            initCard();
        } else
            showAlertPublish.onNext(true);
    }

    public void dismissLogDialog() {
        getViewState().hideLogDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showAlertSubscribe.dispose();
    }
}