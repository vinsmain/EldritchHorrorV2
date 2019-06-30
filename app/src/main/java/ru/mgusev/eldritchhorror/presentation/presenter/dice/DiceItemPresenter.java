package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceItemView;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class DiceItemPresenter extends MvpPresenter<DiceItemView> {

    @Inject
    Repository repository;

    private Dice dice;

    public DiceItemPresenter(Dice dice) {
        super();
        App.getComponent().inject(this);
        this.dice = dice;
        this.dice.setPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d("PRESENTER");
        updateDice();
    }

    public void updateDice() {
        getViewState().updateDice();
    }

    public void updateAnimation(boolean isAnimation) {
        if (isAnimation)
            getViewState().startAnimation();
        else
            getViewState().stopAnimation();
    }

    public void onClickDice() {
        Timber.d(String.valueOf(dice.getId()));
        dice.roll();
    }

    public void updateSuccessCount() {
        repository.changeSuccessCountOnNext(0);
    }
}