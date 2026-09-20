package ru.mgusev.eldritchhorror.ui.adapter.pager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.List;

import ru.mgusev.eldritchhorror.databinding.ItemExpansionBinding;
import ru.mgusev.eldritchhorror.model.Specialization;

public class SpecializationChoiceAdapter extends RecyclerView.Adapter<SpecializationChoiceAdapter.SpecializationViewHolder> implements CompoundButton.OnCheckedChangeListener {

    static class SpecializationViewHolder extends RecyclerView.ViewHolder {
        final ImageView itemSpecializationImage;
        final Switch itemSpecializationSwitch;

        SpecializationViewHolder(ItemExpansionBinding binding) {
            super(binding.getRoot());
            itemSpecializationImage = binding.itemExpansionImage;
            itemSpecializationSwitch = binding.itemExpansionSwitch;
        }
    }

    private List<Specialization> listStorage;

    public SpecializationChoiceAdapter(List<Specialization> listStorage) {
        this.listStorage = listStorage;
    }

    @NonNull
    @Override
    public SpecializationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecializationViewHolder(ItemExpansionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpecializationViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        int resourceId = context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "drawable", context.getPackageName());
        holder.itemSpecializationImage.setImageResource(resourceId);
        holder.itemSpecializationSwitch.setText(listStorage.get(position).getName());
        holder.itemSpecializationSwitch.setChecked(listStorage.get(position).isEnable());
        holder.itemSpecializationSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return listStorage.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getName().contentEquals(compoundButton.getText())) {
                listStorage.get(i).setEnable(b);
                break;
            }
        }
    }

    public List<Specialization> getListStorage() {
        return listStorage;
    }
}
