package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class DicePresenter extends MvpPresenter<DiceView> {

    @Inject
    Repository repository;

    private List<Dice> diceList;

    public DicePresenter() {
        App.getComponent().inject(this);
        diceList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onDiceCountSeekBarStopChangeProgress(repository.getDiceCount());
        getViewState().setScreenLightFlags(repository.getScreenLight());
        getViewState().setInitialValueForSeekBar(repository.getDiceCount());
        repository.changeAnimationModeOnNext(repository.getAnimationMode());
    }

    public void onDiceCountSeekBarChangeProgress(int progress) {
        getViewState().setDiceCountValue(String.valueOf(progress));
    }

    public void onDiceCountSeekBarStopChangeProgress(int progress) {
        repository.setDiceCount(progress);
        initDiceList(progress);
        repository.changeAnimationModeOnNext(repository.getAnimationMode());
    }

    private void initDiceList(int diceCount) {
        while (diceList.size() != diceCount) {
            if (diceList.size() < diceCount) {
                diceList.add(new Dice());
            } else {
                diceList.remove(diceList.size() - 1);
            }
        }
        repository.updateDiceListOnNext(diceList);
    }

    public boolean getScreenLight() {
        return repository.getScreenLight();
    }

    public void onScreenLightClick(boolean isOn) {
        repository.setScreenLight(isOn);
        getViewState().setVisibilityScreenLightButtons();
        getViewState().setScreenLightFlags(isOn);
    }

    public void onClickRerollAllBtn() {
        for (Dice dice : diceList) {
            dice.roll();
        }
    }

    public boolean getAnimationMode() {
        return repository.getAnimationMode();
    }

    public void onAnimationModeClick(boolean is3D) {
        repository.setAnimationMode(is3D);
        getViewState().setVisibilityAnimationModeButtons();
        repository.changeAnimationModeOnNext(is3D);
    }
}