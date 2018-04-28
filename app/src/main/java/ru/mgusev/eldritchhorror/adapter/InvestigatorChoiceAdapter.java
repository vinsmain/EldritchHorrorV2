package ru.mgusev.eldritchhorror.adapter;


import android.content.Context;
import android.content.res.Resources;
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

import ru.mgusev.eldritchhorror.R;
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

    private OnItemClicked onClick;
    private List<Investigator> investigatorList;
    private List<Expansion> expansionList;

    public InvestigatorChoiceAdapter() {
        this.investigatorList = new ArrayList<>();
    }

    public void setListStorage(List<Investigator> investigatorList, List<Expansion> expansionList) {
        //notifyItemRangeRemoved(0, this.investigatorList.size());
        boolean b = false;
        if (this.investigatorList.size() != 0) b = true;
        this.investigatorList = investigatorList;
        this.expansionList = expansionList;
        if (!b) notifyItemRangeInserted(0, investigatorList.size());
        else notifyItemRangeChanged(0, 4);
        System.out.println("NOTIFY ADAPTER");
    }


    @Override
    public InvestigatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investigator, parent, false);
        return new InvestigatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InvestigatorViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Resources resources = holder.itemView.getResources();
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
        return investigatorList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
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