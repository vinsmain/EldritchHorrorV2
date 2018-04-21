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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.HelperFactory;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
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
    private List<Investigator> listStorage;

    public InvestigatorChoiceAdapter() {
        this.listStorage = new ArrayList<>();
    }

    public void setListStorage(List<Investigator> listStorage) {
        this.listStorage = listStorage;
        notifyItemRangeInserted(0, listStorage.size());
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
        int resourceId = resources.getIdentifier(listStorage.get(position).getImageResource(), "drawable", holder.itemView.getContext().getPackageName());
        holder.invPhoto.setImageResource(resourceId);
        holder.invName.setText(listStorage.get(position).getName());
        holder.invOccupation.setText(listStorage.get(position).getOccupation());

        try {
            String expansionResource = new HelperFactory(context).getStaticHelper().getExpansionDAO().getImageResourceByID(listStorage.get(position).getExpansionID());
            if (expansionResource != null) {
                resourceId = resources.getIdentifier(expansionResource, "drawable", context.getPackageName());
                holder.invExpansion.setImageResource(resourceId);
                holder.invExpansion.setVisibility(View.VISIBLE);
            } else holder.invExpansion.setVisibility(View.GONE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (listStorage.get(position).isStarting()) {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_starting_investigator));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
        } else if (listStorage.get(position).isReplacement()) {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_replacement_investigator));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorText));
        } else {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorText));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText));
        }

        if (listStorage.get(position).isDead()) {
            holder.invDead.setVisibility(View.VISIBLE);
        } else holder.invDead.setVisibility(View.INVISIBLE);

        holder.invCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStorage.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}