package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.repository.Repository;

public class MainAdapter extends RecyclerSwipeAdapter<MainAdapter.MainViewHolder> {

    static class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemCV) CardView cardView;
        @BindView(R.id.background) SimpleDraweeView background;
        @BindView(R.id.expansion_image) ImageView expansionImage;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.ancientOne) TextView ancientOne;
        @BindView(R.id.playersCount) TextView playersCount;
        @BindView(R.id.score) TextView score;
        @BindView(R.id.win_image_main) ImageView winImage;
        @BindView(R.id.swipeLayout) SwipeLayout swipeLayout;
        @BindView(R.id.editImgSwipe) ImageView editImgSwipe;
        @BindView(R.id.deleteImgSwipe) ImageView deleteImgSwipe;
        @BindView(R.id.gallery_icon) ImageView galleryIW;
        @BindView(R.id.comment_icon) ImageView commentIW;

        MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
    private OnItemClicked onClick;
    private List<Game> gameList;

    private Context context;
    private SwipeLayout currentSwipeLayout;

    public MainAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.gameList = new ArrayList<>();
    }

    public void updateGameList(List<Game> gameList) {
        notifyItemRangeRemoved(0, getItemCount());
        this.gameList = gameList;
        notifyItemRangeInserted(0, getItemCount());
    }

    public void deleteGame(int position, List<Game> gameList) {
        this.gameList = gameList;
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                if (currentSwipeLayout != layout) closeSwipeLayout();
                currentSwipeLayout = layout;
            }
        });

        holder.date.setText(formatter.format(gameList.get(position).getDate()));
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getImageResource(), "drawable", context.getPackageName());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(App.resourceToUri(context, resourceId))
                .setResizeOptions(new ResizeOptions(400, 100))
                .build();
        holder.background.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.background.getController())
                        .setImageRequest(request)
                        .build());

        resourceId = resources.getIdentifier(Objects.requireNonNull(getExpansion(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getExpansionID())).getImageResource(), "drawable", context.getPackageName());
        holder.expansionImage.setImageResource(resourceId);
        holder.ancientOne.setText(Objects.requireNonNull(getAncientOne(gameList.get(position).getAncientOneID())).getName());
        holder.playersCount.setText(String.valueOf(gameList.get(position).getPlayersCount()));

        if (gameList.get(position).getIsWinGame()) {
            holder.winImage.setImageResource(R.drawable.stars);
            holder.score.setText(String.valueOf(gameList.get(position).getScore()));
            holder.score.setVisibility(View.VISIBLE);
        } else {
            if (gameList.get(position).getIsDefeatByAwakenedAncientOne()) holder.winImage.setImageResource(R.drawable.skull);
            else if (gameList.get(position).getIsDefeatByElimination()) holder.winImage.setImageResource(R.drawable.investigators_out);
            else if (gameList.get(position).getIsDefeatByMythosDepletion()) holder.winImage.setImageResource(R.drawable.mythos_empty);
            else if (gameList.get(position).getIsDefeatByRumor()) holder.winImage.setImageResource(R.drawable.defeat_by_rumor);
            else if (gameList.get(position).getIsDefeatBySurrender()) holder.winImage.setImageResource(R.drawable.defeat_by_surrender);
            holder.score.setVisibility(View.GONE);
        }

        if (repository.getImages(gameList.get(position).getId()).isEmpty()) holder.galleryIW.setVisibility(View.GONE);
        else holder.galleryIW.setVisibility(View.VISIBLE);

        if (gameList.get(position).getComment() != null && !gameList.get(position).getComment().equals("")) holder.commentIW.setVisibility(View.VISIBLE);
        else holder.commentIW.setVisibility(View.GONE);

        holder.cardView.setOnClickListener(v -> {
            //v.startAnimation(animAlpha);
            onClick.onItemClick(position);
        });

        holder.cardView.setOnLongClickListener(view -> false);

        holder.editImgSwipe.setOnClickListener(v -> onClick.onEditClick(position));

        holder.deleteImgSwipe.setOnClickListener(v -> onClick.onDeleteClick(position));
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

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}