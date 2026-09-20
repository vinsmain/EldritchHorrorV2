package ru.mgusev.eldritchhorror.ui.activity.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityDetailsBinding;
import ru.mgusev.eldritchhorror.ui.adapter.details.DetailsAdapter;
import ru.mgusev.eldritchhorror.utils.gallery.GalleryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.interfaces.OnShareImageClick;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.details.DetailsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;
import ru.mgusev.eldritchhorror.utils.PhotoViewerDialog;

public class DetailsActivity extends MvpAppCompatActivity implements DetailsView, OnItemClicked, OnShareImageClick {

    @InjectPresenter
    DetailsPresenter detailsPresenter;

    private ActivityDetailsBinding binding;

    private Toolbar toolbar;
    private ImageView background;
    private ImageView expansionIcon;
    private ImageView victoryIcon;
    private TextView score;

    private TextView date;
    private TextView ancientOne;
    private TextView prelude;
    private TextView playersCount;
    private TableRow simpleMyths;
    private TableRow normalMyths;
    private TableRow hardMyths;
    private TableRow startingRumor;

    private TextView investigatorsNotSelected;
    private RecyclerView investigatorsRV;

    private View includedVictoryResultCard;
    private TextView victoryTime;
    private TextView victoryMysteriesCount;
    private TextView gatesCount;
    private TextView monstersCount;
    private TextView curseCount;
    private TextView rumorsCount;
    private TextView cluesCount;
    private TextView blessedCount;
    private TextView doomCount;

    private View includedDefeatReasonCard;
    private TextView defeatTime;
    private TextView defeatMysteriesCount;
    private TableRow defeatReasonByElimination;
    private TableRow defeatReasonByMythosDepletion;
    private TableRow defeatReasonByAwakenedAncientOne;
    private TableRow defeatReasonBySurrender;
    private TableRow defeatReasonByRumor;
    private TextView rumorName;
    private TableRow defeatReasonByPrelude;
    private TextView preludeName;

    private RecyclerView photoRV;
    private TextView photoNoneMessage;

    private TextView commentTV;
    private LinearLayout loader;

    private ImageButton selectAllBtn;
    private ImageButton selectCancelBtn;
    private ImageButton shareImageBtn;

