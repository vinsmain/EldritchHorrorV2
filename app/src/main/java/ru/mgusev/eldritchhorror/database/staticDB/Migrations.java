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
}
