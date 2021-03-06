package ru.mgusev.eldritchhorror.di.module;

import androidx.room.Room;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import ru.mgusev.eldritchhorror.api.EHAPIService;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration15to16;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration16to17;
import ru.mgusev.eldritchhorror.utils.auth.GoogleAuth;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration6to7;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration7to8;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration8to9;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration9to10;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration10to11;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration11to12;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration12to13;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration13to14;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigration14to15;
import ru.mgusev.eldritchhorror.database.staticDB.migrations.StaticDBMigrations;
import ru.mgusev.eldritchhorror.database.userDB.Migrations;
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
import ru.mgusev.eldritchhorror.utils.StatsIcons;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
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
                .addMigrations(StaticDBMigrations.MIGRATION_1_2)
                .addMigrations(StaticDBMigrations.MIGRATION_2_3)
                .addMigrations(StaticDBMigrations.MIGRATION_3_4)
                .addMigrations(StaticDBMigrations.MIGRATION_4_5)
                .addMigrations(StaticDBMigrations.MIGRATION_5_6)
                .addMigrations(StaticDBMigration6to7.MIGRATION_6_7)
                .addMigrations(StaticDBMigration7to8.MIGRATION_7_8)
                .addMigrations(StaticDBMigration8to9.MIGRATION_8_9)
                .addMigrations(StaticDBMigration9to10.MIGRATION_9_10)
                .addMigrations(StaticDBMigration10to11.MIGRATION_10_11)
                .addMigrations(StaticDBMigration11to12.MIGRATION_11_12)
                .addMigrations(StaticDBMigration12to13.MIGRATION_12_13)
                .addMigrations(StaticDBMigration13to14.MIGRATION_13_14)
                .addMigrations(StaticDBMigration14to15.MIGRATION_14_15)
                .addMigrations(StaticDBMigration15to16.MIGRATION_15_16)
                .addMigrations(StaticDBMigration16to17.MIGRATION_16_17)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public UserDataDB provideUserDataDB(Context context) {
        return Room.databaseBuilder(context, UserDataDB.class, "UserDataDB.db")
                .addMigrations(Migrations.MIGRATION_1_2)
                .addMigrations(Migrations.MIGRATION_2_3)
                .addMigrations(Migrations.MIGRATION_3_4)
                .addMigrations(Migrations.MIGRATION_4_5)
                .addMigrations(Migrations.MIGRATION_5_6)
                .addMigrations(Migrations.MIGRATION_6_7)
                .addMigrations(Migrations.MIGRATION_7_8)
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

    @Provides
    @Singleton
    public EHAPIService provideFaqAPIService(Context context) {
        return new EHAPIService(context);
    }

    @Provides
    @Singleton
    public StatsIcons provideStatsIcons(Context context) {
        return new StatsIcons(context);
    }
}