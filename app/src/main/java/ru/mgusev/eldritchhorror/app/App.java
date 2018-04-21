package ru.mgusev.eldritchhorror.app;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import ru.mgusev.eldritchhorror.database.HelpGactory;
import ru.mgusev.eldritchhorror.database.HelperFactory;
import ru.mgusev.eldritchhorror.database.LocalDBAssetHelper;
import ru.mgusev.eldritchhorror.database.StaticDB;

public class App extends Application{

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();

        new LocalDBAssetHelper(this.getApplicationContext()).getWritableDatabase();
        StaticDB db =  Room.databaseBuilder(this.getApplicationContext(),
                StaticDB.class, "EHLocalDB.db")
                .allowMainThreadQueries()
                .build();

        System.out.println(db.ancientDAO().getAll().size());

    }

    public static AppComponent getComponent() {
        return component;
    }
}