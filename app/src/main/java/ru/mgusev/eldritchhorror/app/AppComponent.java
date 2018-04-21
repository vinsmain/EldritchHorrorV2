package ru.mgusev.eldritchhorror.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.PagerPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ResultGamePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;

@Component (modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(PagerPresenter presenter);
    void inject(StartDataPresenter presenter);
    void inject(InvestigatorChoicePresenter presenter);
    void inject(ResultGamePresenter presenter);
}