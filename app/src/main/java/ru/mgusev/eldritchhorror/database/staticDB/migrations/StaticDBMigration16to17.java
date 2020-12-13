package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class StaticDBMigration16to17 {

    public static final Migration MIGRATION_16_17 = new Migration(16, 17) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO expansions (is_enable, name_ru, name_en, image_resource, _id) VALUES (0, 'Дети Юггота', 'Children of Yuggoth', 'children_of_yuggoth', 11);");

            database.execSQL("INSERT INTO ancient_ones (\n" +
                    "                             max_mysteries,\n" +
                    "                             expansion_id,\n" +
                    "                             name_ru,\n" +
                    "                             name_en,\n" +
                    "                             image_resource,\n" +
                    "                             _id\n" +
                    "                         )\n" +
                    "                         VALUES (\n" +
                    "                             3,\n" +
                    "                             11,\n" +
                    "                             'Ми-го',\n" +
                    "                             'Mi-go',\n" +
                    "                             'mi_go',\n" +
                    "                             17\n" +
                    "                         );\n");

            database.execSQL("INSERT INTO cards (\n" +
                    "                      type_id,\n" +
                    "                      name_resource_id,\n" +
                    "                      _id\n" +
                    "                  )\n" +
                    "                  VALUES (\n" +
                    "                      10,\n" +
                    "                      'abducted',\n" +
                    "                      245\n" +
                    "                  );\n");

            database.execSQL("INSERT INTO cards_expansions (\n" +
                    "                                 card_count,\n" +
                    "                                 expansion_id,\n" +
                    "                                 card_id\n" +
                    "                             )\n" +
                    "                             VALUES (\n" +
                    "                                 16,\n" +
                    "                                 11,\n" +
                    "                                 245\n" +
                    "                             );\n");
        }
    };
}
