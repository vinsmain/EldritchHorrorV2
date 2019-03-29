package ru.mgusev.eldritchhorror.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.mgusev.eldritchhorror.R;

public class StatsIcons {

    private Context context;
    private Map<String, Drawable> iconMap;

    public StatsIcons(Context context) {
        this.context = context;
    }

    @SuppressLint("ResourceType")
    private void initIconMap() {
        iconMap = new HashMap<>();
        String[] iconCodeArray = context.getResources().getStringArray(R.array.icon_code_array);
        TypedArray iconDrawableArray = context.getResources().obtainTypedArray(R.array.icon_drawable_array);
        for (int i = 0; i < iconCodeArray.length; i++) {
            iconMap.put(iconCodeArray[i], Objects.requireNonNull(iconDrawableArray.getDrawable(i)));
        }
        iconDrawableArray.recycle();
    }

    public Map<String, Drawable> getIconMap() {
        if (iconMap == null)
            initIconMap();
        return iconMap;
    }
}
