package ru.mgusev.eldritchhorror.androidmaterialgallery;

import android.content.Context;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryItemHolder> {


    static class GalleryItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_gallery_card_view) CardView cardView;
        @BindView(R.id.item_gallery_photo) SimpleDraweeView photo;

        GalleryItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<String> galleryItems;
    private List<String> selectedGalleryItems;
    private Context context;
    private OnItemClicked onClick;

    public GalleryAdapter(Context context) {
        this.context = context;
        this.galleryItems = new ArrayList<>();
        this.selectedGalleryItems = new ArrayList<>();
    }

    public void addGalleryItems(List<String> galleryItems) {
        notifyItemRangeRemoved(0, getItemCount());
        this.galleryItems = galleryItems;
        notifyItemRangeInserted(0, getItemCount());
    }

    public void selectGalleryItem(List<String> selectedGalleryItems, int position) {
        this.selectedGalleryItems = selectedGalleryItems;
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public GalleryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemHolder holder, int position) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(galleryItems.get(holder.getAdapterPosition())))
                .setResizeOptions(new ResizeOptions(300, 300))
                .build();
        holder.photo.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.photo.getController())
                        .setImageRequest(request)
                        .build());

        if (selectedGalleryItems.contains(galleryItems.get(holder.getAdapterPosition()))) holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_starting_investigator));
        else holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorText));

        holder.cardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
        holder.cardView.setOnLongClickListener(v -> {
            onClick.onItemLongClick(holder.getAdapterPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}