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
import ru.mgusev.eldritchhorror.model.Expansion;

public class ExpansionChoiceAdapter extends RecyclerView.Adapter<ExpansionChoiceAdapter.ExpansionViewHolder> implements CompoundButton.OnCheckedChangeListener {

    static class ExpansionViewHolder extends RecyclerView.ViewHolder {
        final ImageView itemExpansionImage;
        final Switch itemExpansionSwitch;

        ExpansionViewHolder(ItemExpansionBinding binding) {
            super(binding.getRoot());
            itemExpansionImage = binding.itemExpansionImage;
            itemExpansionSwitch = binding.itemExpansionSwitch;
        }
    }

    private List<Expansion> listStorage;

    public ExpansionChoiceAdapter(List<Expansion> listStorage) {
        this.listStorage = listStorage;
    }

    @NonNull
    @Override
    public ExpansionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpansionViewHolder(ItemExpansionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpansionViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        int resourceId = context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "drawable", context.getPackageName());
        holder.itemExpansionImage.setImageResource(resourceId);
        holder.itemExpansionSwitch.setText(listStorage.get(position).getName());
        holder.itemExpansionSwitch.setChecked(listStorage.get(position).isEnable());
        holder.itemExpansionSwitch.setOnCheckedChangeListener(this);
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

    public List<Expansion> getListStorage() {
        return listStorage;
    }
}
