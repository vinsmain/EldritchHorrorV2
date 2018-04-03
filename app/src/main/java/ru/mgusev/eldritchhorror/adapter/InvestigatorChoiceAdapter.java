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
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.HelperFactory;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Investigator;

public class InvestigatorChoiceAdapter extends RecyclerView.Adapter<InvestigatorChoiceAdapter.InvestigatorViewHolder> {

    static class InvestigatorViewHolder extends RecyclerView.ViewHolder {
        CardView invCardView;
        ImageView invPhoto;
        ImageView invExpansionImage;
        ImageView invDead;
        TextView invName;
        TextView invOccupation;

        InvestigatorViewHolder(View itemView) {
            super(itemView);
            invCardView = itemView.findViewById(R.id.item_inv_card_view);
            invPhoto = itemView.findViewById(R.id.item_inv_photo);
            invExpansionImage = itemView.findViewById(R.id.item_inv_expansion);
            invDead = itemView.findViewById(R.id.item_inv_dead);
            invName = itemView.findViewById(R.id.item_inv_name);
            invOccupation = itemView.findViewById(R.id.item_inv_occupation);
        }
    }

    private OnItemClicked onClick;
    private List<Investigator> listStorage;
    private Context context;

    public InvestigatorChoiceAdapter(Context context, List<Investigator> listStorage) {
        this.context = context;
        this.listStorage = listStorage;
    }

    @Override
    public InvestigatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_investigator, parent, false);
        return new InvestigatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InvestigatorViewHolder holder, int position) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(listStorage.get(position).imageResource, "drawable", context.getPackageName());
        holder.invPhoto.setImageResource(resourceId);
        holder.invName.setText(listStorage.get(position).getName());
        holder.invOccupation.setText(listStorage.get(position).getOccupation());

        try {
            String expansionResource = HelperFactory.getStaticHelper().getExpansionDAO().getImageResourceByID(listStorage.get(position).expansionID);
            if (expansionResource != null) {
                resourceId = resources.getIdentifier(expansionResource, "drawable", context.getPackageName());
                holder.invExpansionImage.setImageResource(resourceId);
                holder.invExpansionImage.setVisibility(View.VISIBLE);
            } else holder.invExpansionImage.setVisibility(View.GONE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (listStorage.get(position).isStarting) {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_starting_investigator));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
        } else if (listStorage.get(position).isReplacement) {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_replacement_investigator));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorText));
        } else {
            holder.invCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorText));
            holder.invName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
            holder.invOccupation.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText));
        }

        if (listStorage.get(position).isDead) {
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