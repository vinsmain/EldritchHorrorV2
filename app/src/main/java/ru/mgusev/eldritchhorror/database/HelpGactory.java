package ru.mgusev.eldritchhorror.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.content.Context;

public class HelpGactory implements SupportSQLiteOpenHelper.Factory {

    private LocalDBAssetHelper helper;
    private Context context;

    public HelpGactory(Context context) {
        this.context = context;
        helper = new LocalDBAssetHelper(context);
    }

    public LocalDBAssetHelper getHelper() {
        return helper;
    }

    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return (SupportSQLiteOpenHelper) helper;
    }
}
