package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class PagerPresenter extends MvpPresenter<PagerView> {

    @Inject
    Repository repository;
    private CompositeDisposable subscribe;

    public PagerPresenter() {
        App.getComponent().inject(this);
        subscribe = new CompositeDisposable();
        subscribe.add(repository.getObservableAncientOne().subscribe(ancientOne -> getViewState().setHeadBackground(ancientOne)));
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public void onDestroy() {
        subscribe.dispose();
        super.onDestroy();
    }
}