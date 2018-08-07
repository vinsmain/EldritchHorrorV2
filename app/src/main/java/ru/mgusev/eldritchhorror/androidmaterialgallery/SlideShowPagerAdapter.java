package ru.mgusev.eldritchhorror.androidmaterialgallery;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import ru.mgusev.eldritchhorror.R;

/**
 * Created by amardeep on 11/3/2017.
 */

public class SlideShowPagerAdapter extends PagerAdapter {

    Context mContext;
    //Layout inflater
    LayoutInflater mLayoutInflater;
    //list of Gallery1 Items
    List<GalleryItem> galleryItems;

    public SlideShowPagerAdapter(Context context, List<GalleryItem> galleryItems) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //set galleryItems
        this.galleryItems = galleryItems;
    }

    @Override
    public int getCount() {
        return galleryItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
        //load current image in viewpager
        Picasso.get().load(new File(galleryItems.get(position).imageUri)).fit().into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

}