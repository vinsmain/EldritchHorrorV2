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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Specialization;

public class InvestigatorChoiceAdapter extends RecyclerView.Adapter<InvestigatorChoiceAdapter.InvestigatorViewHolder> {

    static class InvestigatorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_inv_card_view) CardView invCardView;
        @BindView(R.id.item_inv_photo) ImageView invPhoto;
        @BindView(R.id.item_inv_expansion) ImageView invExpansion;
        @BindView(R.id.item_inv_specialization) ImageView invSpecialization;
        @BindView(R.id.item_inv_dead) ImageView invDead;
        @BindView(R.id.item_inv_name) TextView invName;
        @BindView(R.id.item_inv_occupation) TextView invOccupation;

        InvestigatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Inject
    List<Expansion> expansionList;
    @Inject
    List<Specialization> specializationList;
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
        holder.invPhoto.setImageResource(resources.getIdentifier(investigatorList.get(position).getImageResource(), "drawable", context.getPackageName()));
        holder.invName.setText(investigatorList.get(position).getName());
        holder.invOccupation.setText(investigatorList.get(position).getOccupation());

        for (Expansion expansion : expansionList) {
            if (expansion.getId() == investigatorList.get(position).getExpansionID()) {
                holder.invExpansion.setImageResource(resources.getIdentifier(expansion.getImageResource(), "drawable", context.getPackageName()));
                break;
            }
        }

        for (Specialization specialization : specializationList) {
            if (specialization.getId() == investigatorList.get(position).getSpecialization()) {
                holder.invSpecialization.setImageResource(resources.getIdentifier(specialization.getImageResource(), "drawable", context.getPackageName()));
                break;
            }
        }

        if (investigatorList.get(position).getIsStarting()) {
            decorateInvestigatorCard(holder, R.color.color_starting_investigator, R.color.colorPrimaryText, R.color.colorPrimaryText);
        } else if (investigatorList.get(position).getIsReplacement()) {
            decorateInvestigatorCard(holder, R.color.color_replacement_investigator, R.color.colorText, R.color.colorText);
        } else {
            decorateInvestigatorCard(holder, R.color.colorText, R.color.colorPrimaryText, R.color.colorSecondaryText);
        }

        if (investigatorList.get(position).getIsDead()) {
            holder.invDead.setVisibility(View.VISIBLE);
        } else holder.invDead.setVisibility(View.INVISIBLE);

        holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return this.investigatorList.size();
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