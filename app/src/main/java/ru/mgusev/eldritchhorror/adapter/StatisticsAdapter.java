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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    static class StatisticsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_inv_card_view) CardView invCardView;
        @BindView(R.id.item_inv_photo) ImageView invPhoto;
        @BindView(R.id.item_inv_expansion) ImageView invExpansion;
        @BindView(R.id.item_inv_name) TextView invName;
        @BindView(R.id.item_inv_occupation) TextView invOccupation;

        StatisticsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Inject
    List<Expansion> expansionList;
    private OnItemClicked onClick;
    private List<Investigator> investigatorList;

    public StatisticsAdapter() {
        App.getComponent().inject(this);
        this.investigatorList = new ArrayList<>();
    }

    public void updateAllInvCards(List<Investigator> list) {
        this.investigatorList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investigator, parent, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatisticsViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(investigatorList.get(position).getImageResource(), "drawable", context.getPackageName());
        holder.invPhoto.setImageResource(resourceId);
        holder.invName.setText(investigatorList.get(position).getName());
        holder.invOccupation.setText(investigatorList.get(position).getOccupationEN());
        holder.invCardView.setClickable(false);
        holder.invCardView.setEnabled(false);
        holder.invCardView.setCardBackgroundColor(resources.getColor(R.color.colorTransparent));
        holder.invCardView.setCardElevation(0);

        String expansionResource = null;
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == investigatorList.get(position).getExpansionID()) {
                expansionResource = expansion.getImageResource();
                break;
            }
        }

        resourceId = resources.getIdentifier(expansionResource, "drawable", context.getPackageName());
        holder.invExpansion.setImageResource(resourceId);

        holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return this.investigatorList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}