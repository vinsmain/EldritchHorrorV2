package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Condition;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class RandomCardPresenter extends MvpPresenter<RandomCardView> {

    private final String IMAGE_URL = "https://mgusevstudio.ru/images/files/eh_images/";

    @Inject
    Repository repository;

    private List<Condition> conditionList;
    private Condition currentCondition;

    public RandomCardPresenter() {
        App.getComponent().inject(this);
        Timber.d(String.valueOf(repository.getConditionType().getId()));
        conditionList = repository.getConditionList(repository.getConditionType().getId());
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initCard();
    }

    private void initCard() {
        getRandomCondition();
        getViewState().setCategory("Состояние");
        getViewState().setType(repository.getConditionType().getNameResourceID());
        getViewState().setTitle(currentCondition.getNameResourceID());
        String cardUrl = IMAGE_URL + Localization.getInstance().getPrefix() + "/condition/" + repository.getConditionType().getNameResourceID() + "/" + currentCondition.getNameResourceID() + ".png";
        getViewState().loadImage(Uri.parse(cardUrl));
    }

    private Condition getRandomCondition() {
        int index = (int) (Math.random()*conditionList.size());
        currentCondition = conditionList.get(index);
        return currentCondition;
    }
}
