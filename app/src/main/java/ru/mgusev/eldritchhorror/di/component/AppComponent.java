package ru.mgusev.eldritchhorror.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.mgusev.eldritchhorror.di.module.AppModule;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DiceAdapterPresenter;
import ru.mgusev.eldritchhorror.ui.adapter.details.DetailsAdapter;
import ru.mgusev.eldritchhorror.ui.adapter.faq.FaqAdapter;
import ru.mgusev.eldritchhorror.ui.adapter.pager.InvestigatorChoiceAdapter;
import ru.mgusev.eldritchhorror.ui.adapter.main.MainAdapter;
import ru.mgusev.eldritchhorror.ui.adapter.random_card.RandomCardCategoryAdapter;
import ru.mgusev.eldritchhorror.ui.adapter.statistics.StatisticsAdapter;
import ru.mgusev.eldritchhorror.utils.auth.GoogleAuth;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DicePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings.ForgottenEndingsPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardCategoryPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardPresenter;
import ru.mgusev.eldritchhorror.repository.FirebaseHelper;
import ru.mgusev.eldritchhorror.database.oldDB.DatabaseHelperOld;
import ru.mgusev.eldritchhorror.presentation.presenter.details.DetailsPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.main.MainPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ExpansionChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.GamePhotoPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.PagerPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ResultGamePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.SpecializationChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.statistics.StatisticsPresenter;

@Component (modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(MainPresenter presenter);
    void inject(DetailsPresenter presenter);
    void inject(StatisticsPresenter presenter);
    void inject(PagerPresenter presenter);
    void inject(StartDataPresenter presenter);
    void inject(InvestigatorChoicePresenter presenter);
    void inject(ResultGamePresenter presenter);
    void inject(InvestigatorPresenter presenter);
    void inject(ExpansionChoicePresenter presenter);
    void inject(SpecializationChoicePresenter presenter);
    void inject(GamePhotoPresenter presenter);
    void inject(ForgottenEndingsPresenter forgottenEndingsPresenter);
    void inject(FaqPresenter presenter);
    void inject(RandomCardCategoryPresenter presenter);
    void inject(RandomCardPresenter presenter);
    void inject(DicePresenter presenter);
    void inject(DiceAdapterPresenter presenter);

    void inject(MainAdapter adapter);
    void inject(InvestigatorChoiceAdapter adapter);
    void inject(DetailsAdapter adapter);
    void inject(StatisticsAdapter adapter);
    void inject(FaqAdapter adapter);
    void inject(RandomCardCategoryAdapter adapter);

    void inject(FirebaseHelper helper);
    void inject(GoogleAuth googleAuth);

    void inject(DatabaseHelperOld databaseHelperOld);
}