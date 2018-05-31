package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;

public class InvestigatorChoiceAdapter extends RecyclerView.Adapter<InvestigatorChoiceAdapter.InvestigatorViewHolder> {

    static class InvestigatorViewHolder extends RecyclerView.ViewHolder {
        CardView invCardView;
        ImageView invPhoto;
        ImageView invExpansion;
        ImageView invDead;
        TextView invName;
        TextView invOccupation;

        InvestigatorViewHolder(View itemView) {
            super(itemView);
            invCardView = itemView.findViewById(R.id.item_inv_card_view);
            invPhoto = itemView.findViewById(R.id.item_inv_photo);
            invExpansion = itemView.findViewById(R.id.item_inv_expansion);
            invDead = itemView.findViewById(R.id.item_inv_dead);
            invName = itemView.findViewById(R.id.item_inv_name);
            invOccupation = itemView.findViewById(R.id.item_inv_occupation);
        }
    }

    @Inject
    List<Expansion> expansionList;
    private OnItemClicked onClick;
    private List<Investigator> investigatorList;

    public InvestigatorChoiceAdapter() {
        App.getComponent().inject(this);
        this.investigatorList = new ArrayList<>();
    }

    public void updateAllInvCards(List<Investigator> list) {
        notifyItemRangeRemoved(0, getItemCount());
        this.investigatorList = list;
        notifyItemRangeInserted(0, getItemCount());
    }

    public void removeInvCard(int pos, List<Investigator> list) {
        investigatorList = list;
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }

    public void moveInvCard(int oldPosition, int newPosition, List<Investigator> list) {
        investigatorList = list;
        notifyItemMoved(oldPosition, newPosition);
        notifyItemChanged(newPosition);
    }

    public void updateInvCard(int pos, List<Investigator> list) {
        investigatorList = list;
        notifyItemChanged(pos);
    }

    @NonNull
    @Override
    public InvestigatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investigator, parent, false);
        return new InvestigatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InvestigatorViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(investigatorList.get(position).getImageResource(), "drawable", context.getPackageName());
        holder.invPhoto.setImageResource(resourceId);
        holder.invName.setText(investigatorList.get(position).getName());
        holder.invOccupation.setText(investigatorList.get(position).getOccupation());

        String expansionResource = null;
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == investigatorList.get(position).getExpansionID()) {
                expansionResource = expansion.getImageResource();
                break;
            }
        }

        if (expansionResource != null) {
            resourceId = resources.getIdentifier(expansionResource, "drawable", context.getPackageName());
            holder.invExpansion.setImageResource(resourceId);
            holder.invExpansion.setVisibility(View.VISIBLE);
        } else holder.invExpansion.setVisibility(View.GONE);

        if (investigatorList.get(position).isStarting()) {
            decorateInvestigatorCard(holder, R.color.color_starting_investigator, R.color.colorPrimaryText, R.color.colorPrimaryText);
        } else if (investigatorList.get(position).isReplacement()) {
            decorateInvestigatorCard(holder, R.color.color_replacement_investigator, R.color.colorText, R.color.colorText);
        } else {
            decorateInvestigatorCard(holder, R.color.colorText, R.color.colorPrimaryText, R.color.colorSecondaryText);
        }

        if (investigatorList.get(position).isDead()) {
            holder.invDead.setVisibility(View.VISIBLE);
        } else holder.invDead.setVisibility(View.INVISIBLE);

        holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return this.investigatorList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void decorateInvestigatorCard(final InvestigatorViewHolder holder, int cardColor, int nameColor, int occupationColor) {
        holder.invCardView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), cardColor));
        holder.invName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), nameColor));
        holder.invOccupation.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), occupationColor));
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}