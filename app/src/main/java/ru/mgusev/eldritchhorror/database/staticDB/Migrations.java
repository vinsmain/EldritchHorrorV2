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
            database.execSQL("CREATE TABLE `rumors` (`_id` INTEGER NOT NULL, `name_en` TEXT, `name_ru` TEXT, `expansion_id` INTEGER NOT NULL, PRIMARY KEY(`_id`))");
            database.execSQL("INSERT INTO rumors (expansion_id, name_ru, name_en, _id) " +
                    "VALUES (6, 'Договор с Ньярлатхотепом', 'Bargain With Nyarlathotep', 1), " +
                        "(1, 'Миры сталкиваются', 'Dimensions Collide', 2), " +
                        "(4, 'Око бури', 'Eye of the Storm', 3), " +
                        "(4, 'Дальние уголки земного шара', 'Far Corners of the Globe', 4), " +
                        "(1, 'Тайны прошлого', 'Secrets of the Past', 5), " +
                        "(7, 'Песнь сфер', 'Song of Spheres', 6), " +
                        "(3, 'Время на исходе', 'Time Is Running Out', 7), " +
                        "(1, 'Паутина между мирами', 'Web Between Worlds', 8);");

            database.execSQL("UPDATE ancient_ones SET name_ru = 'Ньярлатхотеп' WHERE _id = 15;");

            database.execSQL("UPDATE expansions SET name_ru = 'Маски Ньярлатхотепа' WHERE _id = 9;");
        }
    };

    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO expansions (is_enable, name_ru, name_en, image_resource, _id) VALUES (1, 'Фанатские прелюдии', 'Fan preludes', 'fan_preludes', 10);");

            database.execSQL("INSERT INTO preludes (expansion_id, name_ru, name_en, _id) " +
                    "VALUES (10, 'Космический ужас', 'Cosmic Horror', 41), " +
                    "(10, 'Давящие стены', 'Walls Collapsing', 42), " +
                    "(10, 'Наблюдение за Древними', 'Look to the Ancients.', 43), " +
                    "(10, 'Изгои', 'Outcasts', 44), " +
                    "(10, 'Знания – сила', 'Knowledge is Power', 45), " +
                    "(10, 'Да придет Царствие Твое', 'Thy Kingdom Come', 46), " +
                    "(10, 'Путешественники', 'World Travelers', 47), " +
                    "(10, 'Блаженные бедняки', 'Blessed are the Poor', 48), " +
                    "(10, 'Богатый и мудрый', 'Wealthy and Wise', 49), " +
                    "(10, 'Связи', 'Connected', 50), " +
                    "(10, 'Тайная академия', 'Arcane Academy', 51), " +
                    "(10, 'Призраки прошлого', 'Ghosts of the Past', 52), " +
                    "(10, 'Пандемия', 'Pandemic', 53), " +
                    "(10, 'Психический резонанс', 'Psychic Resonance', 54), " +
                    "(10, 'Цепкие врата', 'Clutching Gates', 55), " +
                    "(10, 'Падение в безумие', 'Slide Into Insanity', 56), " +
                    "(10, 'Крах цивилизации', 'Civilizational Collapse', 57), " +
                    "(10, 'Поток и хаос', 'Flux and Chaos', 58);");

            database.execSQL("CREATE TABLE `prelude_details` (`_id` INTEGER NOT NULL, `prelude_id` INTEGER NOT NULL, `name_en` TEXT, `name_ru` TEXT, PRIMARY KEY(`_id`))");
            database.execSQL("INSERT INTO rumors (name_ru, name_en, expansion_id, _id) " +
                    "VALUES (6, 'Договор с Ньярлатхотепом', 'Bargain With Nyarlathotep', 41, 1), " +
                    "(1, 'Миры сталкиваются', 'Dimensions Collide', 42, 2), " +
                    "(4, 'Око бури', 'Eye of the Storm', 43, 3), " +
                    "(4, 'Дальние уголки земного шара', 'Far Corners of the Globe', 4), " +
                    "(1, 'Тайны прошлого', 'Secrets of the Past', 5), " +
                    "(7, 'Песнь сфер', 'Song of Spheres', 6), " +
                    "(3, 'Время на исходе', 'Time Is Running Out', 7), " +
                    "(1, 'Паутина между мирами', 'Web Between Worlds', 8);");
        }
    };
}