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

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `games` ADD COLUMN `parent_id` INTEGER NOT NULL DEFAULT 0;");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `games` ADD COLUMN `comment` TEXT;");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `games` ADD COLUMN `defeat_by_rumor` INTEGER NOT NULL DEFAULT 0;");
            database.execSQL("ALTER TABLE `games` ADD COLUMN `defeat_rumor_id` INTEGER NOT NULL DEFAULT 2;");
        }
    };

    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `games` ADD COLUMN `defeat_by_surrender` INTEGER NOT NULL DEFAULT 0;");
        }
    };

    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `games` ADD COLUMN `time` INTEGER NOT NULL DEFAULT 0;");
        }
    };
}