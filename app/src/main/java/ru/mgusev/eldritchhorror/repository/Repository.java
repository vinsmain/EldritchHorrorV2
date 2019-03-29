package ru.mgusev.eldritchhorror.repository;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.Module;
import durdinapps.rxfirebase2.RxFirebaseChildEvent;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.database.staticDB.StaticDataDB;
import ru.mgusev.eldritchhorror.database.userDB.UserDataDB;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Ending;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.ImageFile;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.app.AppModule;
import ru.mgusev.eldritchhorror.model.Rumor;
import ru.mgusev.eldritchhorror.model.Specialization;
import ru.mgusev.eldritchhorror.model.StatisticsInvestigator;
import timber.log.Timber;

@Module(includes = AppModule.class)
public class Repository {

    private final Context context;
    private final StaticDataDB staticDataDB;
    private final UserDataDB userDataDB;
    private final PrefHelper prefHelper;
    private final FileHelper fileHelper;

    private PublishSubject<AncientOne> ancientOnePublish;
    private PublishSubject<Integer> scorePublish;
    private PublishSubject<Boolean> isWinPublish;
    private PublishSubject<Investigator> investigatorPublish;
    private PublishSubject<List<Expansion>> expansionPublish;
    private PublishSubject<Integer> randomPublish;
    private PublishSubject<Integer> clearPublish;
    private PublishSubject<List<Game>> gameListPublish;
    private PublishSubject<Boolean> authPublish;
    private PublishSubject<Boolean> ratePublish;
    private PublishSubject<Boolean> clickPhotoButtonPublish;
    private PublishSubject<Boolean> updatePhotoGalleryPublish;
    private PublishSubject<Boolean> selectModePublish;
    private PublishSubject<Boolean> selectAllPhotoPublish;
    private PublishSubject<Boolean> deleteSelectImagesPublish;

    private CompositeDisposable firebaseDBSubscribe;
    private CompositeDisposable firebaseFilesSubscribe;
    private CompositeDisposable uploadFileSubscribe;
    private CompositeDisposable downloadFileSubscribe;

    private Game game;
    private Investigator investigator;
    private int pagerPosition = 0;
    private FirebaseHelper firebaseHelper;
    private FirebaseUser user;
    private List<Article> articleListRu;
    private List<Article> articleListEn;

    @Inject
    public Repository(Context context, StaticDataDB staticDataDB, UserDataDB userDataDB, PrefHelper prefHelper, FileHelper fileHelper, FirebaseHelper firebaseHelper) {
        this.context = context;
        this.staticDataDB = staticDataDB;
        this.userDataDB = userDataDB;
        this.prefHelper = prefHelper;
        this.fileHelper = fileHelper;
        this.firebaseHelper = firebaseHelper;
        ancientOnePublish = PublishSubject.create();
        scorePublish = PublishSubject.create();
        isWinPublish = PublishSubject.create();
        investigatorPublish = PublishSubject.create();
        expansionPublish = PublishSubject.create();
        randomPublish = PublishSubject.create();
        clearPublish = PublishSubject.create();
        gameListPublish = PublishSubject.create();
        authPublish = PublishSubject.create();
        ratePublish = PublishSubject.create();
        clickPhotoButtonPublish = PublishSubject.create();
        updatePhotoGalleryPublish = PublishSubject.create();
        selectModePublish = PublishSubject.create();
        selectAllPhotoPublish = PublishSubject.create();
        deleteSelectImagesPublish = PublishSubject.create();

        uploadFileSubscribe = new CompositeDisposable();
        uploadFileSubscribe.add(firebaseHelper.getSuccessUploadFilePublish().subscribe(this::uploadFile, Timber::d));

        downloadFileSubscribe = new CompositeDisposable();
        downloadFileSubscribe.add(firebaseHelper.getDownloadFileDisposable().subscribe(this::downloadFile, Timber::d));

        articleListRu = new ArrayList<>();
        articleListEn = new ArrayList<>();

        for (Game game : getGameList(0, 0)) {
            fixSpecializationsForOldInvestigators(game);
            fixInvestigatorsLocalization(game);
        }
    }

