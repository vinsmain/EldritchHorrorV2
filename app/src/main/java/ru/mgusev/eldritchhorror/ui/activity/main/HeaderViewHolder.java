package ru.mgusev.eldritchhorror.ui.activity.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;

class HeaderViewHolder {

    @BindView(R.id.nav_header_auth_icon) protected SimpleDraweeView iconImage;
    @BindView(R.id.nav_header_auth_name) protected TextView nameTV;
    @BindView(R.id.nav_header_auth_email) protected TextView emailTV;

    HeaderViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
