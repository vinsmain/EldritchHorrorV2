package ru.mgusev.eldritchhorror.presentation.presenter.ancient_one_info;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import retrofit2.HttpException;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.EHAPIService;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.api.json_model.Files;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.AudioState;
import ru.mgusev.eldritchhorror.presentation.view.ancient_one_info.AncientOneInfoView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class AncientOneInfoPresenter extends MvpPresenter<AncientOneInfoView> {

    @Inject
    Repository repository;
    @Inject
    EHAPIService service;

    private List<String> ancientOneNameList;
    private boolean infoIsExpand;
    private boolean storyIsExpand;
    private int ancientOneSpinnerCurrentPosition = -1; //-1 - ничего не выбрано
    private boolean userTrackingTouch;
    private CompositeDisposable audioStateChangeSubscribe;
    private CompositeDisposable audioProgressChangeSubscribe;
    private Files currentAudio;

    public AncientOneInfoPresenter() {
        App.getComponent().inject(this);
        service.setPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        ancientOneNameList = getAncientOneNameList();
        getViewState().initAncientOneSpinner(ancientOneNameList);
        if (repository.getAncientOneStoryList().isEmpty() || repository.getAncientOneInfoList().isEmpty()) {
            getViewState().setVisibilityContent(false);
            getViewState().setNoneResultsTVVisibility(true);
            clickRefresh();
        } else {
            completeUpdateArticleList();
            getViewState().setAncientOneSpinnerPosition(ancientOneSpinnerCurrentPosition == -1 ? 0 : ancientOneSpinnerCurrentPosition);
            updateContent();
        }
    }

    public void setAncientOneSpinnerCurrentPositionFromIntent(int ancientOneSpinnerCurrentPosition, boolean isNewIntent) {
        if (!isNewIntent) this.ancientOneSpinnerCurrentPosition = ancientOneSpinnerCurrentPosition;
        getViewState().setAncientOneSpinnerPosition(ancientOneSpinnerCurrentPosition);
    }

    private List<String> getAncientOneNameList() {
        ancientOneNameList = new ArrayList<>();
        for (AncientOne ancientOne : repository.getAncientOneList()) {
            ancientOneNameList.add(ancientOne.getName());
        }
        Collections.sort(ancientOneNameList);
        return ancientOneNameList;
    }

    public void setStoryList(List<Article> articleList) {
        repository.setAncientOneStoryList(articleList);
    }

    public void setInfoList(List<Article> articleList) {
        repository.setAncientOneInfoList(articleList);
    }

    public void clickRefresh() {
        getViewState().startRefreshIconRotate();
        service.getStories();
    }

    public void completeUpdateArticleList() {
        getViewState().stopRefreshIconRotate();
    }

    public void showSuccessAlert() {
        getViewState().showAlert(R.string.update_complete);
        getViewState().setAncientOneSpinnerPosition(ancientOneSpinnerCurrentPosition == -1 ? 0 : ancientOneSpinnerCurrentPosition);
        updateContent();
    }

    public void showErrorAlert(Throwable e) {
        Timber.d(e);
        if (e instanceof UnknownHostException || e instanceof HttpException)
            getViewState().showAlert(R.string.connection_error_header);
        else
            getViewState().showAlert(R.string.format_data_error_header);
        getViewState().setVisibilityContent(false);
        getViewState().setNoneResultsTVVisibility(true);
    }

    public void expandableInfoOnClick() {
        infoIsExpand = !infoIsExpand;
        getViewState().expandInfoView(infoIsExpand);
    }

    public void expandableStoryOnClick() {
        storyIsExpand = !storyIsExpand;
        getViewState().expandStoryView(storyIsExpand);
    }

    public void onAncientOneSelected(int position) {
        if (ancientOneSpinnerCurrentPosition != position) {
            ancientOneSpinnerCurrentPosition = position;
            storyIsExpand = false;
            infoIsExpand = false;
            updateContent();
        }
    }

    public void updateContent() {
        if (!repository.getAncientOneStoryList().isEmpty() && !repository.getAncientOneInfoList().isEmpty()) {
            getViewState().setVisibilityContent(true);
            getViewState().setNoneResultsTVVisibility(false);

            getViewState().expandInfoView(infoIsExpand);
            getViewState().expandStoryView(storyIsExpand);

            getViewState().setAncientOneImage(repository.getAncientOne(ancientOneNameList.get(ancientOneSpinnerCurrentPosition)));

            Article infoItem = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList());
            Article storyItem = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneStoryList());

            getViewState().setInfoText(infoItem != null ? infoItem.getIntrotext() : "");
            getViewState().setStoryText(storyItem != null ? storyItem.getIntrotext() : "");

            getViewState().setVisibilityInfoAudioButton(infoItem != null && !infoItem.getFiles().getImageIntroAlt().equals(""));
            getViewState().setVisibilityStoryAudioButton(storyItem != null && !storyItem.getFiles().getImageIntroAlt().equals(""));
            getViewState().setAncientOneSpinnerPosition(ancientOneSpinnerCurrentPosition);

            if (audioStateChangeSubscribe != null) audioStateChangeSubscribe.dispose();
            audioStateChangeSubscribe = new CompositeDisposable();
            audioStateChangeSubscribe.add(repository.getAudioPlayerStateChangeSubject().subscribe(this::updateAudioState, Timber::e));

            if (audioProgressChangeSubscribe != null) audioProgressChangeSubscribe.dispose();
            audioProgressChangeSubscribe = new CompositeDisposable();
            audioProgressChangeSubscribe.add(repository.getAudioPlayerProgressChangeSubject().subscribe(this::updateAudioProgress, Timber::e));
        }
    }

    private Article getArticleByAncientOneName(String name, List<Article> list) {
        for (Article article : list) {
            if (article.getTitle().equals(name))
                return article;
        }
        return null;
    }

    private void updateAudioState(AudioState state) {
        currentAudio = state.getAudio();
        getViewState().setVisibilityAudioPlayer(state.getAudio() != null);
        getViewState().setAudioTitle(state.getAudio().getTitle());
        getViewState().changeAudioControlButtonIcon(state.isPlaying());

        if (state.getAudio().equals(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList()).getFiles())) {
            getViewState().changeAudioInfoIcon(state.isPlaying());
            getViewState().changeAudioStoryIcon(false);
        } else if (state.getAudio().equals(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneStoryList()).getFiles())) {
            getViewState().changeAudioStoryIcon(state.isPlaying());
            getViewState().changeAudioInfoIcon(false);
        } else {
            getViewState().changeAudioStoryIcon(false);
            getViewState().changeAudioInfoIcon(false);
        }
    }

    private void updateAudioProgress(AudioState state) {
        if (!userTrackingTouch) {
            if (state.getTotalDuration() != -1 && state.getCurrentPosition() != -1) {
                getViewState().updateDuration((int) (state.getTotalDuration() / 1000), Math.min((int) (state.getCurrentPosition() / 1000), (int) (state.getTotalDuration() / 1000)));
            } else {
                getViewState().updateDuration((int) (state.getTotalDuration() / 1000), 0);
            }
        }
    }

    public void onAudioControlButtonClicked() {
        if (currentAudio != null)
            getViewState().updateAudioPlayerState(currentAudio);
    }

    public void onUserTrackingTouch(boolean touch) {
        userTrackingTouch = touch;
    }

    public void onAudioInfoButtonClicked() {
        Article info = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList());
        if (info != null && info.getFiles() != null) {
            info.getFiles().setTitle(info.getTitle() + " - Краткая информация");
            info.getFiles().setCover(repository.getAncientOne(ancientOneNameList.get(ancientOneSpinnerCurrentPosition)).getImageResource());
            info.getFiles().setSpinnerPosition(ancientOneSpinnerCurrentPosition);
            getViewState().updateAudioPlayerState(info.getFiles());
        }
    }

    public void onAudioStoryButtonClicked() {
        Article story = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneStoryList());
        if (story != null && story.getFiles() != null) {
            story.getFiles().setTitle(story.getTitle() + " - Вступительная история");
            story.getFiles().setCover(repository.getAncientOne(ancientOneNameList.get(ancientOneSpinnerCurrentPosition)).getImageResource());
            story.getFiles().setSpinnerPosition(ancientOneSpinnerCurrentPosition);
            getViewState().updateAudioPlayerState(story.getFiles());
        }
    }

    @Override
    public void onDestroy() {
        audioStateChangeSubscribe.dispose();
        super.onDestroy();
    }
}