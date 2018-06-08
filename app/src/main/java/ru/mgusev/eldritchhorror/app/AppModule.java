package ru.mgusev.eldritchhorror.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import ru.mgusev.eldritchhorror.database.staticDB.StaticDataDB;
import ru.mgusev.eldritchhorror.database.userDB.UserDataDB;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
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
    public Repository provideRepository(Context context, StaticDataDB staticDataDB, UserDataDB userDataDB){
        return new Repository(context, staticDataDB, userDataDB);
    }

    @Provides
    @Singleton
    public StaticDataDB provideStaticDataDB(Context context) {
        return Room.databaseBuilder(context, StaticDataDB.class, "StaticDataDB.db")
                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                //.addMigrations(Migrations.MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public UserDataDB provideUserDataDB(Context context) {
        return Room.databaseBuilder(context, UserDataDB.class, "UserDataDB.db")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    public Game provideGame() {
        return new Game();
    }

    @Provides
    public List<Expansion> provideExpansionList(Repository repository) {
        return repository.getExpansionList();
    }

    @Provides
    public List<AncientOne> provideAncientOneList(Repository repository) {
        return repository.getAncientOneList();
    }
}