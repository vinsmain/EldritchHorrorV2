package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import ru.mgusev.eldritchhorror.model.ConditionType;
import ru.mgusev.eldritchhorror.support.ConditionTypeDiffUtilCallback;

public class RandomCardCategoryAdapter extends RecyclerView.Adapter<RandomCardCategoryAdapter.CategoryViewHolder> {

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.random_card_category_name)
        TextView categoryName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<ConditionType> conditionTypeList;

    public RandomCardCategoryAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.conditionTypeList = new ArrayList<>();
    }

    public void setData(List<ConditionType> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ConditionTypeDiffUtilCallback(this.conditionTypeList, list));
        this.conditionTypeList = list;
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
    }

    @Override
    public int getItemCount() {
        return conditionTypeList.size();
    }
}