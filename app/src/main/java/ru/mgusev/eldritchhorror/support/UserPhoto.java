package ru.mgusev.eldritchhorror.support;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import com.google.firebase.auth.FirebaseUser;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import dagger.Module;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.app.AppModule;
import ru.mgusev.eldritchhorror.repository.Repository;

public class UserPhoto {

    private static Drawable drawablePhoto;
    @Inject
    Repository repository;

    public UserPhoto() {
        App.getComponent().inject(this);
    }

    public Drawable getPhoto(FirebaseUser user) {
        if (drawablePhoto == null) {
            new AsyncTask<String, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        InputStream in = url.openStream();
                        return BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) drawablePhoto = new BitmapDrawable(Resources.getSystem(), BitmapCircle.getCircleBitmap(bitmap, 96));
                    else drawablePhoto = repository.getContext().getResources().getDrawable(R.drawable.google_signed_in);

                    System.out.println("REPO " + repository);
                    System.out.println("ICON " + drawablePhoto);
                    repository.userIconOnNext(drawablePhoto);
                }
            }.execute(user.getPhotoUrl().toString());
        }
        System.out.println("PHOTO " + drawablePhoto);
        return drawablePhoto;
    }

    public void clearPhoto() {
        drawablePhoto = null;
        repository.userIconOnNext(repository.getContext().getResources().getDrawable(R.drawable.google_icon));
    }
}