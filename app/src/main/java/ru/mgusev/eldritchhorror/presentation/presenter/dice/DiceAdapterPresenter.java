package ru.mgusev.eldritchhorror.presentation.presenter.dice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
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


    public DiceAdapterPresenter() {
        App.getComponent().inject(this);
        initSubscribe();
    }

    @Override
    public void attachView(DiceAdapterView view) {
        super.attachView(view);
        initSubscribe();
    }

    private void initSubscribe() {
        updateDiceListSubscribe = new CompositeDisposable();
        updateDiceListSubscribe.add(repository.getUpdateDiceListPublish().subscribe(this::changeDiceList, Timber::d));

        changeAnimationModeSubscribe = new CompositeDisposable();
        changeAnimationModeSubscribe.add(repository.getChangeAnimationModePublish().subscribe(this::changeAnimationMode, Timber::d));
    }

    private void changeDiceList(List<Dice> list) {
        diceList = new ArrayList<>(list);
        getViewState().setData(diceList);
    }

    private void changeAnimationMode(boolean is3D) {
        Timber.d(String.valueOf(is3D));
        for (Dice dice : diceList) {
            dice.changeAnimationMode(is3D);
        }
    }

    @Override
    public void destroyView(DiceAdapterView view) {
        super.destroyView(view);
        updateDiceListSubscribe.dispose();
        changeAnimationModeSubscribe.dispose();
    }
}