    private DetailsAdapter adapter;
    private GalleryAdapter galleryAdapter;
    private AlertDialog deleteDialog;
    private List<String> imagePathList;
    private int columnsCount = 4;
    private Dialog imageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindViews();
        bindClickListeners();
        EdgeToEdge.applyCollapsingHeader(
                this, toolbar, binding.mainContent, expansionIcon, binding.linearLayout2);
        EdgeToEdge.applyHorizontalInsets(binding.scrollView2);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        initToolbar();
        initInvestigatorRV();
        initPhotoRV();
        setVisibilityIcons();
    }

    private void bindViews() {
        toolbar = binding.toolbar;
        background = binding.detailsHeaderBackground;
        expansionIcon = binding.detailsExpansionIcon;
        victoryIcon = binding.detailsVictoryIcon;
        score = binding.detailsScore;

        date = binding.startDataResultCard.startDataResultDate;
        ancientOne = binding.startDataResultCard.startDataResultAncientOne;
        prelude = binding.startDataResultCard.startDataResultPrelude;
        playersCount = binding.startDataResultCard.startDataResultPlayersCount;
        simpleMyths = binding.startDataResultCard.startDataResultSimpleMythsRow;
        normalMyths = binding.startDataResultCard.startDataResultNormalMythsRow;
        hardMyths = binding.startDataResultCard.startDataResultHardMythsRow;
        startingRumor = binding.startDataResultCard.startDataResultStartingRumorRow;

        investigatorsNotSelected = binding.investigatorResultCard.investigatorResultNotSelected;
        investigatorsRV = binding.investigatorResultCard.investigatorResultRecycleView;

        includedVictoryResultCard = binding.victoryResultCard.getRoot();
        victoryTime = binding.victoryResultCard.victoryResultTime;
        victoryMysteriesCount = binding.victoryResultCard.victoryResultMysteriesCount;
        gatesCount = binding.victoryResultCard.victoryResultGatesCount;
        monstersCount = binding.victoryResultCard.victoryResultMonstersCount;
        curseCount = binding.victoryResultCard.victoryResultCurseCount;
        rumorsCount = binding.victoryResultCard.victoryResultRumorsCount;
        cluesCount = binding.victoryResultCard.victoryResultCluesCount;
        blessedCount = binding.victoryResultCard.victoryResultBlessedCount;
        doomCount = binding.victoryResultCard.victoryResultDoomCount;

        includedDefeatReasonCard = binding.defeatReasonCard.getRoot();
        defeatTime = binding.defeatReasonCard.defeatReasonTime;
        defeatMysteriesCount = binding.defeatReasonCard.defeatReasonMysteriesCount;
        defeatReasonByElimination = binding.defeatReasonCard.defeatReasonByEliminationRow;
        defeatReasonByMythosDepletion = binding.defeatReasonCard.defeatReasonByMythosDepletionRow;
        defeatReasonByAwakenedAncientOne = binding.defeatReasonCard.defeatReasonByAwakenedAncientOneRow;
        defeatReasonBySurrender = binding.defeatReasonCard.defeatReasonBySurrenderRow;
        defeatReasonByRumor = binding.defeatReasonCard.defeatReasonByRumorRow;
        rumorName = binding.defeatReasonCard.defeatReasonByRumorNameTv;
        defeatReasonByPrelude = binding.defeatReasonCard.defeatReasonByPreludeRow;
        preludeName = binding.defeatReasonCard.defeatReasonByPreludeNameTv;

        photoRV = binding.photoDetailsCard.photoDetailsRecycleView;
        photoNoneMessage = binding.photoDetailsCard.photoDetailsNone;
        commentTV = binding.commentCard.commentTv;
        loader = (LinearLayout) binding.loader.getRoot();
        selectAllBtn = binding.photoDetailsCard.photoDetailsSelectAll;
        selectCancelBtn = binding.photoDetailsCard.photoDetailsSelectCancel;
        shareImageBtn = binding.photoDetailsCard.photoDetailsShareImage;
    }

    private void bindClickListeners() {
        binding.startDataResultCard.startDataResultEditButton.setOnClickListener(v -> startIntent(0));
        binding.investigatorResultCard.investigatorResultEditButton.setOnClickListener(v -> startIntent(1));
        binding.victoryResultCard.victoryResultEditButton.setOnClickListener(v -> startIntent(2));
        binding.defeatReasonCard.defeatReasonEditButton.setOnClickListener(v -> startIntent(2));
        binding.commentCard.commentEditButton.setOnClickListener(v -> startIntent(2));
        binding.photoDetailsCard.photoDetailsEditButton.setOnClickListener(v -> startIntent(3));
        selectAllBtn.setOnClickListener(v -> detailsPresenter.selectAllPhoto(true));
        selectCancelBtn.setOnClickListener(v -> detailsPresenter.selectAllPhoto(false));
        shareImageBtn.setOnClickListener(v -> detailsPresenter.shareImage());
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
        detailsPresenter.selectAllPhoto(false);
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
        if (item.getItemId() == R.id.action_delete) {
            detailsPresenter.showDeleteDialog();
            return true;
        }
        if (item.getItemId() == R.id.action_edit) {
            startIntent(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void setDefeatByPreludeIcon() {
        victoryIcon.setImageDrawable(getResources().getDrawable(R.drawable.defeat_by_prelude));
    }

    @Override
    public void setDefeatByRumorName(String name) {
        rumorName.setText(name);
    }

    @Override
    public void setDefeatByPreludeName(String name) {
        preludeName.setText(name);
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
    public void setDefeatReason(boolean isDefeatReasonByElimination, boolean isDefeatReasonByMythosDepletion, boolean isDefeatReasonByAwakenedAncientOne, boolean isDefeatReasonBySurrender, boolean isDefeatReasonByRumor, boolean isDefeatReasonByPrelude) {
        defeatReasonByElimination.setVisibility(isDefeatReasonByElimination ? View.VISIBLE : View.GONE);
        defeatReasonByMythosDepletion.setVisibility(isDefeatReasonByMythosDepletion ? View.VISIBLE : View.GONE);
        defeatReasonByAwakenedAncientOne.setVisibility(isDefeatReasonByAwakenedAncientOne ? View.VISIBLE : View.GONE);
        defeatReasonBySurrender.setVisibility(isDefeatReasonBySurrender ? View.VISIBLE : View.GONE);
        defeatReasonByRumor.setVisibility(isDefeatReasonByRumor ? View.VISIBLE : View.GONE);
        defeatReasonByPrelude.setVisibility(isDefeatReasonByPrelude ? View.VISIBLE : View.GONE);
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
        imagePathList = list;
        galleryAdapter.addGalleryItems(imagePathList);
        photoRV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
        if (imageDialog != null && imageDialog.isShowing()) imageDialog.dismiss();

    }

    @Override
    public void onItemClick(int position) {
        if (detailsPresenter.isSelectMode()) detailsPresenter.selectGalleryItem(imagePathList.get(position), position);
        else {
            detailsPresenter.setCurrentPosition(position);
            detailsPresenter.openFullScreenPhotoViewer();
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (!detailsPresenter.isSelectMode()) {
            detailsPresenter.setSelectMode(true);
            detailsPresenter.selectGalleryItem(imagePathList.get(position), position);
        }
    }

    @Override
    public void openFullScreenPhotoViewer() {
        imageDialog = PhotoViewerDialog.show(
                this,
                Uri.parse(imagePathList.get(detailsPresenter.getCurrentPosition())),
                dialog -> detailsPresenter.closeFullScreenPhotoViewer());
    }

    @Override
    public void closeFullScreenPhotoViewer() {
        if (imageDialog != null && imageDialog.isShowing()) imageDialog.dismiss();
    }

    @Override
    public void showPhotoNoneMessage(boolean isShow) {
        photoNoneMessage.setVisibility(isShow ? View.VISIBLE : View.GONE);
        photoRV.setVisibility(isShow ? View.GONE : View.VISIBLE);
        setVisibilityIcons();
    }

    @Override
    public void onClickShare() {
        detailsPresenter.shareImage();
    }

    @Override
    public void selectGalleryItem(List<String> list, int position) {
        galleryAdapter.selectGalleryItem(list, position);
        setVisibilityIcons();
    }

    @Override
    public void updatePhotoGallery(List<String> imagesUrlList) {
        galleryAdapter.addGalleryItems(imagesUrlList);
        imagePathList = imagesUrlList;
        setVisibilityIcons();
    }

    private void setVisibilityIcons() {
        selectAllBtn.setVisibility(photoNoneMessage.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        selectCancelBtn.setVisibility(detailsPresenter.isSelectMode() ? View.VISIBLE : View.GONE);
        shareImageBtn.setVisibility(detailsPresenter.isSelectMode() ? View.VISIBLE : View.GONE);
    }
}