    private void fixSpecializationsForOldInvestigators(Game game) {
        boolean update = false;
        for (Investigator investigator : game.getInvList()) {
            if (investigator.getSpecialization() == 0) {
                investigator.setSpecialization(staticDataDB.investigatorDAO().getByName(investigator.getName()).getSpecialization());
                update = true;
            }
        }
        if (update) insertGame(game);
    }

    private void fixInvestigatorsLocalization(Game game) {
        boolean update = false;
        game.setInvList(userDataDB.investigatorDAO().getByGameID(game.getId()));
        for (Investigator investigator : game.getInvList()) {
            if (investigator.getNameRU().equals("Агнес Бэйкер")) {
                investigator.setNameRU("Агнес Бейкер");
                update = true;
            } else if (investigator.getNameRU().equals("Дэйзи Уокер")) {
                investigator.setNameRU("Дейзи Уокер");
                update = true;
            } else if (investigator.getOccupationRU().equals("Юрист")) {
                investigator.setOccupationRU("Законник");
                update = true;
            } else if (investigator.getNameRU().equals("Патрисия Хэтэуэй")) {
                investigator.setNameRU("Патрис Хатауэй");
                update = true;
            } else if (investigator.getOccupationRU().equals("Новобранец")) {
                investigator.setOccupationRU("Юный полицейский");
                update = true;
            } else if (investigator.getOccupationRU().equals("Разнорабочий")) {
                investigator.setOccupationRU("Умелец");
                update = true;
            } else if (investigator.getNameRU().equals("Дарелл Симмонс")) {
                investigator.setNameRU("Даррелл Симмонс");
                update = true;
            } else if (investigator.getNameRU().equals("Глория Голдберг") || investigator.getOccupationRU().equals("Писатель")) {
                investigator.setNameRU("Глория Гольдберг");
                investigator.setOccupationRU("Писательница");
                update = true;
            } else if (investigator.getNameRU().equals("Кейт Уинторп")) {
                investigator.setNameRU("Кейт Уинтроп");
                update = true;
            } else if (investigator.getOccupationRU().equals("Провидец")) {
                investigator.setOccupationRU("Сновидец");
                update = true;
            } else if (investigator.getOccupationRU().equals("Врач")) {
                investigator.setOccupationRU("Доктор");
                update = true;
            } else if (investigator.getNameRU().equals("Декстер Дрэйк") || investigator.getOccupationRU().equals("Фокусник")) {
                investigator.setNameRU("Декстер Дрейк");
                investigator.setOccupationRU("Иллюзионист");
                update = true;
            } else if (investigator.getOccupationRU().equals("Дилетант")) {
                investigator.setOccupationRU("Дилетантка");
                update = true;
            } else if (investigator.getNameRU().equals("Майкл МакГлен")) {
                investigator.setNameRU("Майкл Макглен");
                update = true;
            } else if (investigator.getOccupationRU().equals("Эстрадная артистка")) {
                investigator.setOccupationRU("Певица");
                update = true;
            } else if (investigator.getNameRU().equals("\"Скидс\" О'Тул")) {
                investigator.setNameRU("Скат О'Тул");
                update = true;
            } else if (investigator.getNameRU().equals("Зоуи Самарас") || investigator.getOccupationRU().equals("Шеф-повар")) {
                investigator.setNameRU("Зои Самарас");
                investigator.setOccupationRU("Повар");
                update = true;
            }
        }
        if (update) insertGame(game);
    }

    public Game getGame() {
        return game;
    }

    public Game getGame(long id) {
        Game game = userDataDB.gameDAO().getGame(id);
        if (game != null) game.setInvList(userDataDB.investigatorDAO().getByGameID(game.getId()));
        return game;
    }

