package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class DicePresenter extends MvpPresenter<DiceView> {

    @Inject
    Repository repository;

    private List<Dice> diceList;
    private CompositeDisposable changeSuccessCountSubscribe;

    public DicePresenter() {
        App.getComponent().inject(this);
        diceList = new ArrayList<>();
        changeSuccessCountSubscribe = new CompositeDisposable();
        changeSuccessCountSubscribe.add(repository.getChangeSuccessCountPublish().subscribe(this::calculateSuccessCount, Timber::d));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onDiceCountSeekBarStopChangeProgress(repository.getDiceCount());
        getViewState().setScreenLightFlags(repository.getScreenLight());
        getViewState().setInitialValueForSeekBar(repository.getDiceCount());
        repository.changeAnimationModeOnNext(repository.getAnimationMode());
        repository.changeSuccessModeOnNext(repository.getSuccessMode());
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

    public void onSuccessModeClick(int mode) {
        repository.setSuccessMode(mode);
        getViewState().setVisibilitySuccessModeButtons();
        repository.changeSuccessModeOnNext(mode);
    }

    public int getSuccessMode() {
        return repository.getSuccessMode();
    }

    private void calculateSuccessCount(int value) {
        int count = 0;
        for (Dice dice : diceList) {
            if (dice.getFirstValue() >= dice.getSuccessMode()) {
                count++;
            }
        }
        getViewState().setSuccessCount(String.valueOf(count));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        changeSuccessCountSubscribe.dispose();
    }
}