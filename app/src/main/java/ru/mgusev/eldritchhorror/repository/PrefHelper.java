package ru.mgusev.eldritchhorror.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefHelper {

    private SharedPreferences sPref;
    private Context context;
    private static final String SETTINGS = "settings";
    private static final String SETTINGS_DATE = "settings_date";
    private static final String SETTINGS_IS_RATE = "settings_is_rate";
    private static final String SETTINGS_GAMES_COUNT = "settings_games_count";
    private static final String SETTINGS_SORT_MODE = "settings_sort_mode";
    private static final long TWENTY_FOUR_HOURS = 86400000; //milliseconds

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
}