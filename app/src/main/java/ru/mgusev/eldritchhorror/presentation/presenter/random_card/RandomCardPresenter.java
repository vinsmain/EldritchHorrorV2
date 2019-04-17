package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Card;
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
        Timber.d(String.valueOf(repository.getCardType().getId()));
        cardList = repository.getCardList(repository.getCardType().getId());
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initCard();
    }

    private void initCard() {
        getRandomCondition();
        getViewState().setCategory(repository.getCardType().getCategoryResourceID());
        getViewState().setType(repository.getCardType().getNameResourceID());
        getViewState().setTitle(currentCard.getNameResourceID());
        String cardUrl = IMAGE_URL + Localization.getInstance().getPrefix() + "/" + repository.getCardType().getCategoryResourceID() + "/" + repository.getCardType().getNameResourceID() + "/" + currentCard.getNameResourceID() + ".png";
        getViewState().loadImage(Uri.parse(cardUrl));
    }

    private Card getRandomCondition() {
        int index = (int) (Math.random()* cardList.size());
        currentCard = cardList.get(index);
        return currentCard;
    }
}
