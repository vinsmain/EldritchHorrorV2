package ru.mgusev.eldritchhorror.ui.activity.details;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.presenter.details.DetailsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;

public class DetailsActivity extends MvpAppCompatActivity implements DetailsView {

    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.details_header_background) ImageView background;
    @BindView(R.id.details_expansion_icon) ImageView expansionIcon;
    @BindView(R.id.details_victory_icon) ImageView victoryIcon;
    @BindView(R.id.details_score) TextView score;

    @BindView(R.id.start_data_result_date) TextView date;
    @BindView(R.id.start_data_result_ancient_one) TextView ancientOne;
    @BindView(R.id.start_data_result_prelude) TextView prelude;
    @BindView(R.id.start_data_result_players_count) TextView playersCount;
    @BindView(R.id.start_data_result_simple_myths_row) TableRow simpleMyths;
    @BindView(R.id.start_data_result_normal_myths_row) TableRow normalMyths;
    @BindView(R.id.start_data_result_hard_myths_row) TableRow hardMyths;
    @BindView(R.id.start_data_result_starting_rumor_row) TableRow startingRumor;

    @BindView(R.id.investigator_result_not_selected) TextView inestigatorsNotSelected;
    @BindView(R.id.investigator_result_recycle_view) RecyclerView investigatorsRV;

    @BindView(R.id.victory_result_card) View includedVictoryResultCard;
    @BindView(R.id.victory_result_mysteries_count) TextView victoryMysteriesCount;
    @BindView(R.id.victory_result_gates_count) TextView gatesCount;
    @BindView(R.id.victory_result_monsters_count) TextView monstersCount;
    @BindView(R.id.victory_result_curse_count) TextView curseCount;
    @BindView(R.id.victory_result_rumors_count) TextView rumorsCount;
    @BindView(R.id.victory_result_clues_count) TextView cluesCount;
    @BindView(R.id.victory_result_blessed_count) TextView blessedCount;
    @BindView(R.id.victory_result_doom_count) TextView doomCount;

    @BindView(R.id.defeat_reason_card) View includedDefeatReasonCard;
    @BindView(R.id.defeat_reason_mysteries_count) TextView defeatMysteriesCount;
    @BindView(R.id.defeat_reason_by_elimination_row) TableRow defeatReasonByElimination;
    @BindView(R.id.defeat_reason_by_mythos_depletion_row) TableRow defeatReasonByMythosDepletion;
    @BindView(R.id.defeat_reason_by_awakened_ancient_one_row) TableRow defeatReasonByAwakenedAncientOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.activity_party_details_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void setHeadBackground(AncientOne ancientOne, Expansion expansion) {
        background.setImageResource(getResources().getIdentifier(ancientOne.getImageResource(), "drawable", getPackageName()));
        expansionIcon.setImageResource(getResources().getIdentifier(expansion.getImageResource(), "drawable", getPackageName()));
    }

    @Override
    public void setScore(int score) {
        this.score.setText(String.valueOf(score));
    }

    @Override
    public void setVictoryIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.stars));
    }

    @Override
    public void setDefeatByEliminationIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.inestigators_out));
    }

    @Override
    public void setDefeatByMythosDepletionIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.mythos_empty));
    }

    @Override
    public void setDefeatByAwakenedAncientOneIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.skull));
    }

    @Override
    public void showScore() {
        score.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScore() {
        score.setVisibility(View.GONE);
    }

    @Override
    public void setMysteriesCount(String mysteriesCount) {
        victoryMysteriesCount.setText(mysteriesCount);
        defeatMysteriesCount.setText(mysteriesCount);
    }

    @Override
    public void showVictoryCard() {
        includedVictoryResultCard.setVisibility(View.VISIBLE);
        includedDefeatReasonCard.setVisibility(View.GONE);
    }

    @Override
    public void showDefeatCard() {
        includedVictoryResultCard.setVisibility(View.GONE);
        includedDefeatReasonCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void setInitialConditions(String date, String ancientOne, String prelude, String playersCount) {
        this.date.setText(date);
        this.ancientOne.setText(ancientOne);
        this.prelude.setText(prelude);
        this.playersCount.setText(playersCount);
    }

    @Override
    public void setAdditionalRules(boolean isSimpleMyths, boolean isNormalMyths, boolean isHardMyths, boolean isStartingRumor) {
        simpleMyths.setVisibility(isSimpleMyths ? View.VISIBLE : View.GONE);
        normalMyths.setVisibility(isNormalMyths ? View.VISIBLE : View.GONE);
        hardMyths.setVisibility(isHardMyths ? View.VISIBLE : View.GONE);
        startingRumor.setVisibility(isStartingRumor ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setVictoryResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount) {
        this.gatesCount.setText(String.valueOf(gatesCount));
        this.monstersCount.setText(String.valueOf(monstersCount));
        this.curseCount.setText(String.valueOf(curseCount));
        this.rumorsCount.setText(String.valueOf(rumorsCount));
        this.cluesCount.setText(String.valueOf(cluesCount));
        this.blessedCount.setText(String.valueOf(blessedCount));
        this.doomCount.setText(String.valueOf(doomCount));
    }

    @Override
    public void setDefeatReason(boolean isDefeatReasonByElimination, boolean isDefeatReasonByMythosDepletion, boolean isDefeatReasonByAwakenedAncientOne) {
        defeatReasonByElimination.setVisibility(isDefeatReasonByElimination ? View.VISIBLE : View.GONE);
        defeatReasonByMythosDepletion.setVisibility(isDefeatReasonByMythosDepletion ? View.VISIBLE : View.GONE);
        defeatReasonByAwakenedAncientOne.setVisibility(isDefeatReasonByAwakenedAncientOne ? View.VISIBLE : View.GONE);
    }
}
