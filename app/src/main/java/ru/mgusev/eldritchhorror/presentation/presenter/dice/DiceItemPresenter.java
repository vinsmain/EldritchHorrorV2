package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.presentation.view.dice.DiceItemView;
import ru.mgusev.eldritchhorror.model.Dice;
import timber.log.Timber;

@InjectViewState
public class DiceItemPresenter extends MvpPresenter<DiceItemView> {

    private Dice dice;

    public DiceItemPresenter(Dice dice) {
        super();
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
}