package ru.mgusev.eldritchhorror.app;

import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import ru.mgusev.eldritchhorror.database.HelperFactory;
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
    public Repository provideRepository(Context context, HelperFactory helperFactory){
        return new Repository(context, helperFactory);
    }

    @Provides
    @Singleton
    public HelperFactory provideHelperFactory(Context context) {
        return new HelperFactory(context);
    }
}