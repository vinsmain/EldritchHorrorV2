package ru.mgusev.eldritchhorror.ui.activity.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import ru.mgusev.eldritchhorror.databinding.NavHeaderMainBinding;

class HeaderViewHolder {

    protected SimpleDraweeView iconImage;
    protected TextView nameTV;
    protected TextView emailTV;

    HeaderViewHolder(View view) {
        NavHeaderMainBinding binding = NavHeaderMainBinding.bind(view);
        iconImage = binding.navHeaderAuthIcon;
        nameTV = binding.navHeaderAuthName;
        emailTV = binding.navHeaderAuthEmail;
    }
}
