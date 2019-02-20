package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import ru.mgusev.eldritchhorror.api.model.article.Result;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_faq_card_view) CardView faqCardView;
        @BindView(R.id.item_faq_question) TextView faqQuestion;
        @BindView(R.id.item_faq_answer) TextView faqAnswer;

        FaqViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClicked onClick;
    private List<Result> articleList;

    public FaqAdapter() {
        //App.getComponent().inject(this);
        this.articleList = new ArrayList<>();
    }

    public void updateAllFaqCards(List<Result> list) {
        notifyItemRangeRemoved(0, getItemCount());
        this.articleList = list;
        notifyItemRangeInserted(0, getItemCount());
    }

//    public void removeInvCard(int pos, List<Investigator> list) {
//        investigatorList = list;
//        notifyItemRemoved(pos);
//        notifyItemRangeChanged(pos, getItemCount());
//    }
//
//    public void moveInvCard(int oldPosition, int newPosition, List<Investigator> list) {
//        investigatorList = list;
//        notifyItemMoved(oldPosition, newPosition);
//        notifyItemChanged(newPosition);
//    }
//
//    public void updateInvCard(int pos, List<Investigator> list) {
//        investigatorList = list;
//        notifyItemChanged(pos);
//    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        holder.faqQuestion.setText(articleList.get(position).getTitle());
        holder.faqAnswer.setText(android.text.Html.fromHtml(articleList.get(position).getIntrotext()).toString());

        //holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}