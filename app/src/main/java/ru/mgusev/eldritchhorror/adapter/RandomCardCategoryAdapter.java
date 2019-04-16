package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.ConditionType;
import ru.mgusev.eldritchhorror.support.ConditionTypeDiffUtilCallback;
import timber.log.Timber;

public class RandomCardCategoryAdapter extends RecyclerView.Adapter<RandomCardCategoryAdapter.CategoryViewHolder> {

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.random_card_category)
        CardView categoryCV;
        @BindView(R.id.random_card_category_name)
        TextView categoryName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<ConditionType> conditionTypeList;
    private OnItemClicked onClick;

    public RandomCardCategoryAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.conditionTypeList = new ArrayList<>();
    }

    public void setData(List<ConditionType> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ConditionTypeDiffUtilCallback(this.conditionTypeList, list), false);
        conditionTypeList = new ArrayList<>();
        conditionTypeList.addAll(list);

        //notifyDataSetChanged();
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RandomCardCategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random_card_category, parent, false);
        return new RandomCardCategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RandomCardCategoryAdapter.CategoryViewHolder holder, int position) {
        Resources resources = context.getResources();
        holder.categoryName.setText(resources.getIdentifier(conditionTypeList.get(position).getNameResourceID(), "string", context.getPackageName()));

        holder.categoryCV.setOnClickListener(v -> {
            //v.startAnimation(animAlpha);
            Timber.d(String.valueOf(conditionTypeList.get(position).getId()));
            Timber.d(String.valueOf(position));
            Timber.d(String.valueOf(holder.categoryName.getText()));
            for (ConditionType conditionType : conditionTypeList) {
                Timber.d(conditionType.getId() + " " + conditionType.getNameResourceID());
            }
            onClick.onItemClick(conditionTypeList.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return conditionTypeList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}