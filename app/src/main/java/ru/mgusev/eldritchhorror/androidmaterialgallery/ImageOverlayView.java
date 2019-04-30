package ru.mgusev.eldritchhorror.androidmaterialgallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.interfaces.OnShareImageClick;

public class ImageOverlayView extends RelativeLayout {

    private OnShareImageClick onShareImageClick;

    public ImageOverlayView(Context context, OnShareImageClick onShareImageClick) {
        super(context);
        this.onShareImageClick = onShareImageClick;
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_image_overlay, this);
        view.findViewById(R.id.btnShare).setOnClickListener(v -> onShareImageClick.onClickShare());
    }
}