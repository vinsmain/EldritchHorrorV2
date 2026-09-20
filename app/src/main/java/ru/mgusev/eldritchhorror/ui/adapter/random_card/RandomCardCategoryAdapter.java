package ru.mgusev.eldritchhorror.ui.adapter.random_card;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ItemRandomCardCategoryBinding;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.CardType;

public class RandomCardCategoryAdapter extends RecyclerView.Adapter<RandomCardCategoryAdapter.CategoryViewHolder> {

        static class CategoryViewHolder extends RecyclerView.ViewHolder {
        final CardView categoryCV; final TextView categoryName;

        private CardType item;

        CategoryViewHolder(ItemRandomCardCategoryBinding binding) {
            super(binding.getRoot()); categoryCV = binding.randomCardCategory; categoryName = binding.randomCardCategoryName;
        }
    }

    private Context context;
    private List<CardType> cardTypeList;
    private OnItemClickedReturnObj onClick;

    public RandomCardCategoryAdapter(Context context) {
        App.getComponent().inject(this);
        this.context = context;
        this.cardTypeList = new ArrayList<>();
    }

    public void setData(List<CardType> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CardTypeDiffUtilCallback(this.cardTypeList, list), false);
        cardTypeList.clear();
        cardTypeList.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RandomCardCategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomCardCategoryAdapter.CategoryViewHolder(ItemRandomCardCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RandomCardCategoryAdapter.CategoryViewHolder holder, int position) {
        holder.item = cardTypeList.get(position);
        Resources resources = context.getResources();
        holder.categoryName.setText(resources.getIdentifier(holder.item.getNameResourceID(), "string", context.getPackageName()));

        switch (holder.item.getCategoryResourceID()) {
            case "condition" :
                holder.categoryCV.setBackgroundColor(Color.rgb(90, 80, 80));
                break;
            case "spell" :
                holder.categoryCV.setBackgroundColor(Color.rgb(120, 85, 125));
                break;
            case "unique_asset" :
                holder.categoryCV.setBackgroundColor(Color.rgb(190, 155, 75));
                break;
        }

        if (holder.item.getId() < -1) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.categoryCV.getLayoutParams();
            layoutParams.setMargins(6, 24, 6, 6);
            holder.categoryCV.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.categoryCV.getLayoutParams();
            layoutParams.setMargins(6, 6, 6, 6);
            holder.categoryCV.requestLayout();
        }

        holder.categoryCV.setOnClickListener(v -> onClick.onItemClick(holder.item));
    }

    @Override
    public void onViewRecycled(@NonNull CategoryViewHolder holder) {
        super.onViewRecycled(holder);
        holder.item = null;
    }

    @Override
    public int getItemCount() {
        return cardTypeList.size();
    }

    public void setOnClick(OnItemClickedReturnObj onClick) {
        this.onClick = onClick;
    }
}
