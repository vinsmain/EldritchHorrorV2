package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.support.ArticleDiffUtilCallback;
import timber.log.Timber;

import static android.support.v4.util.Preconditions.checkNotNull;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_faq_card_view)
        CardView faqCardView;
        @BindView(R.id.item_faq_question)
        TextView faqQuestion;
        @BindView(R.id.item_faq_answer)
        TextView faqAnswer;

        FaqViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private OnItemClicked onClick;
    private List<Article> articleList;
    private Map<String, Drawable> iconMap;

    public FaqAdapter(Context context) {
        //App.getComponent().inject(this);
        this.articleList = new ArrayList<>();
        this.context = context;
        initIconMap();
    }

    private void initIconMap() {
        iconMap = new HashMap<>();
        iconMap.put("#reckoning", context.getResources().getDrawable(R.drawable.reckoning));
        iconMap.put("#number of players", context.getResources().getDrawable(R.drawable.number_of_players));
        iconMap.put("#lore", context.getResources().getDrawable(R.drawable.lore));
        iconMap.put("#influence", context.getResources().getDrawable(R.drawable.influence));
        iconMap.put("#observation", context.getResources().getDrawable(R.drawable.observation));
        iconMap.put("#strength", context.getResources().getDrawable(R.drawable.strength));
        iconMap.put("#will", context.getResources().getDrawable(R.drawable.will));
    }

    public void setData(List<Article> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ArticleDiffUtilCallback(this.articleList, list));
        this.articleList = list;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqViewHolder holder, int position) {
        holder.faqQuestion.setText(articleList.get(position).getTitle());
        holder.faqQuestion.setVisibility(articleList.get(position).getTitle().contains("#errata") ? View.GONE : View.VISIBLE);

        holder.faqAnswer.setText(trimSpannable(getSpannableFormText(articleList.get(position).getIntrotext())), TextView.BufferType.SPANNABLE);
        holder.faqAnswer.setMovementMethod(LinkMovementMethod.getInstance());
        Timber.d(String.valueOf(articleList.get(position).getId()));
        //if (articleList.get(position).getTags().getItemTags().size() != 0)
            //Timber.d(articleList.get(position).getTags().getItemTags().get(0).getTitle());
//

        //holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    private SpannableStringBuilder getSpannableFormText(String text) {
        String textFormHtml = Html.fromHtml(text).toString();
        SpannableStringBuilder spannable = (SpannableStringBuilder) Html.fromHtml(text);

        for(Map.Entry<String, Drawable> entry : iconMap.entrySet()) {
            String key = entry.getKey();
            Drawable value = entry.getValue();
            value.setBounds(0, 0, value.getIntrinsicWidth(), value.getIntrinsicHeight());

            while (textFormHtml.contains(key)) {
                int startSpan = textFormHtml.indexOf(key);
                spannable.setSpan(new ImageSpan(value, ImageSpan.ALIGN_BOTTOM), startSpan, startSpan + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                StringBuilder string = new StringBuilder();
                while (string.length() != key.length()) {
                    string.append(" ");
                }
                textFormHtml = textFormHtml.replaceFirst(key, String.valueOf(string));
            }
        }
        return spannable;
    }

    private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        checkNotNull(spannable);
        int trimStart = 0;
        int trimEnd = 0;

        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }

        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }

        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}