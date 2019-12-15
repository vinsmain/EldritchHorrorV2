package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class StaticDBMigration12to13 {

    public static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `preludes` ADD COLUMN `defeat` INTEGER NOT NULL DEFAULT 0;");

            database.execSQL("UPDATE preludes SET defeat = 1 WHERE _id = 23;");
        }
    };
}