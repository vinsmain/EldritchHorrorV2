package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorChoicePresenter extends MvpPresenter<InvestigatorChoiceView> {

    @Inject
    Repository repository;
    private List<Investigator> investigatorList;
    private CompositeDisposable investigatorSubscribe;

    public InvestigatorChoicePresenter() {
        App.getComponent().inject(this);
        investigatorList = repository.getInvestigatorList();
        setInvestigatorListFromRepository();
        investigatorSubscribe = new CompositeDisposable();
        investigatorSubscribe.add(repository.getInvestigatorPublish().subscribe(this::refreshInvestigatorList));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    private void refreshInvestigatorList(Investigator currentInvestigator) {
        int position = 0;
        for (int i = 0; i < investigatorList.size(); i++) {
            if (investigatorList.get(i).getId() == currentInvestigator.getId()) {
                position = i;
                break;
            }
        }
        investigatorList.set(position, currentInvestigator);
        setInvestigatorListFromRepository();
    }

    private void setInvestigatorListFromRepository() {
        getViewState().showItems(investigatorList, repository.getExpansionList());

    }

    public void itemClick(int position) {
        repository.setInvestigator(investigatorList.get(position));
        getViewState().showInvestigatorActivity();
    }

    @Override
    public void onDestroy() {
        investigatorSubscribe.dispose();
        super.onDestroy();
    }
}