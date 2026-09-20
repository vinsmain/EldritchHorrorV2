package ru.mgusev.eldritchhorror.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.Window;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/** Полноэкранный просмотр одного изображения без дополнительной media-библиотеки. */
public final class PhotoViewerDialog {

    private PhotoViewerDialog() {
    }

    public static Dialog show(Context context, Uri imageUri, DialogInterface.OnDismissListener onDismissListener) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        SimpleDraweeView imageView = new SimpleDraweeView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(Color.BLACK);
        imageView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        imageView.setImageURI(imageUri);
        imageView.setOnClickListener(view -> dialog.dismiss());

        dialog.setContentView(imageView);
        dialog.setOnDismissListener(onDismissListener);
        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        return dialog;
    }
}
