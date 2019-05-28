package ru.mgusev.eldritchhorror.model;

import android.os.Handler;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import ru.mgusev.eldritchhorror.presentation.presenter.dice.DiceItemPresenter;
import timber.log.Timber;

public class Dice {

    private static AtomicLong currentId = new AtomicLong(new Date().getTime());

    private long id;
    private int firstValue;
    private int secondValue;
    private int thirdValue;
    private int fourthValue;
    private int lineMode;
    private int rotateMode;
    private int updateValuesTimeOut;
    private int stopAnimationTimeOut;
    private boolean animationMode;
    private int successMode;
    private DiceItemPresenter presenter;
    private boolean animationRun;

    public Dice() {
        id = currentId.incrementAndGet();
        initValues();
        Timber.d(lineMode + " " + rotateMode + " " + firstValue);
    }

    private void initModes() {
        lineMode = (int) (Math.random() * 2);
        rotateMode = (int) (Math.random() * 2);
    }

    private void initOtherValues() {
        switch (firstValue) {
            case 2 :
                initTwo();
                break;
            case 3 :
                initThree();
                break;
            case 4 :
                initFour();
                break;
            case 5 :
                initFive();
                break;
            case 6 :
                initSix();
                break;
            default:
                initOne();
                break;
        }
    }

    private void initOne() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(2, 6, 5);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(5, 6, 2);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(3, 6, 4);
        else
            setOtherValues(4, 6, 3);
    }

    private void initTwo() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(6, 5, 1);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(1, 5, 6);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(3, 5, 4);
        else
            setOtherValues(4, 5, 3);
    }

    private void initThree() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(1, 4, 6);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(6, 4, 1);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(2, 4, 5);
        else
            setOtherValues(5, 4, 2);
    }

    private void initFour() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(1, 3, 6);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(6, 3, 1);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(5, 3, 2);
        else
            setOtherValues(2, 3, 5);
    }

    private void initFive() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(1, 2, 6);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(6, 2, 1);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(3, 2, 4);
        else
            setOtherValues(4, 2, 3);
    }

    private void initSix() {
        if (lineMode == 0 && rotateMode == 0)
            setOtherValues(5, 1, 2);
        else if (lineMode == 0 && rotateMode == 1)
            setOtherValues(2, 1, 5);
        else if (lineMode == 1 && rotateMode == 0)
            setOtherValues(3, 1, 4);
        else
            setOtherValues(4, 1, 3);
    }

    private void setOtherValues(int secondValue, int thirdValue, int fourthValue) {
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.fourthValue = fourthValue;
    }

    public long getId() {
        return id;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public int getThirdValue() {
        return thirdValue;
    }

    public int getFourthValue() {
        return fourthValue;
    }

    public int getStopAnimationTimeOut() {
        return stopAnimationTimeOut;
    }

    public boolean isAnimationMode() {
        return animationMode;
    }

    public int getSuccessMode() {
        return successMode;
    }

    private void startAnimation() {
        if (presenter != null)
            presenter.updateAnimation(animationRun);
    }

    private void stopAnimation() {
        new Handler().postDelayed(() -> {
            animationRun = false;
            if (presenter != null)
                presenter.updateAnimation(animationRun);
        }, stopAnimationTimeOut);
    }

    private void initValues() {
        this.firstValue = 1 + (int) (Math.random() * 6);
        Timber.d("ROLL DICE %s", id);
        initModes();
        initOtherValues();
    }

    public void roll() {
        if (!animationRun) {
            initValues();
            animationRun = true;
            startAnimation();

            new Handler().postDelayed(() -> {
                if (presenter != null)
                    presenter.updateDice();
            }, updateValuesTimeOut);

            stopAnimation();
        }
        Timber.d(String.valueOf(successMode));
    }

    public void setPresenter(DiceItemPresenter presenter) {
        this.presenter = presenter;
        updateDice();
    }

    private void updateDice() {
        if (presenter != null) {
            presenter.updateDice();
            presenter.updateAnimation(animationRun);
        }
    }

    public void changeAnimationMode(boolean is3D) {
        animationMode = is3D;
        if (is3D) {
            updateValuesTimeOut = 700;
            stopAnimationTimeOut = 3000;
        } else  {
            updateValuesTimeOut = 250;
            stopAnimationTimeOut = 500;
        }
    }

    public void changeSuccessMode(int mode) {
        successMode = mode;
        if (presenter != null)
            presenter.updateDice();
    }
}