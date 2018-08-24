package ru.mgusev.eldritchhorror.database;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseChildEvent;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Flowable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.ImageFile;
import ru.mgusev.eldritchhorror.model.ImageFileList;

public class FirebaseHelper {

    private static FirebaseDatabase mDatabase;
    private static FirebaseStorage storage;
    private static DatabaseReference reference;
    private static DatabaseReference fileReference;
    private static StorageReference storageReference;

    private Flowable<RxFirebaseChildEvent<Game>> childEventDisposable;
    private Flowable<RxFirebaseChildEvent<ImageFile>> fileEventDisposable;

    public FirebaseHelper() {
        App.getComponent().inject(this);
        mDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        mDatabase.setPersistenceEnabled(true);
    }

    public void initReference(FirebaseUser user) {
        reference = mDatabase.getReference().child("users").child(user.getUid()).child("games");
        childEventDisposable = RxFirebaseDatabase.observeChildEvent(reference, Game.class);

        fileReference = mDatabase.getReference().child("users").child(user.getUid()).child("files");
        fileEventDisposable = RxFirebaseDatabase.observeChildEvent(fileReference, ImageFile.class);

        storageReference = storage.getReference().child("users").child(user.getUid()).child("games");
    }

    public Flowable<RxFirebaseChildEvent<Game>> getChildEventDisposable() {
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
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d("FIRESTORE", "SUCCESS");
                }
            });
        }
    }

    public void getFileFromFirebaseStorage(ImageFile imageFile, File localFile) {
        if (fileReference != null) {
            StorageReference fileReference = storageReference.child(String.valueOf(imageFile.getGameId())).child(imageFile.getName());

            RxFirebaseStorage.getFile(fileReference, localFile)
                    .subscribe(taskSnapshot -> {
                        Log.i("RxFirebaseSample", "transferred: " + taskSnapshot.getBytesTransferred() + " bytes " + localFile.getAbsolutePath());
                    }, throwable -> {
                        Log.e("RxFirebaseSample", throwable.toString());
                    });
        }
    }

    public void deleteFileFromFirebaseStorage(ImageFile file) {
        if (fileReference != null) {
            StorageReference deleteReference = storageReference.child(String.valueOf(file.getGameId())).child(file.getName());

            deleteReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });
        }
    }
}