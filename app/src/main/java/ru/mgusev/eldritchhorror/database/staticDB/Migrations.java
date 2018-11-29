package ru.mgusev.eldritchhorror.database.staticDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE preludes SET name_ru = 'Слухи с севера' WHERE _id = 1;");
            database.execSQL("UPDATE preludes SET name_ru = 'Ключ к спасению' WHERE _id = 2;");
            database.execSQL("UPDATE preludes SET name_ru = 'Непокорная жертва' WHERE _id = 3;");
            database.execSQL("UPDATE preludes SET name_ru = 'Начало конца' WHERE _id = 4;");
            database.execSQL("UPDATE preludes SET name_ru = 'Пророк из Антарктиды' WHERE _id = 5;");
            database.execSQL("UPDATE preludes SET name_ru = 'Последняя жертва' WHERE _id = 6;");
            database.execSQL("UPDATE preludes SET name_ru = 'Призрак из прошлого' WHERE _id = 7;");
            database.execSQL("UPDATE preludes SET name_ru = 'Решительные меры' WHERE _id = 8;");
            database.execSQL("UPDATE preludes SET name_ru = 'Зов Ктулху' WHERE _id = 9;");
            database.execSQL("UPDATE preludes SET name_ru = 'Литания секретов' WHERE _id = 10;");
            database.execSQL("UPDATE preludes SET name_ru = 'Эпидемия' WHERE _id = 11;");
            database.execSQL("UPDATE preludes SET name_ru = 'Под пирамидами' WHERE _id = 12;");
            database.execSQL("UPDATE preludes SET name_ru = 'Претворить слабость в силу' WHERE _id = 13;");
            database.execSQL("UPDATE preludes SET name_ru = 'Грехи прошлого' WHERE _id = 14;");
            database.execSQL("UPDATE preludes SET name_ru = 'Сокровища Серебряных Сумерек' WHERE _id = 15;");
            database.execSQL("UPDATE preludes SET name_ru = 'Король в желтом' WHERE _id = 16;");
            database.execSQL("UPDATE preludes SET name_ru = 'Темные благословения' WHERE _id = 17;");
            database.execSQL("UPDATE preludes SET name_ru = 'Надвигающийся шторм' WHERE _id = 18;");
            database.execSQL("UPDATE preludes SET name_ru = 'Ужас Данвича' WHERE _id = 19;");
            database.execSQL("UPDATE preludes SET name_ru = 'Космическое выравнивание' WHERE _id = 20;");
            database.execSQL("UPDATE preludes SET name_ru = 'Написано в звездах' WHERE _id = 21;");
            database.execSQL("UPDATE preludes SET name_ru = 'Таящийся среди нас' WHERE _id = 22;");
            database.execSQL("UPDATE preludes SET name_ru = 'Двойные богохульства Черной Козы' WHERE _id = 23;");
            database.execSQL("UPDATE preludes SET name_ru = 'Паутина между мирами' WHERE _id = 24;");
            database.execSQL("UPDATE preludes SET name_ru = 'Потусторонние мечты' WHERE _id = 25;");
            database.execSQL("UPDATE preludes SET name_ru = 'Целенаправленное обучение' WHERE _id = 26;");
            database.execSQL("UPDATE preludes SET name_ru = 'Апокалипсис близок' WHERE _id = 27;");
            database.execSQL("UPDATE preludes SET name_ru = 'Падение человека' WHERE _id = 28;");
            database.execSQL("UPDATE preludes SET name_ru = 'Цена престижа' WHERE _id = 29;");
            database.execSQL("UPDATE preludes SET name_ru = 'Вы знаете, что вы должны сделать' WHERE _id = 30;");
            database.execSQL("UPDATE preludes SET name_ru = 'Помощь старших богов' WHERE _id = 31;");
            database.execSQL("UPDATE preludes SET name_ru = 'Архивы' WHERE _id = 32;");
            database.execSQL("UPDATE preludes SET name_ru = 'Армия тьмы' WHERE _id = 33;");
            database.execSQL("UPDATE preludes SET name_ru = 'Отец Змей' WHERE _id = 34;");
            database.execSQL("UPDATE preludes SET name_ru = 'Предвестник внешних богов' WHERE _id = 35;");
            database.execSQL("UPDATE preludes SET name_ru = 'В камере без света' WHERE _id = 36;");
            database.execSQL("UPDATE preludes SET name_ru = 'Звезды выстраиваются в ряд' WHERE _id = 37;");
            database.execSQL("UPDATE preludes SET name_ru = 'Искушение' WHERE _id = 38;");
            database.execSQL("UPDATE preludes SET name_ru = 'К разрыву' WHERE _id = 39;");
            database.execSQL("UPDATE preludes SET name_ru = 'Дивное любопытство' WHERE _id = 40;");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE expansions SET name_ru = 'Мир грёз' WHERE _id = 7;");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE preludes SET name_ru = 'Предначертано звездами' WHERE _id = 21;");
            database.execSQL("UPDATE preludes SET name_ru = 'Шпион среди нас' WHERE _id = 22;");
            database.execSQL("UPDATE preludes SET name_ru = 'Нечестивые близнецы Черной Козы' WHERE _id = 23;");
            database.execSQL("UPDATE preludes SET name_ru = 'Сны об Ином мире' WHERE _id = 25;");
            database.execSQL("UPDATE preludes SET name_ru = 'Интенсивное обучение' WHERE _id = 26;");

            database.execSQL("UPDATE ancient_ones SET name_ru = 'Йог-Сотот' WHERE _id = 4;");
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Шуб-Ниггурат' WHERE _id = 7;");
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Возрождение Старцев' WHERE _id = 8;");
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Итакуа' WHERE _id = 9;");
            database.execSQL("UPDATE ancient_ones SET name_ru = 'Гипнос' WHERE _id = 12;");

            database.execSQL("UPDATE investigators SET name_ru = 'Агнес Бейкер' WHERE _id = 21;");
            database.execSQL("UPDATE investigators SET name_ru = 'Дейзи Уокер' WHERE _id = 23;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Законник' WHERE _id = 24;");
            database.execSQL("UPDATE investigators SET name_ru = 'Патрис Хатауэй' WHERE _id = 25;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Юный полицейский' WHERE _id = 26;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Умелец' WHERE _id = 28;");
            database.execSQL("UPDATE investigators SET name_ru = 'Даррелл Симмонс' WHERE _id = 31;");
            database.execSQL("UPDATE investigators SET name_ru = 'Глория Гольдберг', occupation_ru = 'Писательница' WHERE _id = 32;");
            database.execSQL("UPDATE investigators SET name_ru = 'Кейт Уинтроп' WHERE _id = 33;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Сновидец' WHERE _id = 34;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Доктор' WHERE _id = 35;");
            database.execSQL("UPDATE investigators SET name_ru = 'Декстер Дрейк', occupation_ru = 'Иллюзионист' WHERE _id = 41;");
            database.execSQL("UPDATE investigators SET occupation_ru = 'Дилетантка' WHERE _id = 42;");
            database.execSQL("UPDATE investigators SET name_ru = 'Майкл Макглен' WHERE _id = 43;");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `rumors` (`_id` INTEGER NOT NULL, `name_en` TEXT, `name_ru` TEXT, `expansion_id` INTEGER NOT NULL)");
            database.execSQL("INSERT INTO rumors (\n" +
                    "                       expansion_id,\n" +
                    "                       name_ru,\n" +
                    "                       name_en,\n" +
                    "                       _id\n" +
                    "                   )\n" +
                    "                   VALUES (\n" +
                    "                       6,\n" +
                    "                       NULL,\n" +
                    "                       'Bargain With Nyarlathotep',\n" +
                    "                       1\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       1,\n" +
                    "                       NULL,\n" +
                    "                       'Dimensions Collide',\n" +
                    "                       2\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       4,\n" +
                    "                       NULL,\n" +
                    "                       'Eye of the Storm',\n" +
                    "                       3\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       4,\n" +
                    "                       NULL,\n" +
                    "                       'Far Corners of the Globe',\n" +
                    "                       4\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       1,\n" +
                    "                       NULL,\n" +
                    "                       'Secrets of the Past',\n" +
                    "                       5\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       7,\n" +
                    "                       NULL,\n" +
                    "                       'Song of Spheres',\n" +
                    "                       6\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       3,\n" +
                    "                       NULL,\n" +
                    "                       'Time Is Running Out',\n" +
                    "                       7\n" +
                    "                   ),\n" +
                    "                   (\n" +
                    "                       1,\n" +
                    "                       NULL,\n" +
                    "                       'Web Between Worlds',\n" +
                    "                       8\n" +
                    "                   );");
        }
    };
}