    public void setGame(Game game) {
        if (game != null) {
            this.game = new Game(game);
            this.game.setInvList(userDataDB.investigatorDAO().getByGameID(game.getId()));
        }
    }

    public int getPagerPosition() {
        return pagerPosition;
    }

    public void setPagerPosition(int pagerPosition) {
        this.pagerPosition = pagerPosition;
    }

    public Context getContext() {
        return context;
    }

    public void clearGame() {
        game = null;
    }

    public Observable<AncientOne> getObservableAncientOne() {
        return ancientOnePublish;
    }

    public void ancientOneOnNext(AncientOne ancientOne) {
        ancientOnePublish.onNext(ancientOne);
    }

    public Observable<Integer> getObservableScore() {
        return scorePublish;
    }

    public void scoreOnNext() {
        scorePublish.onNext(game.getScore());
    }

    public Observable<Integer> getRandomPublish() {
        return randomPublish;
    }

    public void randomOnNext(int position) {
        randomPublish.onNext(position);
    }

    public Observable<Integer> getClearPublish() {
        return clearPublish;
    }

    public void clearOnNext(int position) {
        clearPublish.onNext(position);
    }

    public Observable<Boolean> getObservableIsWin() {
        return isWinPublish;
    }

    public void isWinOnNext() {
        isWinPublish.onNext(game.getIsWinGame());
    }

    public AncientOne getAncientOne(int id) {
        return staticDataDB.ancientOneDAO().getAncientOneByID(id);
    }

    // StartDataFragment

    public List<String> getPlayersCountArray() {
        return Arrays.asList(context.getResources().getStringArray(R.array.playersCountArray));
    }

    public List<AncientOne> getAncientOneList() {
        if (Localization.getInstance().isRusLocale())
            return staticDataDB.ancientOneDAO().getAllRU();
        else return staticDataDB.ancientOneDAO().getAllEN();
    }

    public List<Prelude> getPreludeList() {
        if (Localization.getInstance().isRusLocale()) return staticDataDB.preludeDAO().getAllRU();
        else return staticDataDB.preludeDAO().getAllEN();
    }

    public Prelude getPrelude(int id) {
        return staticDataDB.preludeDAO().getPreludeByID(id);
    }

    public AncientOne getAncientOne(String name) {
        return staticDataDB.ancientOneDAO().getAncientOneByName(name);
    }

    public Prelude getPrelude(String name) {
        return staticDataDB.preludeDAO().getPreludeByName(name);
    }


    //Rumor

    public List<Rumor> getRumorList() {
        if (Localization.getInstance().isRusLocale()) return staticDataDB.rumorDAO().getAllRU();
        else return staticDataDB.rumorDAO().getAllEN();
    }

    public Rumor getRumor(String name) {
        Timber.d("RUMOR %s", staticDataDB.rumorDAO().getRumorByName(name));
        return staticDataDB.rumorDAO().getRumorByName(name);
    }

    public Rumor getRumor(int id) {
        return staticDataDB.rumorDAO().getRumorByID(id);
    }

    // InvestigatorChoiceFragment

    public List<Investigator> getInvestigatorList() {
        if (Localization.getInstance().isRusLocale())
            return staticDataDB.investigatorDAO().getAllRU();
        else return staticDataDB.investigatorDAO().getAllEN();
    }

    public List<Expansion> getExpansionList() {
        return staticDataDB.expansionDAO().getAll();
    }

    public Expansion getExpansion(int id) {
        return staticDataDB.expansionDAO().getExpansionByID(id);
    }

    public PublishSubject<Investigator> getInvestigatorPublish() {
        return investigatorPublish;
    }

    public void investigatorOnNext() {
        investigatorPublish.onNext(investigator);
    }

    public Investigator getInvestigator() {
        return investigator;
    }

    public void setInvestigator(Investigator investigator) {
        this.investigator = investigator;
    }

    // ExpansionChoiceActivity

