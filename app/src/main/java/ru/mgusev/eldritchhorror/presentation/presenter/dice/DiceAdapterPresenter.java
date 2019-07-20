package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceAdapterView;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class DiceAdapterPresenter extends MvpPresenter<DiceAdapterView> {

    @Inject
    Repository repository;

    private List<Dice> diceList;
    private CompositeDisposable updateDiceListSubscribe;
    private CompositeDisposable changeAnimationModeSubscribe;
    private CompositeDisposable changeSuccessModeSubscribe;


    public DiceAdapterPresenter() {
        App.getComponent().inject(this);
        initSubscribes();
    }

    private void initSubscribes() {
        updateDiceListSubscribe = new CompositeDisposable();
        updateDiceListSubscribe.add(repository.getUpdateDiceListPublish().subscribe(this::changeDiceList, Timber::d));

        changeAnimationModeSubscribe = new CompositeDisposable();
        changeAnimationModeSubscribe.add(repository.getChangeAnimationModePublish().subscribe(this::changeAnimationMode, Timber::d));

        changeSuccessModeSubscribe = new CompositeDisposable();
        changeSuccessModeSubscribe.add(repository.getChangeSuccessModePublish().subscribe(this::changeSuccessMode, Timber::d));
    }

    private void changeDiceList(List<Dice> list) {
        diceList = new ArrayList<>(list);
        for (Dice dice : diceList) {
            dice.immediateStopAnimation();
        }
        getViewState().setData(diceList);
        repository.changeSuccessModeOnNext(repository.getSuccessMode());

    }

    private void changeAnimationMode(boolean is3D) {
        Timber.d(String.valueOf(is3D));
        for (Dice dice : diceList) {
            dice.changeAnimationMode(is3D);
        }
    }

    private void changeSuccessMode(int mode) {
        Timber.d(String.valueOf(mode));
        for (Dice dice : diceList) {
            dice.changeSuccessMode(mode);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateDiceListSubscribe.dispose();
        changeAnimationModeSubscribe.dispose();
        changeSuccessModeSubscribe.dispose();
    }
}