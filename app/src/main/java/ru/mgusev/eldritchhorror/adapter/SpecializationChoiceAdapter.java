package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Specialization;

public class SpecializationChoiceAdapter extends RecyclerView.Adapter<SpecializationChoiceAdapter.SpecializationViewHolder> implements CompoundButton.OnCheckedChangeListener {

    static class SpecializationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_expansion_image) ImageView itemSpecializationImage;
        @BindView(R.id.item_expansion_switch) Switch itemSpecializationSwitch;

        SpecializationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Specialization> listStorage;

    public SpecializationChoiceAdapter(List<Specialization> listStorage) {
        this.listStorage = listStorage;
    }

    @NonNull
    @Override
    public SpecializationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansion, parent, false);
        return new SpecializationViewHolder(view);
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