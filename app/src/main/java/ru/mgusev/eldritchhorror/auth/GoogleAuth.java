package ru.mgusev.eldritchhorror.auth;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ru.mgusev.eldritchhorror.R;

public class GoogleAuth {

    private static final String TAG = "GoogleActivity";

    private Context context;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseUser currentUser;

    public GoogleAuth(Context context) {
        this.context = context;
        configGoogleSignIn();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void configGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success");
                currentUser = mAuth.getCurrentUser();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.getException());
            }
        });
    }

    public void signOut() {
        currentUser = null;
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(
            task -> {
                Log.d(TAG, "signInWithCredential:sign out");
                //updateUI(null);
            });
    }
}