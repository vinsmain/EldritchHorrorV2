package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class StaticDBMigration10to11 {

    public static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Шудде-Мьелл' WHERE _id = 13;");

            database.execSQL("UPDATE investigators SET occupation_ru = 'Коммивояжёр' WHERE _id = 38;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Федерал' WHERE _id = 40;");
            database.execSQL("UPDATE investigators SET name_ru = 'Пит Мусорщик' WHERE _id = 37;");

            database.execSQL("UPDATE expansions SET name_ru = 'Разрушенные города' WHERE _id = 8;");

            database.execSQL("UPDATE preludes SET name_ru = 'Апокалипсис грядёт' WHERE _id = 27;");
            database.execSQL("UPDATE preludes SET name_ru = 'Конец человечества' WHERE _id = 28;");
            database.execSQL("UPDATE preludes SET name_ru = 'Цена известности' WHERE _id = 29;");
            database.execSQL("UPDATE preludes SET name_ru = 'Вы знаете, что должны сделать' WHERE _id = 30;");
        }
    };
}