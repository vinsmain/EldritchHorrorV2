package ru.mgusev.eldritchhorror.database.userDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `files` (`_id` INTEGER NOT NULL, `game_id` INTEGER NOT NULL, `name` TEXT, `comment` TEXT, `user_id` TEXT, `last_modified` INTEGER NOT NULL, `md5_hash` TEXT, PRIMARY KEY(`_id`))");
        }
    };
}
