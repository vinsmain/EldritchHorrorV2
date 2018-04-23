package ru.mgusev.eldritchhorror.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import ru.mgusev.eldritchhorror.database.StaticDataDB;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.repository.Repository;

@Module
public class AppModule {

    private Context context;

    AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public Repository provideRepository(Context context, StaticDataDB staticDataDB, Game game){
        return new Repository(context, staticDataDB, game);
    }

    @Provides
    @Singleton
    public StaticDataDB provideStaticDataDB(Context context) {
        return Room.databaseBuilder(context,
                StaticDataDB.class, "EHLocalDB.db")
                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    public Game provideGame() {
        return new Game();
    }
}