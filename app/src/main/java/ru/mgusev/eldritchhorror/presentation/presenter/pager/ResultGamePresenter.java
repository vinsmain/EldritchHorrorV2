package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class ResultGamePresenter extends MvpPresenter<ResultGameView> {
    @Inject
    Repository repository;
    private CompositeDisposable subscribe;

    public ResultGamePresenter() {
        App.getComponent().inject(this);
        subscribe = new CompositeDisposable();
        subscribe.add(repository.getObservableAncientOne().subscribe(ancientOne ->  getViewState().showMysteriesRadioGroup(ancientOne)));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setResultSwitchChecked(repository.getGame().isWinGame());
        repository.scoreOnNext(); //устанавливаем счет при первом запуске
    }

    public void setResultSwitchText(boolean value) {
        repository.getGame().setWinGame(value);
        getViewState().setResultSwitchText(value);
        getViewState().showResultTable(value);
    }

    @Override
    public void onDestroy() {
        subscribe.dispose();
        super.onDestroy();
    }

    public void setResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount) {
        repository.setResult(gatesCount, monstersCount, curseCount, rumorsCount, cluesCount, blessedCount, doomCount);
    }
}