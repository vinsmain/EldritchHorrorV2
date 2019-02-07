package ru.mgusev.eldritchhorror.repository;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.RxFirebaseChildEvent;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.ImageFile;
import timber.log.Timber;

public class FirebaseHelper {

    private static FirebaseDatabase mDatabase;
    private static FirebaseStorage storage;
    private static DatabaseReference reference;
    private static DatabaseReference fileReference;
    private static StorageReference storageReference;

    private Flowable<List<RxFirebaseChildEvent<Game>>> childEventDisposable;
    private Flowable<RxFirebaseChildEvent<ImageFile>> fileEventDisposable;
    private PublishSubject<ImageFile> downloadFileDisposable;
    private PublishSubject<ImageFile> successUploadFilePublish;

    public FirebaseHelper() {
        App.getComponent().inject(this);
        mDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        mDatabase.setPersistenceEnabled(true);
        successUploadFilePublish = PublishSubject.create();
        downloadFileDisposable = PublishSubject.create();
    }

    public void initReference(FirebaseUser user) {
        reference = mDatabase.getReference().child("users").child(user.getUid()).child("games");
        childEventDisposable = RxFirebaseDatabase.observeChildEvent(reference, Game.class).subscribeOn(Schedulers.io()).buffer(250, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());

        fileReference = mDatabase.getReference().child("users").child(user.getUid()).child("files");
        fileEventDisposable = RxFirebaseDatabase.observeChildEvent(fileReference, ImageFile.class);

        storageReference = storage.getReference().child("users").child(user.getUid()).child("games");
    }

    public Flowable<List<RxFirebaseChildEvent<Game>>> getChildEventDisposable() {
        return childEventDisposable;
    }

    public Flowable<RxFirebaseChildEvent<ImageFile>> getFileEventDisposable() {
        return fileEventDisposable;
    }

    public void addGame(Game game) {
        if (reference != null) {
            reference.child(String.valueOf(game.getId())).setValue(game);
        }
    }

    public void removeGame(Game game) throws NullPointerException {
        if (reference != null) reference.child(String.valueOf(game.getId())).removeValue();
    }

    public void addFile(ImageFile file) {
        if (fileReference != null) {
            fileReference.child(String.valueOf(file.getId())).setValue(file);
        }
    }

    public void removeFile(ImageFile file) throws NullPointerException {
        if (fileReference != null) fileReference.child(String.valueOf(file.getId())).removeValue();
    }

    public void sendFileToFirebaseStorage(Uri file, long gameId) {
        if (fileReference != null) {
            StorageReference sendReference = storageReference.child(String.valueOf(gameId)).child(file.getLastPathSegment());
            UploadTask uploadTask = sendReference.putFile(file);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(exception -> {
                Timber.tag("FIRESTORAGE UPLOAD").d("FAIL %s", exception.getLocalizedMessage());
            }).addOnSuccessListener(taskSnapshot -> {
                ImageFile imageFile = new ImageFile();
                imageFile.setName(Objects.requireNonNull(taskSnapshot.getMetadata()).getName());
                imageFile.setGameId(gameId);
                imageFile.setMd5Hash(taskSnapshot.getMetadata().getMd5Hash());
                successUploadFilePublish.onNext(imageFile);
                Timber.tag("FIRESTORAGE UPLOAD").d(Objects.requireNonNull(taskSnapshot.getMetadata()).getMd5Hash());
            });
        }
    }

    public void getFileFromFirebaseStorage(ImageFile imageFile, File localFile) {
        if (fileReference != null) {
            StorageReference fileReference = storageReference.child(String.valueOf(imageFile.getGameId())).child(imageFile.getName());

            RxFirebaseStorage.getFile(fileReference, localFile)
                    .subscribe(taskSnapshot -> {
                        downloadFileDisposable.onNext(imageFile);
                        Timber.tag("RxFirebaseSample").i(String.format("transferred: %d, bytes %s", taskSnapshot.getBytesTransferred(), localFile.getAbsolutePath()));
                    }, throwable -> {
                        Timber.tag("RxFirebaseSample").e(throwable.toString());
                    });
        }
    }

    public void deleteFileFromFirebaseStorage(ImageFile file) {
        if (fileReference != null) {
            StorageReference deleteReference = storageReference.child(String.valueOf(file.getGameId())).child(file.getName());

            deleteReference.delete().addOnSuccessListener(aVoid -> {
                Timber.tag("FIRESTORAGE DELETE").d("SUCCESS");
            }).addOnFailureListener(exception -> {
                Timber.tag("FIRESTORAGE UPLOAD").d("FAIL %s", exception.getLocalizedMessage());
            });
        }
    }

    public PublishSubject<ImageFile> getSuccessUploadFilePublish() {
        return successUploadFilePublish;
    }

    public PublishSubject<ImageFile> getDownloadFileDisposable() {
        return downloadFileDisposable;
    }

    public void signOut() {
        reference = null;
        fileReference = null;
        storageReference = null;
    }
}