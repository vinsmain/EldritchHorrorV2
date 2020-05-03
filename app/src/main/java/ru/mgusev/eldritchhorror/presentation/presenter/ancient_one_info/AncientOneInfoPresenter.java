package ru.mgusev.eldritchhorror.presentation.presenter.ancient_one_info;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import retrofit2.HttpException;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.EHAPIService;
import ru.mgusev.eldritchhorror.api.json_model.Article;
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
    private List<Article> endingList;
    private boolean infoIsExpand;
    private boolean storyIsExpand;
    private int ancientOneSpinnerCurrentPosition = -1; //-1 - ничего не выбрано
    private boolean userTrackingTouch;
    private CompositeDisposable audioStateChangeSubscribe;

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
            clickRefresh();
        } else {
            completeUpdateArticleList();
            if (ancientOneSpinnerCurrentPosition == -1) {
                getViewState().setAncientOneSpinnerPosition(0);
            }
        }
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

    private List<Article> getArticleListWithHeaders(List<Article> list) {
        List<Article> listWithHeaders = new ArrayList<>();
        String tagTitle = "";
        int headerId = -1;
        for (Article article : list) {
            if (!article.getTags().getItemTags().isEmpty()) {
                if (!article.getTags().getItemTags().get(0).getTitle().equals(tagTitle)) {
                    Article header = new Article();
                    tagTitle = article.getTags().getItemTags().get(0).getTitle();
                    header.setId(headerId);
                    header.setTitle("#errata");
                    header.setIntrotext("<h2>" + tagTitle + "</h2>");
                    listWithHeaders.add(header);
                    headerId--;
                }
            }
            listWithHeaders.add(article);
        }
        return listWithHeaders;
    }

    public void showSuccessAlert() {
        getViewState().showAlert(R.string.update_complete);
        if (ancientOneSpinnerCurrentPosition == -1) {
            getViewState().setAncientOneSpinnerPosition(0);
        }
    }

    public void showErrorAlert(Throwable e) {
        Timber.d(e);
        if (e instanceof UnknownHostException || e instanceof HttpException)
            getViewState().showAlert(R.string.connection_error_header);
        else
            getViewState().showAlert(R.string.format_data_error_header);
        getViewState().setVisibilityContent(false);
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

            getViewState().setVisibilityContent(true);

            getViewState().expandInfoView(infoIsExpand);
            getViewState().expandStoryView(storyIsExpand);

            getViewState().setAncientOneImage(repository.getAncientOne(ancientOneNameList.get(ancientOneSpinnerCurrentPosition)));

            Article infoItem = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList());
            Article storyItem = getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneStoryList());

            getViewState().setInfoText(infoItem != null ? infoItem.getIntrotext() : "");
            getViewState().setStoryText(storyItem != null ? storyItem.getIntrotext() : "");
            //getViewState().setVisibilityAudioPlayer((infoItem != null && !infoItem.getFiles().getImageIntroAlt().equals("")) || (storyItem != null && !storyItem.getFiles().getImageIntroAlt().equals("")));

            getViewState().setVisibilityInfoAudioButton(infoItem != null && !infoItem.getFiles().getImageIntroAlt().equals(""));
            getViewState().setVisibilityStoryAudioButton(storyItem != null && !storyItem.getFiles().getImageIntroAlt().equals(""));
            getViewState().setAncientOneSpinnerPosition(ancientOneSpinnerCurrentPosition);

            if (audioStateChangeSubscribe != null) audioStateChangeSubscribe.dispose();
            audioStateChangeSubscribe = new CompositeDisposable();
            audioStateChangeSubscribe.add(repository.getAudioPlayerStateChangeSubject().subscribe(this::updateAudioState, Timber::e));
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
        Timber.d(state.getAudio().getImageIntroAlt() + " " + state.isPlaying());
        getViewState().setVisibilityAudioPlayer(state.getAudio() != null);
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

    private void initAudioPlayerProgressSubscribe() {
//        repository.getAudioPlayerProgressChangeSubject(new DisposableObserver<AudioState>() {
//
//            @Override
//            public void onNext(AudioState state) {
//                //if (newsDetailsItem != null && newsDetailsItem.getAudio() != null && !userTrackingTouch && state.getAudio().equals(newsDetailsItem.getAudio())) {
//                    //Timber.d(NewsDetailsPresenter.this + " " + state.getAudio().equals(newsDetailsItem.getAudio()) + " " + state.getTotalDuration() + " " + state.getCurrentPosition());
//                    if (state.getTotalDuration() != -1 && state.getCurrentPosition() != -1) {
//                        getViewState().updateDuration((int) (state.getTotalDuration() / 1000), (int) (state.getCurrentPosition() / 1000));
//                    } else {
//                        //getViewState().updateDuration((int) newsDetailsItem.getAudio().getDuration(), 0);
//                    }
//                }
//
//
//            @Override
//            public void onError(Throwable e) {
//                Timber.d(e);
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
    }

    public void onAudioControlButtonClicked() {
        //Timber.d(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList()).getFiles().getImageIntroAlt());
        getViewState().updateAudioPlayerState(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList()).getFiles());

    }

    public void onUserTrackingTouch(boolean touch) {
        userTrackingTouch = touch;
    }

    public void onAudioInfoButtonClicked() {
        getViewState().updateAudioPlayerState(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneInfoList()).getFiles());
    }

    public void onAudioStoryButtonClicked() {
        getViewState().updateAudioPlayerState(getArticleByAncientOneName(ancientOneNameList.get(ancientOneSpinnerCurrentPosition), repository.getAncientOneStoryList()).getFiles());
    }

    @Override
    public void onDestroy() {
        audioStateChangeSubscribe.dispose();
        super.onDestroy();
    }
}