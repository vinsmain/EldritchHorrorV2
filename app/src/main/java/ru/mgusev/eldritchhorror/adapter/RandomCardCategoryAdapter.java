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
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.support.CardTypeDiffUtilCallback;

public class RandomCardCategoryAdapter extends RecyclerView.Adapter<RandomCardCategoryAdapter.CategoryViewHolder> {

        static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.random_card_category)
        CardView categoryCV;
        @BindView(R.id.random_card_category_name)
        TextView categoryName;

        private CardType item;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random_card_category, parent, false);
        return new RandomCardCategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RandomCardCategoryAdapter.CategoryViewHolder holder, int position) {
        holder.item = cardTypeList.get(position);
        Resources resources = context.getResources();
        holder.categoryName.setText(resources.getIdentifier(holder.item.getNameResourceID(), "string", context.getPackageName()));

        holder.categoryCV.setOnClickListener(v -> {
            onClick.onItemClick(holder.item);
        });
    }

    @Override
    public void onViewRecycled(@NonNull CategoryViewHolder holder) {
        super.onViewRecycled(holder);
        holder.item = null;
    }

    public CardType getType(int pos) {
        return cardTypeList.get(pos);
    }

    @Override
    public int getItemCount() {
        return cardTypeList.size();
    }

    public void setOnClick(OnItemClickedReturnObj onClick) {
        this.onClick = onClick;
    }
}