package ru.mgusev.eldritchhorror.ui.activity.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.DetailsAdapter;
import ru.mgusev.eldritchhorror.androidmaterialgallery.GalleryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.details.DetailsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;

public class DetailsActivity extends MvpAppCompatActivity implements DetailsView, OnItemClicked, ImageViewer.OnDismissListener, ImageViewer.OnImageChangeListener {

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

    @BindView(R.id.investigator_result_not_selected) TextView investigatorsNotSelected;
    @BindView(R.id.investigator_result_recycle_view) RecyclerView investigatorsRV;

    @BindView(R.id.victory_result_card) View includedVictoryResultCard;
    @BindView(R.id.victory_result_time) TextView victoryTime;
    @BindView(R.id.victory_result_mysteries_count) TextView victoryMysteriesCount;
    @BindView(R.id.victory_result_gates_count) TextView gatesCount;
    @BindView(R.id.victory_result_monsters_count) TextView monstersCount;
    @BindView(R.id.victory_result_curse_count) TextView curseCount;
    @BindView(R.id.victory_result_rumors_count) TextView rumorsCount;
    @BindView(R.id.victory_result_clues_count) TextView cluesCount;
    @BindView(R.id.victory_result_blessed_count) TextView blessedCount;
    @BindView(R.id.victory_result_doom_count) TextView doomCount;

    @BindView(R.id.defeat_reason_card) View includedDefeatReasonCard;
    @BindView(R.id.defeat_reason_time) TextView defeatTime;
    @BindView(R.id.defeat_reason_mysteries_count) TextView defeatMysteriesCount;
    @BindView(R.id.defeat_reason_by_elimination_row) TableRow defeatReasonByElimination;
    @BindView(R.id.defeat_reason_by_mythos_depletion_row) TableRow defeatReasonByMythosDepletion;
    @BindView(R.id.defeat_reason_by_awakened_ancient_one_row) TableRow defeatReasonByAwakenedAncientOne;
    @BindView(R.id.defeat_reason_by_surrender_row) TableRow defeatReasonBySurrender;
    @BindView(R.id.defeat_reason_by_rumor_row) TableRow defeatReasonByRumor;
    @BindView(R.id.defeat_reason_by_rumor_name_tv) TextView rumorName;

    @BindView(R.id.photo_details_recycle_view) RecyclerView photoRV;
    @BindView(R.id.photo_details_none) TextView photoNoneMessage;

    @BindView(R.id.comment_tv) TextView commentTV;
    @BindView(R.id.loader) LinearLayout loader;

    private DetailsAdapter adapter;
    private GalleryAdapter galleryAdapter;
    private AlertDialog deleteDialog;
    private List<String> uriList;
    private int columnsCount = 4;
    private ImageViewer imageViewer;
    private boolean imageViewerIsOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        initToolbar();
        initInvestigatorRV();
        initPhotoRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoader();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.activity_party_details_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initInvestigatorRV() {
        LinearLayoutManager leanerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        investigatorsRV.setLayoutManager(leanerLayoutManager);
        investigatorsRV.setHasFixedSize(true);

        adapter = new DetailsAdapter(this);
        investigatorsRV.setAdapter(adapter);
    }

    private void initPhotoRV() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 6;
        photoRV.setLayoutManager(new GridLayoutManager(this, columnsCount));
        photoRV.setHasFixedSize(true);

