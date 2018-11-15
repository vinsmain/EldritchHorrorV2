package ru.mgusev.eldritchhorror.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import ru.mgusev.eldritchhorror.auth.GoogleAuth;
import ru.mgusev.eldritchhorror.repository.FirebaseHelper;
import ru.mgusev.eldritchhorror.database.staticDB.StaticDataDB;
import ru.mgusev.eldritchhorror.database.userDB.UserDataDB;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Specialization;
import ru.mgusev.eldritchhorror.repository.FileHelper;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.repository.PrefHelper;

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
    public Repository provideRepository(Context context, StaticDataDB staticDataDB, UserDataDB userDataDB, PrefHelper prefHelper, FileHelper fileHelper, FirebaseHelper firebaseHelper){
        return new Repository(context, staticDataDB, userDataDB, prefHelper, fileHelper, firebaseHelper);
    }

    @Provides
    @Singleton
    public StaticDataDB provideStaticDataDB(Context context) {
        return Room.databaseBuilder(context, StaticDataDB.class, "StaticDataDB.db")
                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .addMigrations(ru.mgusev.eldritchhorror.database.staticDB.Migrations.MIGRATION_1_2)
                .addMigrations(ru.mgusev.eldritchhorror.database.staticDB.Migrations.MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public UserDataDB provideUserDataDB(Context context) {
        return Room.databaseBuilder(context, UserDataDB.class, "UserDataDB.db")
                .addMigrations(ru.mgusev.eldritchhorror.database.userDB.Migrations.MIGRATION_1_2)
                .addMigrations(ru.mgusev.eldritchhorror.database.userDB.Migrations.MIGRATION_2_3)
                .addMigrations(ru.mgusev.eldritchhorror.database.userDB.Migrations.MIGRATION_3_4)
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
    public List<Specialization> provideSpecializationList(Repository repository) {
        return repository.getSpecializationList();
    }

    @Provides
    public List<AncientOne> provideAncientOneList(Repository repository) {
        return repository.getAncientOneList();
    }

    @Provides
    @Singleton
    public PrefHelper providePrefHelper(Context context) {
        return new PrefHelper(context);
    }

    @Provides
    @Singleton
    public FileHelper provideFileHelper(Context context) {
        return new FileHelper(context);
    }

    @Provides
    @Singleton
    public GoogleAuth provideGoogleAuth(Context context) {
        return new GoogleAuth(context);
    }

    @Provides
    @Singleton
    public FirebaseHelper provideFirebaseHelper() {
        return new FirebaseHelper();
    }
}