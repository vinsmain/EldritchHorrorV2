package ru.mgusev.eldritchhorror.database.staticDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE expansions SET image_resource = null WHERE _id = 1;");
        }
    };
}
