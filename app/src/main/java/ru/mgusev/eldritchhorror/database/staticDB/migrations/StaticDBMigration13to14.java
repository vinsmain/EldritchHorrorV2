package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class StaticDBMigration13to14 {

    public static final Migration MIGRATION_13_14 = new Migration(13, 14) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Незапамятный' WHERE _id = 16;");

            database.execSQL("UPDATE investigators SET occupation_ru = 'Беглец' WHERE _id = 50;");

            database.execSQL("UPDATE preludes SET name_ru = 'Помощь Старших богов' WHERE _id = 31;");
            database.execSQL("UPDATE preludes SET name_ru = 'Отец змец' WHERE _id = 34;");
            database.execSQL("UPDATE preludes SET name_ru = 'Посланник Внешних богов' WHERE _id = 35;");
            database.execSQL("UPDATE preludes SET name_ru = 'В тёмной-тёмной комнате' WHERE _id = 36;");
            database.execSQL("UPDATE preludes SET name_ru = 'Звёзды заняли свои места' WHERE _id = 37;");
            database.execSQL("UPDATE preludes SET name_ru = 'Соблазн' WHERE _id = 38;");
            database.execSQL("UPDATE preludes SET name_ru = 'В пролом' WHERE _id = 39;");
            database.execSQL("UPDATE preludes SET name_ru = 'Занятные диковинки' WHERE _id = 40;");
        }
    };
}