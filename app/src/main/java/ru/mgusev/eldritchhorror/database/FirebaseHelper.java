package ru.mgusev.eldritchhorror.database;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import durdinapps.rxfirebase2.RxFirebaseChildEvent;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Flowable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;

public class FirebaseHelper {

    private static FirebaseDatabase mDatabase;
    private static DatabaseReference reference;

    private Flowable<RxFirebaseChildEvent<Game>> childEventDisposable;

    public FirebaseHelper() {
        App.getComponent().inject(this);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
    }

    public void initReference(FirebaseUser user) {
        reference = mDatabase.getReference().child("users").child(user.getUid()).child("games");
        childEventDisposable = RxFirebaseDatabase.observeChildEvent(reference, Game.class);
    }

    public Flowable<RxFirebaseChildEvent<Game>> getChildEventDisposable() {
        return childEventDisposable;
    }

    public boolean addGame(Game game) {
        if (reference != null) {
            reference.child(String.valueOf(game.getId())).setValue(game);
            return true;
        }
        return false;
    }

    public void removeGame(Game game) throws NullPointerException {
        if (reference != null) reference.child(String.valueOf(game.getId())).removeValue();
    }
}