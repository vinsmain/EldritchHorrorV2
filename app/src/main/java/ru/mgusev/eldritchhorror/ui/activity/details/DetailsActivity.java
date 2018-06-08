package ru.mgusev.eldritchhorror.ui.activity.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.details.DetailsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;

public class DetailsActivity extends MvpAppCompatActivity implements DetailsView {

    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @BindView(R.id.victory_result_card) View includedVictoryResultCard;
    @BindView(R.id.defeat_reason_card) View includedDefeatReasonCard;
    @BindView(R.id.gameResultDetailHeader) TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
    }
}
