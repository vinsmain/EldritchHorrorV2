package ru.mgusev.eldritchhorror.app;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends Application{

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}