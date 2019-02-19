package ru.mgusev.eldritchhorror.app;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.log.LogDebugTree;
import timber.log.Timber;

public class App extends Application{

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new LogDebugTree());
        }

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static Uri resourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }
}