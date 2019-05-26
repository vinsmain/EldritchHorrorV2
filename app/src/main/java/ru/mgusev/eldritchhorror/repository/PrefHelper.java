package ru.mgusev.eldritchhorror.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefHelper {

    private SharedPreferences sPref;
    private Context context;
    private static final String SETTINGS = "settings";
    private static final String SETTINGS_IS_RATE = "settings_is_rate";
    private static final String SETTINGS_GAMES_COUNT = "settings_games_count";
    private static final String SETTINGS_SORT_MODE = "settings_sort_mode";
    private static final String SETTINGS_IS_SCREEN_LIGHT = "settings_is_screen_light";
    private static final String SETTINGS_ANIMATION_MODE = "settings_animation_mode";
    private static final String SETTINGS_DICE_COUNT = "settings_dice_count";

    public PrefHelper(Context context) {
        this.context = context;
    }

    public void saveIsRate(boolean value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putBoolean(SETTINGS_IS_RATE, value);
        ed.apply();
    }

    public boolean loadIsRate() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getBoolean(SETTINGS_IS_RATE, false);
    }

    public void saveGameCountForRateDialog(int value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putInt(SETTINGS_GAMES_COUNT, value);
        ed.apply();
    }

    public int loadGameCountForRateDialog() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getInt(SETTINGS_GAMES_COUNT, 0);
    }

    public void saveSortMode(int value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putInt(SETTINGS_SORT_MODE, value);
        ed.apply();
    }

    public int loadSortMode() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getInt(SETTINGS_SORT_MODE, 1);
    }

    public void saveIsScreenLight(boolean value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putBoolean(SETTINGS_IS_SCREEN_LIGHT, value);
        ed.apply();
    }

    public boolean loadIsScreenLight() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getBoolean(SETTINGS_IS_SCREEN_LIGHT, false);
    }

    /**
     * Сохраняет значение режима анимации кубиков
     * {@link ru.mgusev.eldritchhorror.ui.activity.dice.DiceActivity#animation2DMode}
     * {@link ru.mgusev.eldritchhorror.ui.activity.dice.DiceActivity#animation3DMode}
     * @param value : true - 3D, false - 2D
     */
    public void saveAnimationMode(boolean value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putBoolean(SETTINGS_ANIMATION_MODE, value);
        ed.apply();
    }

    /**
     * Возвращает знаечение режима анимации кубиков
     * @return true - 3D, false - 2D
     */
    public boolean loadAnimationMode() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getBoolean(SETTINGS_ANIMATION_MODE, false);
    }

    /**
     * Сохраняет число кубиков для {@link ru.mgusev.eldritchhorror.ui.activity.dice.DiceActivity#diceCountSeekBar}
     * @param value - число кубиков
     */
    public void saveDiceCount(int value) {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putInt(SETTINGS_DICE_COUNT, value);
        ed.apply();
    }

    /**
     * Возвращает число кубиков
     * @return - число кубиков
     */
    public int loadDiceCount() {
        sPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sPref.getInt(SETTINGS_DICE_COUNT, 6);
    }
}