    public void saveExpansionList(List<Expansion> list) {
        for (Expansion expansion : list) {
            staticDataDB.expansionDAO().updateExpansion(expansion);
        }
    }

    public PublishSubject<List<Expansion>> getExpansionPublish() {
        return expansionPublish;
    }

    public void expansionOnNext() {
        expansionPublish.onNext(getExpansionList());
    }

    // Game

    public int getSortMode() {
        return prefHelper.loadSortMode();
    }

    public void setSortMode(int sortMode) {
        prefHelper.saveSortMode(sortMode);
    }

    public List<Game> getGameList(int sortMode, int ancientOneId) {
        switch (sortMode == 0 ? prefHelper.loadSortMode() : sortMode) {
            case 1:
                return ancientOneId == 0 ? userDataDB.gameDAO().getGameListSortedDateDescending() : userDataDB.gameDAO().getGameListSortedDateDescending(ancientOneId);
            case 2:
                return userDataDB.gameDAO().getGameListSortedDateAscending();
            case 3:
                return userDataDB.gameDAO().getGameListSortedAncientOne();
            case 4:
                return ancientOneId == 0 ? userDataDB.gameDAO().getGameListSortedScoreAscending() : userDataDB.gameDAO().getGameListSortedScoreAscending(ancientOneId);
            case 5:
                return userDataDB.gameDAO().getGameListSortedScoreDescending();
            default:
                return userDataDB.gameDAO().getGameListSortedDateDescending();
        }
    }

    public void initFirebaseHelper() {
        firebaseHelper.initReference(user);
        firebaseDBSubscribe = new CompositeDisposable();
        firebaseDBSubscribe.add(firebaseHelper.getChildEventDisposable().subscribe(this::processDataFromFirebase, e -> Timber.tag("FIREBASE").w(e)));

        for (Game game : userDataDB.gameDAO().getGameListSortedDateAscending()) {
            if (game.getUserID() == null) {
                game.setInvList(userDataDB.investigatorDAO().getByGameID(game.getId()));
                insertGame(game);
            }
        }

        firebaseFilesSubscribe = new CompositeDisposable();
        firebaseFilesSubscribe.add(firebaseHelper.getFileEventDisposable().subscribe(this::processFilesDataFromFirebase, e -> Timber.tag("FIREBASE").w(e)));

        for (ImageFile file : userDataDB.imageFileDAO().getAllImageFiles()) {
            if (file.getUserID() == null) {
                file.setLastModified(new Date().getTime());
                file.setUserID(user.getUid());
                addImageFile(file);
            }
            if (file.getMd5Hash() == null)
                sendFileToStorage(Uri.fromFile(fileHelper.getImageFile(file.getName(), file.getGameId())), file.getGameId());
        }
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
        if (user == null) firebaseHelper.signOut();
    }

    private void processDataFromFirebase(List<RxFirebaseChildEvent<Game>> data) {
        for (RxFirebaseChildEvent<Game> event : data)
            switch (event.getEventType()) {
                case ADDED:
                    changeGame(event.getValue());
                    break;
                case CHANGED:
                    changeGame(event.getValue());
                    break;
                case REMOVED: {
                    removeGame(event.getValue());
                    break;
                }
            }
        if (data.size() > 0) gameListOnNext();
    }

    private void processFilesDataFromFirebase(RxFirebaseChildEvent<ImageFile> data) {
        switch (data.getEventType()) {
            case ADDED:
                changeImageFile(data.getValue());
                break;
            case CHANGED:
                changeImageFile(data.getValue());
                break;
            case REMOVED: {
                removeImageFile(data.getValue());
                break;
            }
        }
    }

    public void firebaseSubscribeDispose() {
        if (firebaseDBSubscribe != null) firebaseDBSubscribe.dispose();
        if (firebaseFilesSubscribe != null) firebaseFilesSubscribe.dispose();
        if (uploadFileSubscribe != null) uploadFileSubscribe.dispose();
    }

