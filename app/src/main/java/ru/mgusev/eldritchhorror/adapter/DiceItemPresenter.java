package ru.mgusev.eldritchhorror.adapter;

import android.os.Handler;

import ru.mgusev.eldritchhorror.model.Dice;
import timber.log.Timber;

public class DiceItemPresenter extends BasePresenter<Dice, DiceItemView> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;
    private boolean animationRun;
    private boolean animationFinish;

    @Override
    protected void updateView() {
        view().updateDice(model);
        startAnimation();
//        view().setCounterName(model.getName());
//        int value = model.getValue();
//        view().setCounterValue(value);
//        view().setMinusButtonEnabled(value > MIN_VALUE);
//        view().setPlusButtonEnabled(value < MAX_VALUE);
    }

    public void startAnimation() {
        Timber.d(String.valueOf(animationFinish));
        if (setupDone()) {
            if (!animationRun && !animationFinish) {
                view().startAnimation();
                animationRun = true;
                new Handler().postDelayed(() -> {
                    stopAnimation();
                    //presenter.stopAnimation();
                }, 3000);
            } else if (animationRun && !animationFinish) {
                view().startAnimation();
            } else {
                stopAnimation();
            }
        }
    }

    public void stopAnimation() {
        if (setupDone()) {
            view().stopAnimation();
            animationRun = false;
            animationFinish = true;
        }
    }

    public void onDiceClicked() {
        if (setupDone()) {
            model.roll();
            animationRun = false;
            animationFinish = false;
            updateView();
        }
    }

//    public void onMinusButtonClicked() {
//        if (setupDone() && model.getValue() > MIN_VALUE) {
//            model.setValue(model.getValue() - 1);
//            CounterDatabase.getInstance().saveCounter(model);
//            updateView();
//        }
//    }
//
//    public void onPlusButtonClicked() {
//        if (setupDone() && model.getValue() < MAX_VALUE) {
//            model.setValue(model.getValue() + 1);
//            CounterDatabase.getInstance().saveCounter(model);
//            updateView();
//        }
//    }
//
//    public void onCounterClicked() {
//        if (setupDone()) {
//            view().goToDetailView(model);
//        }
//    }
}