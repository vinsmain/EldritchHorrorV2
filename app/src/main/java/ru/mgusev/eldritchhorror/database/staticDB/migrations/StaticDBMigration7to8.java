package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class StaticDBMigration7to8 {

    public static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE expansions SET name_ru = 'Таинственные руины' WHERE _id = 4;");
            database.execSQL("UPDATE expansions SET name_ru = 'Фанатские прелюдии\nавтор Michael Williams', name_en = 'Fan preludes\nby Michael Williams' WHERE _id = 10;");

            database.execSQL("UPDATE preludes SET name_ru = 'Темное благословение' WHERE _id = 17;");
            database.execSQL("UPDATE preludes SET name_ru = 'Приближается буря' WHERE _id = 18;");
            database.execSQL("UPDATE preludes SET name_ru = 'Данвичский ужас' WHERE _id = 19;");
            database.execSQL("UPDATE preludes SET name_ru = 'Парад светил' WHERE _id = 20;");

            database.execSQL("UPDATE investigators SET occupation_ru = 'Певица' WHERE _id = 45;");
            database.execSQL("UPDATE investigators SET name_ru = 'Скат О''Тул' WHERE _id = 46;");
            database.execSQL("UPDATE investigators SET name_ru = 'Зои Самарас', occupation_ru = 'Повар' WHERE _id = 48;");

            database.execSQL("UPDATE rumors SET name_ru = 'Глаз бури' WHERE _id = 3;");
            database.execSQL("UPDATE rumors SET name_ru = 'Далёкие уголки Земли' WHERE _id = 4;");
        }
    };
}