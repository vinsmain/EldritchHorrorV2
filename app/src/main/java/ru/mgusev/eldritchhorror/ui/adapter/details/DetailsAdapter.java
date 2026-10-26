package ru.mgusev.eldritchhorror.ui.adapter.details;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ItemInvestigatorDetailsBinding;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Specialization;

public class DetailsAdapter  extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    static class DetailsViewHolder extends RecyclerView.ViewHolder {
        final CardView investigatorCard; final ImageView photo; final ImageView expansionIcon; final ImageView specializationIcon; final ImageView deadIcon;

        DetailsViewHolder(ItemInvestigatorDetailsBinding binding) {
            super(binding.getRoot());
            investigatorCard = binding.itemInvestigatorCard; photo = binding.itemInvestigatorPhoto; expansionIcon = binding.itemInvestigatorExpansionIcon; specializationIcon = binding.itemInvestigatorSpecializationIcon; deadIcon = binding.itemInvestigatorDeadIcon;
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
        return new DetailsViewHolder(ItemInvestigatorDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
