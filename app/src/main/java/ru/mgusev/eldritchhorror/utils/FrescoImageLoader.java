package ru.mgusev.eldritchhorror.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.esafirm.imagepicker.features.imageloader.ImageLoader;
import com.esafirm.imagepicker.features.imageloader.ImageType;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import ru.mgusev.eldritchhorror.repository.FileHelper;

public class FrescoImageLoader implements ImageLoader {

    @Override
    public void loadImage(String path, ImageView imageView, ImageType imageType) {
        Drawable defaultDrawable = imageView.getDrawable();
        Resources resources = imageView.getContext().getResources();
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(resources)
                .build();
        final DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, imageView.getContext());
        draweeHolder.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
        Drawable drawable = draweeHolder.getHierarchy().getTopLevelDrawable();
        if (drawable == null) {
            imageView.setImageDrawable(defaultDrawable);
        } else {
            imageView.setImageDrawable(drawable);
        }

        File file = new File(path);
        Uri uri = FileHelper.getImageContentUri(imageView.getContext(), file);

        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(500, 500))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.getController())
                .setImageRequest(imageRequest)
                .build();
        draweeHolder.setController(controller);
    }
}