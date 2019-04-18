package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class RandomCardPresenter extends MvpPresenter<RandomCardView> {

    private final String IMAGE_URL = "https://mgusevstudio.ru/images/files/eh_images/";

    @Inject
    Repository repository;

    private List<Card> cardList;
    private Card currentCard;

    public RandomCardPresenter() {
        App.getComponent().inject(this);
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
        getRandomCondition();
        getViewState().setCategory(repository.getCardType().getCategoryResourceID());
        getViewState().setType(repository.getCardType(currentCard.getTypeID()).getNameResourceID());
        getViewState().setTitle(currentCard.getNameResourceID());
        String cardUrl = IMAGE_URL + Localization.getInstance().getPrefix() + "/" + repository.getCardType().getCategoryResourceID() + "/" + repository.getCardType(currentCard.getTypeID()).getNameResourceID() + "/" + currentCard.getNameResourceID() + ".png";
        getViewState().loadImage(Uri.parse(cardUrl));
    }

    private Card getRandomCondition() {
        int index = (int) (Math.random()* cardList.size());
        currentCard = cardList.get(index);
        return currentCard;
    }

    public void onClickLogBtn() {
        getViewState().showLogDialog(cardList);
    }
}
