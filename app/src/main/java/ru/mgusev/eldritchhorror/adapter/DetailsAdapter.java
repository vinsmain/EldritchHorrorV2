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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Specialization;

public class DetailsAdapter  extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    static class DetailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_investigator_card) CardView investigatorCard;
        @BindView(R.id.item_investigator_photo) ImageView photo;
        @BindView(R.id.item_investigator_expansion_icon) ImageView expansionIcon;
        @BindView(R.id.item_investigator_specialization_icon) ImageView specializationIcon;
        @BindView(R.id.item_investigator_dead_icon) ImageView deadIcon;

        DetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Inject
    List<Expansion> expansionList;
    @Inject
    List<Specialization> specializationList;
    private Context context;
    private List<Investigator> investigatorList;

    public DetailsAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.investigatorList = new ArrayList<>();
    }

    public void setInvestigatorList(List<Investigator> investigatorList) {
        notifyItemRangeRemoved(0, getItemCount());
        this.investigatorList = investigatorList;
        notifyItemRangeInserted(0, getItemCount());
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investigator_details, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder holder, int position) {
        Resources resources = context.getResources();
        holder.photo.setImageResource(resources.getIdentifier(investigatorList.get(position).getImageResource(), "drawable", context.getPackageName()));
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == investigatorList.get(position).getExpansionID()) {
                holder.expansionIcon.setImageResource(resources.getIdentifier(expansion.getImageResource(), "drawable", context.getPackageName()));
                break;
            }
        }
        for (Specialization specialization : specializationList) {
            if (specialization.getId() == investigatorList.get(position).getSpecialization()) {
                holder.specializationIcon.setImageResource(resources.getIdentifier(specialization.getImageResource(), "drawable", context.getPackageName()));
                break;
            }
        }
        holder.investigatorCard.setCardBackgroundColor(ContextCompat.getColor(context, investigatorList.get(position).getIsReplacement() ? R.color.color_replacement_investigator : R.color.color_starting_investigator));
        holder.deadIcon.setVisibility(investigatorList.get(position).getIsDead() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return investigatorList.size();
    }
}