package ru.mgusev.eldritchhorror.app;

import android.app.Application;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

public class App extends Application{

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();

        if (!MainActivity.initialized) {
//            Intent firstIntent = new Intent(this, MainActivity.class);
//            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
//            startActivity(firstIntent);


        }
    }

    public static AppComponent getComponent() {
        return component;
    }
}