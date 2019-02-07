package ru.mgusev.eldritchhorror.app;

import android.app.Application;

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
}