        galleryAdapter = new GalleryAdapter(this);
        photoRV.setAdapter(galleryAdapter);
        galleryAdapter.setOnClick(this);
    }

    private void startIntent(int position) {
        showLoader();
        detailsPresenter.setCurrentPagerPosition(position);
        detailsPresenter.setGameToRepository();
        Intent editIntent = new Intent(this, PagerActivity.class);
        startActivity(editIntent);
    }

    private void showLoader() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loader.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                detailsPresenter.showDeleteDialog();
                return true;
            case R.id.action_edit:
                startIntent(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.start_data_result_edit_button, R.id.investigator_result_edit_button, R.id.victory_result_edit_button, R.id.defeat_reason_edit_button, R.id.comment_edit_button, R.id.photo_details_edit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_data_result_edit_button:
                startIntent(0);
                break;
            case R.id.investigator_result_edit_button:
                startIntent(1);
                break;
            case R.id.photo_details_edit_button:
                startIntent(3);
                break;
            default:
                startIntent(2);
                break;
        }
    }

    @Override
    public void showDeleteDialog() {
        if (deleteDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.dialogAlert);
            builder.setMessage(R.string.deleteDialogMessage);
            builder.setIcon(R.drawable.delete);
            builder.setPositiveButton(R.string.messageOK, (dialog, which) -> {
                detailsPresenter.deleteGame();
                finish();
            });
            builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> detailsPresenter.hideDeleteDialog());
            deleteDialog = builder.show();
        }
    }

    @Override
    public void hideDeleteDialog() {
        deleteDialog = null;
        //Delete showDeleteDialog() from currentState with DismissDialogStrategy
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
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.investigators_out));
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
    public void setDefeatByRumorIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.defeat_by_rumor));
    }

    @Override
    public void setDefeatBySurrenderIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.defeat_by_surrender));
    }

    @Override
    public void setDefeatByRumorName(String name) {
        rumorName.setText(name);
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
    public void setTime(String time) {
        victoryTime.setText(time);
        defeatTime.setText(time);
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
    public void setComment(String text) {
        commentTV.setText(text);
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
    public void setDefeatReason(boolean isDefeatReasonByElimination, boolean isDefeatReasonByMythosDepletion, boolean isDefeatReasonByAwakenedAncientOne, boolean isDefeatReasonBySurrender, boolean isDefeatReasonByRumor) {
        defeatReasonByElimination.setVisibility(isDefeatReasonByElimination ? View.VISIBLE : View.GONE);
        defeatReasonByMythosDepletion.setVisibility(isDefeatReasonByMythosDepletion ? View.VISIBLE : View.GONE);
        defeatReasonByAwakenedAncientOne.setVisibility(isDefeatReasonByAwakenedAncientOne ? View.VISIBLE : View.GONE);
        defeatReasonBySurrender.setVisibility(isDefeatReasonBySurrender ? View.VISIBLE : View.GONE);
        defeatReasonByRumor.setVisibility(isDefeatReasonByRumor ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideInvestigatorsNotSelected(boolean isVisible) {
        investigatorsNotSelected.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        investigatorsRV.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setInvestigatorsList(List<Investigator> list) {
        adapter.setInvestigatorList(list);
    }

    @Override
    public void setPhotoList(List<String> list) {
        photoRV.setVisibility(View.GONE);
        uriList = list;
        galleryAdapter.addGalleryItems(uriList);
        photoRV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
        if (imageViewer != null) {
            imageViewerIsOpen = true;
            imageViewer.onDismiss();
        }

    }

    @Override
    public void onItemClick(int position) {
        detailsPresenter.setCurrentPosition(position);
        detailsPresenter.openFullScreenPhotoViewer();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onEditClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void openFullScreenPhotoViewer() {
        imageViewer = new ImageViewer.Builder(this, uriList)
                .setImageChangeListener(this)
                .setOnDismissListener(this)
                .setStartPosition(detailsPresenter.getCurrentPosition())
                .show();
    }

    @Override
    public void closeFullScreenPhotoViewer() {
        if (imageViewer != null) imageViewer.onDismiss();
    }

    @Override
    public void onDismiss() {
        if (!imageViewerIsOpen) detailsPresenter.closeFullScreenPhotoViewer();
    }

    @Override
    public void onImageChange(int position) {
        detailsPresenter.setCurrentPosition(position);
    }

    @Override
    public void showPhotoNoneMessage(boolean isShow) {
        photoNoneMessage.setVisibility(isShow ? View.VISIBLE : View.GONE);
        photoRV.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }
}