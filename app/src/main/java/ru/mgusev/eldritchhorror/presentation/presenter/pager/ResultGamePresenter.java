package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class ResultGamePresenter extends MvpPresenter<ResultGameView> {

    public ResultGamePresenter() {
        Repository.getInstance().getObservableAncientOne().subscribe(ancientOne ->  getViewState().showMysteriesRadioGroup(ancientOne));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setResultSwitchChecked(Repository.getInstance().getGame().isWinGame());
    }

    public void setResultSwitchText(boolean value) {
        Repository.getInstance().getGame().setWinGame(value);
        getViewState().setResultSwitchText(value);
        getViewState().showResultTable(value);
    }
}
