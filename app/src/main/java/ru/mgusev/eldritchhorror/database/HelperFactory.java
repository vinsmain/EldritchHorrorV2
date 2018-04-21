package ru.mgusev.eldritchhorror.database;

import android.content.Context;


import javax.inject.Inject;

public class HelperFactory {
    private DatabaseHelper databaseHelper;
    private DatabaseLocalHelper databaseLocalHelper;

    @Inject
    public HelperFactory(Context context) {
        databaseHelper = DatabaseHelper.getHelper(context);
        databaseLocalHelper = DatabaseLocalHelper.getHelper(context);
    }

    public DatabaseHelper getHelper(){
        return databaseHelper;
    }

    public DatabaseLocalHelper getStaticHelper(){
        return databaseLocalHelper;
    }
}