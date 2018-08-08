package ru.mgusev.eldritchhorror.androidmaterialgallery;

import android.content.Context;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.ui.fragment.pager.GamePhotoFragment;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryItemHolder> {


    static class GalleryItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_gallery_card_view) CardView cardView;
        @BindView(R.id.item_gallery_photo) SimpleDraweeView photo;

        GalleryItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //Declare GalleryItems List
    private List<String> galleryItems;
    private Context context;
    //Declare GalleryAdapterCallBacks
    private GalleryAdapterCallBacks mAdapterCallBacks;

    public GalleryAdapter(Context context, GamePhotoFragment fragment) {
        this.context = context;
        //get GalleryAdapterCallBacks from contex
        this.mAdapterCallBacks = (GalleryAdapterCallBacks) fragment;
        //Initialize GalleryItem List
        this.galleryItems = new ArrayList<>();
    }

    //This method will take care of adding new Gallery1 items to RecyclerView
    public void addGalleryItems(List<String> galleryItems) {
        notifyItemRangeRemoved(0, getItemCount());
        this.galleryItems = galleryItems;
        notifyItemRangeInserted(0, getItemCount());
    }

    @NonNull
    @Override
    public GalleryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemHolder holder, int position) {
        holder.photo.setImageURI(Uri.parse(galleryItems.get(position)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call onItemSelected method and pass the position and let activity decide what to do when item selected
                mAdapterCallBacks.onItemSelected(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    //Interface for communication of Adapter and MainActivity
    public interface GalleryAdapterCallBacks {
        //call this method to notify about item is clicked
        void onItemSelected(int position);
    }
}