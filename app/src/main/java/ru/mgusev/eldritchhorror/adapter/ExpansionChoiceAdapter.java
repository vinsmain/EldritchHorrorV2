package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Expansion;

public class ExpansionChoiceAdapter extends RecyclerView.Adapter<ExpansionChoiceAdapter.ExpansionViewHolder> implements CompoundButton.OnCheckedChangeListener {

    static class ExpansionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_expansion_image) ImageView itemExpansionImage;
        @BindView(R.id.item_expansion_switch) Switch itemExpansionSwitch;

        ExpansionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Expansion> listStorage;

    public ExpansionChoiceAdapter(List<Expansion> listStorage) {
        this.listStorage = listStorage;
    }

    @NonNull
    @Override
    public ExpansionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansion, parent, false);
        return new ExpansionViewHolder(view);
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