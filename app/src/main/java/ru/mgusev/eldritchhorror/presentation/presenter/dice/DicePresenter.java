package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class DicePresenter extends MvpPresenter<DiceView> {

    @Inject
    Repository repository;

    private List<Dice> diceList;
    private PublishSubject<Boolean> updateDiceListPublish;
    private CompositeDisposable updateDiceListSubscribe;

    public DicePresenter() {
        App.getComponent().inject(this);
        diceList = new ArrayList<>();
        updateDiceListPublish = PublishSubject.create();
        updateDiceListSubscribe = new CompositeDisposable();
        updateDiceListSubscribe.add(updateDiceListPublish.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(this::updateDiceList, Timber::e));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onDiceCountSeekBarChangeProgress(int progress) {
        getViewState().setDiceCountValue(String.valueOf(progress));
    }

    public void onDiceCountSeekBarStopChangeProgress(int progress) {
        initDiceList(progress);
        getViewState().updateDiceList(diceList);
    }

    private void initDiceList(int diceCount) {
        while (diceList.size() != diceCount) {
            if (diceList.size() < diceCount) {
                diceList.add(new Dice());
            } else {
                diceList.remove(diceList.size() - 1);
            }
        }
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
        Timber.d("reroll");
        List<Dice> temp = new ArrayList<>();
        for (Dice dice : diceList) {
            dice.roll();
            //temp.add(new Dice());
        }
        //diceList = temp;
        getViewState().updateDiceList(diceList);
    }

    public void onClickRerollOneDice(Dice clickedDice) {
        for (Dice dice : diceList) {
            if (clickedDice.getId() == dice.getId()) {
                dice.roll();
            }
        }
        //getViewState().updateDiceList(diceList);
    }

    public void onNextUpdateDiceListPublish() {
        updateDiceListPublish.onNext(true);
    }

    public void updateDiceList(boolean value) {
        List<Dice> temp = new ArrayList<>();
        for (Dice dice : diceList) {
            //dice.roll();
            temp.add(new Dice(dice));
        }
        getViewState().updateDiceList(temp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateDiceListSubscribe.dispose();
    }
}