    private void changeGame(Game game) {
        fixSpecializationsForOldInvestigators(game);
        if (this.game != null && this.game.getId() == game.getId()) this.game = game;
        if (getGameById(game.getId()) == null || game.getLastModified() > getGameById(game.getId()).getLastModified()) {
            insertGameToDB(game);
            //gameListOnNext();
        }
    }

    private void removeGame(Game game) {
        if (game != null) {
            deleteGameFromDB(game);
            //gameListOnNext();
        }
    }

    private void changeImageFile(ImageFile file) {
        if (file != null && getGame(file.getGameId()) != null) {
            if (getImageFile(file.getName()) == null)
                userDataDB.imageFileDAO().insertImageFile(file);
            if (file.getMd5Hash() != null) getFileFromStorage(file);
        }
    }

    public void insertGame(Game game) {
        long time = new Date().getTime();
        game.setLastModified(time);
        for (Investigator investigator : game.getInvList()) {
            investigator.setGameId(game.getId());
            investigator.setId(time++);
        }
        if (user != null && game.getUserID() == null) {
            game.setUserID(user.getUid());
        }
        insertGameToDB(game);
        firebaseHelper.addGame(game);
    }

    public void insertGameToDB(Game game) {
        userDataDB.gameDAO().insertGame(game);
        userDataDB.investigatorDAO().deleteByGameID(game.getId());
        userDataDB.investigatorDAO().insertInvestigatorList(game.getInvList());
    }

