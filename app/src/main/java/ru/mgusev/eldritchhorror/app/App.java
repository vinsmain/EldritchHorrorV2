package ru.mgusev.eldritchhorror.app;

import android.app.Application;

public class App extends Application{

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}