package ru.mgusev.eldritchhorror.database.staticDB.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class StaticDBMigrations {

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
            database.execSQL("INSERT INTO expansions (is_enable, name_ru, name_en, image_resource, _id) VALUES (0, 'Фанатские прелюдии\nавтор Michael Williams', 'Fan preludes\nby Michael Williams', 'fan_preludes', 10);");

            database.execSQL("ALTER TABLE `preludes` ADD COLUMN `text_en` TEXT;");
            database.execSQL("ALTER TABLE `preludes` ADD COLUMN `text_ru` TEXT;");

            database.execSQL("INSERT INTO preludes (text_ru, text_en, expansion_id, name_ru, name_en, _id) " +
                    "VALUES ('Ужасные последствия мифов неизбежно сводят вас с ума.\n" +
                    "- Максимальная потеря рассудка от любого источника равна 1.\n" +
                    "- Рассудок не может быть восстановлен с помощью отдыха.',\n" +
                    "                         'The horrors of the mythos are inevitably driving you mad.\n" +
                    "- The maximum sanity loss from any single cause is 1\n" +
                    "- Sanity cannot be recovered during a rest',\n" +
                    "                         10,\n" +
                    "                         'Космический ужас',\n" +
                    "                         'Cosmic Horror',\n" +
                    "                         41\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Стены, защищающие реальность разрушаются.\n" +
                    "- Всякий раз, когда знамение продвигается на одно деление, продвиньте его еще раз.\n" +
                    "- При расплате, сыграйте вторую расплату сразу же после первой.\n" +
                    "- При открытии врат и наплыве монстров, монстры на поле не выкладываются.',\n" +
                    "                         'The walls defending reality are crumbling.\n" +
                    "- Whenever the Omen advances by 1, advance it again\n" +
                    "- On a Reckoning, resolve a second Reckoning after completing the first\n" +
                    "- Gate spawns and monster surges do not spawn monsters',\n" +
                    "                         10,\n" +
                    "                         'Давящие стены',\n" +
                    "                         'Walls Collapsing',\n" +
                    "                         42\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Наши предки сдержали мифы, возможно, они смогут помочь нам и сейчас.\n" +
                    "- Новое действие: Пройдите проверку знаний или общения. Если вам выпало 2+ успеха, немедленно перейдите в локацию Экспедиции или мистических руин.\n" +
                    "- Проходя экспедицию или мистические руины, потеряйте 2 здоровья.',\n" +
                    "                         'Our ancestors delayed the mythos, maybe they can help us now.\n" +
                    "- New Action: roll 2 successes on Lore or Influence to immediately move to the Expedition or Mystic Ruins\n" +
                    "- Attempting the Expedition or Mystic Ruins costs 2 Health',\n" +
                    "                         10,\n" +
                    "                         'Наблюдение за Древними',\n" +
                    "                         'Look to the Ancients.',\n" +
                    "                         43\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Только тот, кто живет в тени, может сражаться с тьмой.\n" +
                    "- Сыщики не могут отдыхать в городах.\n" +
                    "- Каждый сыщик начинает игру с артефактом по своему выбору.',\n" +
                    "                         'Only those who live in the shadows can fight the darkness.\n" +
                    "- Investigators cannot rest in cities\n" +
                    "- Each investigator begins with an artifact of his choice',\n" +
                    "                         10,\n" +
                    "                         'Изгои',\n" +
                    "                         'Outcasts',\n" +
                    "                         44\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Древние знания могут быть благом, а могут нести проклятие.\n" +
                    "- Сыщики начинают с улучшением +2 знания.\n" +
                    "- Сыщики начинают с ухудшением -2 силы.',\n" +
                    "                         'Eldritch lore can be used for good as well as evil.\n" +
                    "- Investigators start with 2 Lore improvements\n" +
                    "- Investigators start with 2 Strength impairments',\n" +
                    "                         10,\n" +
                    "                         'Знания – сила',\n" +
                    "                         'Knowledge is Power',\n" +
                    "                         45\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Святые воители со всего мира присоединяются к битве с мифами.\n" +
                    "- Каждый сыщик начинает игру благословленным.\n" +
                    "- Каждый сыщик начинает игру с состоянием обязательство (в русских локализациях еще нет дополнения вводящего это состояние)',\n" +
                    "                         'Holy warriors from around the world join to fight the mythos.\n" +
                    "- Each investigator starts with a Righteous condition\n" +
                    "- Each investigator starts with an Agreement condition',\n" +
                    "                         10,\n" +
                    "                         'Да придет Царствие Твое',\n" +
                    "                         'Thy Kingdom Come',\n" +
                    "                         46\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Вы будете охотиться за ними до конца света.\n" +
                    "- Каждый сыщик может держать у себя до 4 билетов.\n" +
                    "- Каждый сыщик может взять 2 билета, когда должен получить 1.\n" +
                    "- Когда улика появляется в городе, переместите ее в ближайшую не городскую локацию без улик. Если таких локаций нет, не выкладывайте улику.',\n" +
                    "                         'You will hunt it to the ends of the earth.\n" +
                    "- Each investigator can hold 4 tickets at once, and gains 2 whenever he would gain 1\n" +
                    "- When a Clue spawns in a City, move it to the nearest non-City with 0 Clues or remove the Clue',\n" +
                    "                         10,\n" +
                    "                         'Путешественники',\n" +
                    "                         'World Travelers',\n" +
                    "                         47\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Мировая элита была навсегда испорчена.\n" +
                    "- Сыщики начинают с ухудшениями -2 общения.\n" +
                    "- Всякий раз, когда сыщик должен получить один жетон собранности, он получает 2 жетона вместо 1.',\n" +
                    "                         'The world’s elite have been irredeemably corrupted.\n" +
                    "- Investigators start with 2 Influence impairments\n" +
                    "- Whenever an investigator would gain 1 Focus he instead gains 2',\n" +
                    "                         10,\n" +
                    "                         'Блаженные бедняки',\n" +
                    "                         'Blessed are the Poor',\n" +
                    "                         48\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Только наращивая ресурсы, мы сможем преодолеть натиск.\n" +
                    "- Все успехи при приобретении активов удваиваются.\n" +
                    "- Сыщики не могут получать активы, стоимостью 2 и меньше. Если они их получили, то актив сбрасывается. Эффект распространяется и на стартовое имущество.',\n" +
                    "                         'Only by massing our resources can we hope to survive the onslaught.\n" +
                    "- Double the number of successes rolled when acquiring assets\n" +
                    "- Investigators cannot acquire assets that cost 1 or 2. If they would do so, the asset is instead discarded, including starting assets.',\n" +
                    "                         10,\n" +
                    "                         'Богатый и мудрый',\n" +
                    "                         'Wealthy and Wise',\n" +
                    "                         49\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Вы знаете парня, который знает парня, который может вам помочь.\n" +
                    "- Новое действие: при приобретении активов, вы можете потратить 1 успех, чтобы выбрать оружие, книгу, услугу или союзника и вытянуть случайный актив соответствующего типа из колоды активов.\n" +
                    "- Каждый сыщик начинает с состоянием долг. Если сыщик избавляется от долга, он немедленно получает новый.',\n" +
                    "                         'You know a guy who knows a guy who can help.\n" +
                    "- New Action: when acquiring assets, spend 1 success to select Weapon, Tome, Service, or Ally and draw a random asset of that type from the deck\n" +
                    "- Each investigator starts with a Debt condition. If an investigator ever loses a Debt condition he immediately gains a Debt condition.',\n" +
                    "                         10,\n" +
                    "                         'Связи',\n" +
                    "                         'Connected',\n" +
                    "                         50\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Настало время для вашего последнего экзамена.\n" +
                    "- Сыщики помещают жетон приключения в локации для обозначения академии.\n" +
                    "- Все сыщики начинают в академии.\n" +
                    "- Новое действие: Сыщик в академии может произнести любое заклинание ритуал.\n" +
                    "- Расплата: Выложите монстра в академию.',\n" +
                    "                         'It’s time for your final exam.\n" +
                    "- Investigators place an Adventure token on a location to indicate their Academy\n" +
                    "- All investigators begin at the Academy\n" +
                    "- New Action: while at the Academy, an investigator may cast any Ritual spell\n" +
                    "- On a Reckoning: spawn a monster on the Academy',\n" +
                    "                         10,\n" +
                    "                         'Тайная академия',\n" +
                    "                         'Arcane Academy',\n" +
                    "                         51\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Ваши предшественники провалились и их духи впали в отчаяние.\n" +
                    "- Каждый сыщик начинает с состоянием призраки.\n" +
                    "- Новое действие: сыщик без состояния призраки, может получить это состояние, чтобы отказаться от другого состояния или получить 2 здоровья и 2 рассудка.',\n" +
                    "                         'Your predecessors have fallen, and their spirits rage in despair.\n" +
                    "- Each investigator starts with a Haunted condition\n" +
                    "- New Action: an investigator without a Haunted condition may gain a Haunted condition to discard one other condition or to gain 2 Health and 2 Sanity',\n" +
                    "                         10,\n" +
                    "                         'Призраки прошлого',\n" +
                    "                         'Ghosts of the Past',\n" +
                    "                         52\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'На вас возложены все надежды этого гниющего мира.\n" +
                    "- Расплата: Каждый сыщик теряет 1 здоровье.\n" +
                    "- Новое действие: Сыщик в городе может взять один актив из резерва.',\n" +
                    "                         'You carry the hopes of the world as it wastes away.\n" +
                    "- On a Reckoning: each investigator loses 1 Health\n" +
                    "- New Action: an investigator in a City may take one Asset from the Reserve',\n" +
                    "                         10,\n" +
                    "                         'Пандемия',\n" +
                    "                         'Pandemic',\n" +
                    "                         53\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Вы знаете, что нужно делать, но ваши враги дышат в спину.\n" +
                    "- Новое действие: Получить жетон собранности и выложить 1 улику.\n" +
                    "- Расплата: Каждый сыщик в городе получает состояние преследуемый и задерживается.',\n" +
                    "                         'You know what you have to do, but your foes are close behind.\n" +
                    "- New Action: gain a Focus and spawn a Clue\n" +
                    "- On a Reckoning: each investigator in a City gains a Hunted condition and is Delayed',\n" +
                    "                         10,\n" +
                    "                         'Психический резонанс',\n" +
                    "                         'Psychic Resonance',\n" +
                    "                         54\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Реальность от вас ускользает.\n" +
                    "- Когда у сыщика не получается закрыть врата, он получает состояние потерянные во времени и пространстве.\n" +
                    "- Новое действие: Пройдите проверку знаний или общения. Если вам выпало 2+ успеха, получите заклинание.',\n" +
                    "                         'Reality is melting away before your eyes.\n" +
                    "- When an investigator fails to close a Gate he gains Lost in Time and Space\n" +
                    "- New Action: roll 2 successes on Lore or Influence to gain a spell\n" +
                    "',\n" +
                    "                         10,\n" +
                    "                         'Цепкие врата',\n" +
                    "                         'Clutching Gates',\n" +
                    "                         55\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Ваша слабая связь с вашим рассудком рвется, когда вы мчитесь в пропасть.\n" +
                    "- Когда сыщик получает состояние, он также получает безумие (допускаются повторения).\n" +
                    "- Новое действие: повторите действие, которое уже было использовано на этом ходу.',\n" +
                    "                         'Your tenuous grip on sanity is failing as you hurtle towards the end.\n" +
                    "- When an investigator gains a condition he also gains a Madness (allow duplicates)\n" +
                    "- New Action: repeat an action already used this turn',\n" +
                    "                         10,\n" +
                    "                         'Падение в безумие',\n" +
                    "                         'Slide Into Insanity',\n" +
                    "                         56\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Общество погружается в хаос.\n" +
                    "- Сыщики не могут использовать билеты.\n" +
                    "- Сыщики не могут быть задержаны и могут игнорировать любые последствия состояния долг.',\n" +
                    "                         'Society descends into chaos.\n" +
                    "- Investigators cannot use tickets\n" +
                    "- Investigators cannot be Detained and may ignore any effects on Deal conditions',\n" +
                    "                         10,\n" +
                    "                         'Крах цивилизации',\n" +
                    "                         'Civilizational Collapse',\n" +
                    "                         57\n" +
                    "                     ),\n" +
                    "                     (\n" +
                    "                         'Ничего подобного.\n" +
                    "- Когда сыщик проваливает попытку получить улику, уберите ее с поля и выложите новую.\n" +
                    "- Выкладывая врата помещайте их в случайную локацию.\n" +
                    "- Новое действие: Сбросьте актив, чтобы получить новый актив такой же стоимостью или выше.\n" +
                    "- Новое действие: Сбросьте актив стоимостью 4, чтобы получить артефакт.',\n" +
                    "                         'Nothing is as it seems.\n" +
                    "- When an investigator fails a Research encounter remove the Clue and spawn a new Clue\n" +
                    "- When a Gate is spawned move it to a random location before spawning a monster\n" +
                    "- New Action: discard an asset to draw a new asset with the same cost or higher\n" +
                    "- New Action: discard a cost 4 asset to draw an Artifact',\n" +
                    "                         10,\n" +
                    "                         'Поток и хаос',\n" +
                    "                         'Flux and Chaos',\n" +
                    "                         58);");
        }
    };
}