    public void deleteGame(Game game, boolean showToast) {
        deleteGameFromDB(game);
        removeImageFile(game.getId());
        deleteRecursiveFiles(Objects.requireNonNull(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + game.getId())));
        if (showToast) {
            Toast.makeText(context, R.string.success_deleting_message, Toast.LENGTH_SHORT).show();
        }
        firebaseHelper.removeGame(game);
    }

    private void deleteGameFromDB(Game game) {
        if (game != null) {
            userDataDB.gameDAO().deleteGame(game);
            userDataDB.investigatorDAO().deleteByGameID(game.getId());
        }
    }

    public void deleteSynchGames() {
        for (Game game : userDataDB.gameDAO().getGameListSortedDateAscending()) {
            if (game.getUserID() != null) {
                deleteGameFromDB(game);
            }
        }
        userDataDB.imageFileDAO().deleteAllImageFiles();
    }

    public PublishSubject<List<Game>> getGameListPublish() {
        return gameListPublish;
    }

    public void gameListOnNext() {
        gameListPublish.onNext(getGameList(0, 0));
    }

    // Statistics

    public int getGameCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getGameCount() : userDataDB.gameDAO().getGameCount(ancientOneId);
    }

    public int getVictoryGameCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getVictoryGameCount() : userDataDB.gameDAO().getVictoryGameCount(ancientOneId);
    }

    public int getDefeatGameCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatGameCount() : userDataDB.gameDAO().getDefeatGameCount(ancientOneId);
    }

    public int getBestScore() {
        return userDataDB.gameDAO().getBestScore();
    }

    public int getWorstScore() {
        return userDataDB.gameDAO().getWorstScore();
    }

    public List<AncientOne> getAddedAncientOneList() {
        return Localization.getInstance().isRusLocale() ? staticDataDB.ancientOneDAO().getAllRU(userDataDB.gameDAO().getAncientOneIdList()) : staticDataDB.ancientOneDAO().getAllEN(userDataDB.gameDAO().getAncientOneIdList());
    }

    public int getAncientOneCountById(int id) {
        return userDataDB.gameDAO().getAncientOneCountByID(id);
    }

    public List<Integer> getScoreList(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getScoreList() : userDataDB.gameDAO().getScoreList(ancientOneId);
    }

    public int getScoreCount(int score, int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getScoreCount(score) : userDataDB.gameDAO().getScoreCount(score, ancientOneId);
    }

    public int getDefeatByEliminationCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatByEliminationCount() : userDataDB.gameDAO().getDefeatByEliminationCount(ancientOneId);
    }

    public int getDefeatByMythosDepletionCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatByMythosDepletionCount() : userDataDB.gameDAO().getDefeatByMythosDepletionCount(ancientOneId);
    }

    public int getDefeatByAwakenedAncientOneCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatByAwakenedAncientOneCount() : userDataDB.gameDAO().getDefeatByAwakenedAncientOneCount(ancientOneId);
    }

    public int getDefeatBySurrenderCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatBySurrenderCount() : userDataDB.gameDAO().getDefeatBySurrenderCount(ancientOneId);
    }

    public int getDefeatByRumorCount(int ancientOneId) {
        return ancientOneId == 0 ? userDataDB.gameDAO().getDefeatByRumorCount() : userDataDB.gameDAO().getDefeatByRumorCount(ancientOneId);
    }

    public List<StatisticsInvestigator> getStatisticsInvestigatorList(int ancientOneId) {
        return userDataDB.investigatorDAO().getStatisticsInvestigatorList(ancientOneId == 0 ? userDataDB.gameDAO().getGameIdList() : userDataDB.gameDAO().getGameIdList(ancientOneId));
    }

    public Investigator getInvestigator(String name) {
        return staticDataDB.investigatorDAO().getByName(name);
    }

    private Game getGameById(long id) {
        return userDataDB.gameDAO().getGame(id);
    }

    public PublishSubject<Boolean> getAuthPublish() {
        return authPublish;
    }

    public void authOnNext(boolean isAuthFail) {
        authPublish.onNext(isAuthFail);
    }

    public PublishSubject<Boolean> getRatePublish() {
        return ratePublish;
    }

    public void rateOnNext() {
        if (isShowRate()) ratePublish.onNext(true);
    }

    public void setRate(boolean isRate) {
        prefHelper.saveIsRate(isRate);
        prefHelper.saveGameCountForRateDialog(getGameCount(0));
    }

    private boolean isShowRate() {
        return !prefHelper.loadIsRate() && prefHelper.loadGameCountForRateDialog() + 5 <= getGameCount(0);
    }

    // Specialization

    public List<Specialization> getSpecializationList() {
        return staticDataDB.specializationDAO().getAll();
    }

    public void saveSpecializationList(List<Specialization> list) {
        staticDataDB.specializationDAO().insertSpecializationList(list);
    }

    public Specialization getSpecialization(int id) {
        return staticDataDB.specializationDAO().getSpecializationById(id);
    }

    public int getEnabledSpecializationCount() {
        return staticDataDB.specializationDAO().getEnabledSpecializationCount();
    }

    // GamePhoto

    public List<String> getImages() {
        return fileHelper.getImages(getGame().getId());
    }

    public List<String> getImages(long id) {
        return fileHelper.getImages(id);
    }

    public PublishSubject<Boolean> getClickPhotoButtonPublish() {
        return clickPhotoButtonPublish;
    }

    public void clickPhotoButtonOnNext(boolean value) {
        clickPhotoButtonPublish.onNext(value);
    }

    public PublishSubject<Boolean> getUpdatePhotoGalleryPublish() {
        return updatePhotoGalleryPublish;
    }

    public void updatePhotoGalleryOnNext(boolean value) {
        updatePhotoGalleryPublish.onNext(value);
    }

    public PublishSubject<Boolean> getSelectModePublish() {
        return selectModePublish;
    }

    public void selectModeOnNext(boolean value) {
        selectModePublish.onNext(value);
    }

    public PublishSubject<Boolean> getSelectAllPhotoPublish() {
        return selectAllPhotoPublish;
    }

    public void deleteSelectImagesOnNext(boolean value) {
        deleteSelectImagesPublish.onNext(value);
    }

    public PublishSubject<Boolean> getDeleteSelectImagesPublish() {
        return deleteSelectImagesPublish;
    }

    public void selectAllPhotoOnNext(boolean value) {
        selectAllPhotoPublish.onNext(value);
    }

    public File getPhotoFile(String fileName) {
        return fileHelper.getImageFile(fileName, getGame().getId());
    }

    public void copyFile(File source, File dest) throws IOException {
        fileHelper.copyFile(source, dest);
    }

    public void addImageFile(ImageFile file) {
        userDataDB.imageFileDAO().insertImageFile(file);
        firebaseHelper.addFile(file);
    }

    public ImageFile getImageFile(String name) {
        return userDataDB.imageFileDAO().getImageFile(name);
    }

    public void removeImageFile(ImageFile file) {
        if (file != null && userDataDB.imageFileDAO().getImageFile(file.getName()) != null) {
            userDataDB.imageFileDAO().deleteImageFile(file);
            firebaseHelper.removeFile(file);
            firebaseHelper.deleteFileFromFirebaseStorage(file);
        }
    }

    public void deleteRecursiveFiles(File fileOrDirectory) {
        fileHelper.deleteRecursiveFiles(fileOrDirectory);
    }

    public void removeImageFile(long gameId) {
        for (ImageFile file : userDataDB.imageFileDAO().getImageFileList(gameId)) {
            removeImageFile(file);
        }
    }

    public void sendFileToStorage(Uri file, long gameId) {
        firebaseHelper.sendFileToFirebaseStorage(file, gameId);
    }

    private void getFileFromStorage(ImageFile imageFile) {
        boolean exist = false;
        for (String filePath : fileHelper.getImages(imageFile.getGameId())) {
            if (filePath.contains(imageFile.getName())) exist = true;
        }
        if (!exist)
            firebaseHelper.getFileFromFirebaseStorage(imageFile, fileHelper.getImageFile(imageFile.getName(), imageFile.getGameId()));
    }

    private void uploadFile(ImageFile file) {
        try {
            ImageFile imageFile = getImageFile(file.getName());
            imageFile.setMd5Hash(file.getMd5Hash());
            imageFile.setLastModified(new Date().getTime());
            addImageFile(imageFile);
        } catch (Exception e) {
            firebaseHelper.deleteFileFromFirebaseStorage(file);
            Timber.d(e);
        }
    }

    private void downloadFile(ImageFile imageFile) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
        if (getGame() != null && getGame().getId() == imageFile.getGameId())
            updatePhotoGalleryOnNext(true);
    }

    //Forgotten endings

    public List<Ending> getEndingList(int ancientOneId, boolean result, List<String> conditionList) {
        List<Ending> list = new ArrayList<>();
        if (result && !conditionList.isEmpty())
            list.addAll(staticDataDB.endingDAO().getEndingListWithCondition(ancientOneId, true, conditionList));
        else if (result) {
            list.addAll(staticDataDB.endingDAO().getEndingListWithoutCondition(ancientOneId, true));
        } else {
            list.addAll(staticDataDB.endingDAO().getEndingListWithCondition(ancientOneId, false, conditionList));
            list.addAll(staticDataDB.endingDAO().getEndingListWithoutCondition(ancientOneId, false));
        }
        return list;
    }

    public List<Integer> getAncientOneIdFromForgottenEndings() {
        return staticDataDB.endingDAO().getAncientOneIdList();
    }

    public List<String> getConditionList(int ancientOneId, boolean victory) {
        return staticDataDB.endingDAO().getConditionList(ancientOneId, victory);
    }

    //Faq

    public List<Article> getArticleList() {
        return Localization.getInstance().isRusLocale() ? articleListRu : articleListEn;
    }

    public void setArticleList(List<Article> articleList) {
        if (Localization.getInstance().isRusLocale()) {
            articleListRu = new ArrayList<>();
            articleListRu.addAll(articleList);
        } else {
            articleListEn = new ArrayList<>();
            articleListEn.addAll(articleList);
        }
    }
}