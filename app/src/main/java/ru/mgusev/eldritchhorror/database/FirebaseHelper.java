package ru.mgusev.eldritchhorror.database;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.repository.Repository;

public class FirebaseHelper {

    @Inject
    Repository repository;
    private static FirebaseHelper helper;
    private static FirebaseDatabase mDatabase;
    private static DatabaseReference reference;

    public static FirebaseHelper getInstance() {
        if (helper == null) {
            helper = new FirebaseHelper();
        }
        initDatabase();
        return helper;
    }

    private FirebaseHelper() {
        App.getComponent().inject(this);
    }

    private static void initDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
    }

    public void getReference(FirebaseUser user) {
        reference = mDatabase.getReference().child("users").child(user.getUid()).child("games");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Game addingGame = dataSnapshot.getValue(Game.class);
                changeGame(addingGame);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Game changingGame = dataSnapshot.getValue(Game.class);
                changeGame(changingGame);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                repository.deleteGame(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void changeGame(Game game) {
        if (game.getLastModified() != repository.getGameById(game.getId()).getLastModified()) {
            repository.insertGame(game);
        }
    }

    public static void addGame(Game game) {
        if (reference != null) reference.child(String.valueOf(game.getId())).setValue(game);
    }

    public static void removeGame(Game game) throws NullPointerException {
        if (reference != null) reference.child(String.valueOf(game.getId())).removeValue();
    }
}