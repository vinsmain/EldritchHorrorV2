package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skydoves.powermenu.MenuBaseAdapter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.support.IconPowerMenuItem;

public class IconMenuAdapter extends MenuBaseAdapter<IconPowerMenuItem> {

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_icon_menu, viewGroup, false);
        }

        IconPowerMenuItem item = (IconPowerMenuItem) getItem(index);
        final ImageView icon = view.findViewById(R.id.menu_icon);
        icon.setImageDrawable(item.getIcon());
        final TextView title = view.findViewById(R.id.menu_title);
        title.setText(item.getTitle());
        return super.getView(index, view, viewGroup);
    }
}