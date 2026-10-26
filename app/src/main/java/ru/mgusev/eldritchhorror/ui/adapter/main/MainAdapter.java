package ru.mgusev.eldritchhorror.ui.adapter.main;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ItemGameBinding;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.repository.Repository;

public class MainAdapter extends RecyclerSwipeAdapter<MainAdapter.MainViewHolder> {

    static class MainViewHolder extends RecyclerView.ViewHolder {

        private final ItemGameBinding binding;

        private Game item;

        MainViewHolder(ItemGameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            //swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
    }

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Inject
    Repository repository;
    @Inject
    List<AncientOne> ancientOneList;
    @Inject
    List<Expansion> expansionList;
    //declare interface
    private OnItemClickedReturnObj onClick;
    private List<Game> gameList;

    private Context context;
    private SwipeLayout currentSwipeLayout;

    public MainAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.gameList = new ArrayList<>();
    }

    public void updateGameList(List<Game> gameList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new GameDiffUtilCallback(this.gameList, gameList), false);
        this.gameList.clear();
        this.gameList.addAll(gameList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGameBinding binding = ItemGameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {
        holder.item = gameList.get(position);

        holder.binding.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        holder.binding.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                if (currentSwipeLayout != layout) closeSwipeLayout();
                currentSwipeLayout = layout;
            }
        });

        holder.binding.date.setText(formatter.format(gameList.get(position).getDate()));
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getImageResource(), "drawable", context.getPackageName());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(App.resourceToUri(context, resourceId))
                .setResizeOptions(new ResizeOptions(400, 100))
                .build();
        holder.binding.background.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.binding.background.getController())
                        .setImageRequest(request)
                        .build());

        resourceId = resources.getIdentifier(Objects.requireNonNull(getExpansion(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getExpansionID())).getImageResource(), "drawable", context.getPackageName());
        holder.binding.expansionImage.setImageResource(resourceId);
        holder.binding.ancientOne.setText(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getName());
        holder.binding.playersCount.setText(String.valueOf(gameList.get(position).getPlayersCount()));

        if (gameList.get(position).getIsWinGame()) {
            holder.binding.winImageMain.setImageResource(R.drawable.stars);
            holder.binding.score.setText(String.valueOf(gameList.get(position).getScore()));
            holder.binding.score.setVisibility(View.VISIBLE);
        } else {
            if (gameList.get(position).getIsDefeatByAwakenedAncientOne()) holder.binding.winImageMain.setImageResource(R.drawable.skull);
            else if (gameList.get(position).getIsDefeatByElimination()) holder.binding.winImageMain.setImageResource(R.drawable.investigators_out);
            else if (gameList.get(position).getIsDefeatByMythosDepletion()) holder.binding.winImageMain.setImageResource(R.drawable.mythos_empty);
            else if (gameList.get(position).getIsDefeatByRumor()) holder.binding.winImageMain.setImageResource(R.drawable.defeat_by_rumor);
            else if (gameList.get(position).getIsDefeatBySurrender()) holder.binding.winImageMain.setImageResource(R.drawable.defeat_by_surrender);
            else if (gameList.get(position).getIsDefeatByPrelude()) holder.binding.winImageMain.setImageResource(R.drawable.defeat_by_prelude);
            holder.binding.score.setVisibility(View.GONE);
        }

        if (repository.getImages(gameList.get(position).getId()).isEmpty()) holder.binding.galleryIcon.setVisibility(View.GONE);
        else holder.binding.galleryIcon.setVisibility(View.VISIBLE);

        if (gameList.get(position).getComment() != null && !gameList.get(position).getComment().equals("")) holder.binding.commentIcon.setVisibility(View.VISIBLE);
        else holder.binding.commentIcon.setVisibility(View.GONE);

        holder.binding.itemCV.setOnClickListener(v -> {
            //v.startAnimation(animAlpha);
            onClick.onItemClick(holder.item);
        });

        holder.binding.itemCV.setOnLongClickListener(view -> false);

        holder.binding.editImgSwipe.setOnClickListener(v -> onClick.onEditClick(holder.item));

        holder.binding.deleteImgSwipe.setOnClickListener(v -> onClick.onDeleteClick(holder.item));
    }

    private AncientOne getAncientOne(int id) {
        for (AncientOne ancientOne : ancientOneList) {
            if (ancientOne.getId() == id) return ancientOne;
        }
        return null;
    }

    private Expansion getExpansion(int id) {
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == id) return expansion;
        }
        return null;
    }

    public void closeSwipeLayout() {
        if (currentSwipeLayout != null) currentSwipeLayout.close(true);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void setOnClick(OnItemClickedReturnObj onClick) {
        this.onClick = onClick;
    }
}
