package ru.mgusev.eldritchhorror.auth;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

public class GoogleAuth {

    private static final String TAG = "GoogleActivity";

    @Inject
    Repository repository;

    private Context context;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseUser currentUser;

    public GoogleAuth(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        configGoogleSignIn();
        googleSignInClient = GoogleSignIn.getClient(context, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void configGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Timber.tag(TAG).d("signInWithCredential:success");
                currentUser = mAuth.getCurrentUser();
                repository.setUser(currentUser);
                repository.initFirebaseHelper();
                repository.authOnNext(true);
                //repository.userIconOnNext(currentUser.getPhotoUrl());
            } else {
                // If sign in fails, display a message to the user.
                Timber.tag(TAG).w(task.getException(), "signInWithCredential:failure");
                repository.authOnNext(false);
            }
        });
    }

    public void signOut() {
        repository.firebaseSubscribeDispose();
        currentUser = null;
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(
            task -> {
                Timber.tag(TAG).d("signInWithCredential:sign out");
                repository.authOnNext(false);
                repository.deleteSynchGames();
                repository.gameListOnNext();
                repository.setUser(currentUser);
            });
    }
}