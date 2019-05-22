package ru.mgusev.eldritchhorror.model;

import android.os.Handler;

import com.iigo.library.DiceLoadingView;

import java.util.Date;

import ru.mgusev.eldritchhorror.adapter.DiceItemPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DicePresenter;
import timber.log.Timber;

public class Dice {

    private long id;
    private int firstValue;
    private int secondValue;
    private int thirdValue;
    private int fourthValue;
    private int lineMode;
    private int rotateMode;

    private DiceItemPresenter presenter;
    private boolean animationRun;
    private boolean animationFinish = true;

    public Dice() {
        id = new Date().getTime();
        roll();
        Timber.d(lineMode + " " + rotateMode);
    }

    public Dice(Dice dice) {
        this.firstValue = dice.getFirstValue();
        this.secondValue = dice.getSecondValue();
        this.thirdValue = dice.getThirdValue();
        this.fourthValue = dice.getFourthValue();
        this.animationRun = dice.isAnimationRun();
        this.animationFinish = dice.isAnimationFinish();
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

    public int getLineMode() {
        return lineMode;
    }

    public int getRotateMode() {
        return rotateMode;
    }

    public void setDiceDLV(DiceLoadingView diceDLV) {
//        if (this.diceDLV == null) {
//            this.diceDLV = diceDLV;
//        }
//        //startAnimation();
//
//        if (!animationRun && !animationFinish) {
//            this.diceDLV.setDuration(3000);
//            this.diceDLV.start();
//            stopAnimation();
//            animationRun = true;
//            animationFinish = false;
//        } else if(!animationRun || animationFinish) {
//            this.diceDLV.setDuration(0);
//            this.diceDLV.stop();
//        }
    }


    private void startAnimation() {
        animationRun = true;
        animationFinish = false;
        //presenter.startAnimation();
        stopAnimation();

//        if (!animationRun && !animationFinish) {
//            if (diceDLV != null) {
//
//                new Handler().postDelayed(() -> {
//                    if (diceDLV != null) {
//                        diceDLV.setFirstSideDiceNumber(getFirstValue());
//                        diceDLV.setSecondSideDiceNumber(getSecondValue());
//                        diceDLV.setThirdSideDiceNumber(getThirdValue());
//                        diceDLV.setFourthSideDiceNumber(getFourthValue());
//                    }
//                    animationRun = false;
//                    animationFinish = true;
//                }, 700);
//
//
//                this.diceDLV.setDuration(3000);
//                this.diceDLV.start();
//            }
//            stopAnimation();
//            animationRun = true;
//            animationFinish = false;
//        } else if(diceDLV != null && (!animationRun || animationFinish)) {
//            this.diceDLV.setDuration(0);
//            this.diceDLV.stop();
//        }
    }

    private void stopAnimation() {
        new Handler().postDelayed(() -> {
            animationRun = false;
            animationFinish = true;
            //presenter.stopAnimation();
        }, 3000);
    }

    public void setAnimationRun(boolean animationRun) {
        this.animationRun = animationRun;
    }

    public void setAnimationFinish(boolean animationFinish) {
        this.animationFinish = animationFinish;
    }

    public boolean isAnimationRun() {
        return animationRun;
    }

    public boolean isAnimationFinish() {
        return animationFinish;
    }

    public void roll() {
        this.firstValue = 1 + (int) (Math.random() * 6);
        initModes();
        initOtherValues();
        startAnimation();
    }

    public DiceItemPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(DiceItemPresenter presenter) {
        this.presenter = presenter;
    }
}