package ru.mgusev.eldritchhorror.utils.gallery;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ItemGalleryBinding;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryItemHolder> {


    static class GalleryItemHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final SimpleDraweeView photo;

        GalleryItemHolder(ItemGalleryBinding binding) {
            super(binding.getRoot());
            cardView = binding.itemGalleryCardView;
            photo = binding.itemGalleryPhoto;
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
        return new GalleryItemHolder(ItemGalleryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
