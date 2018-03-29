package ru.mgusev.eldritchhorror.presentation;

import android.app.Application;
import android.content.Context;

import ru.mgusev.eldritchhorror.database.HelperFactory;

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        HelperFactory.setHelper(getApplicationContext());
        HelperFactory.setStaticHelper(getApplicationContext());
    }

    public static Context getContext(){
        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HelperFactory.releaseHelper();
    }
}
