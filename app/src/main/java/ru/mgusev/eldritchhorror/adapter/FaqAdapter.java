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
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.support.ArticleDiffUtilCallback;
import ru.mgusev.eldritchhorror.util.StatsIcons;

import static android.support.v4.util.Preconditions.checkNotNull;

public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    static class ErrataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_faq_errata_card_view)
        CardView errataCardView;
        @BindView(R.id.item_faq_errata_text)
        TextView errataText;

        ErrataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Inject
    StatsIcons statsIcons;

    private Context context;
    private OnItemClicked onClick;
    private List<Article> articleList;


    public FaqAdapter(Context context) {
        App.getComponent().inject(this);
        this.articleList = new ArrayList<>();
        this.context = context;
    }


    public void setData(List<Article> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ArticleDiffUtilCallback(this.articleList, list));
        this.articleList = list;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (articleList.get(position).getTitle().contains("#errata"))
            return 1;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: return new FaqViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false));
            case 1: return new ErrataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq_errata, parent, false));
            default: return new FaqViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                FaqViewHolder faqViewHolder = (FaqViewHolder) holder;
                faqViewHolder.faqQuestion.setText(getSpannableFormText(articleList.get(position).getTitle()), TextView.BufferType.SPANNABLE);
                faqViewHolder.faqAnswer.setText(trimSpannable(getSpannableFormText(articleList.get(position).getIntrotext())), TextView.BufferType.SPANNABLE);
                faqViewHolder.faqAnswer.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 1:
                ErrataViewHolder errataViewHolder = (ErrataViewHolder) holder;
                errataViewHolder.errataText.setText(trimSpannable(getSpannableFormText(articleList.get(position).getIntrotext())), TextView.BufferType.SPANNABLE);
                errataViewHolder.errataText.setMovementMethod(LinkMovementMethod.getInstance());
                break;
        }
    }

    private SpannableStringBuilder getSpannableFormText(String text) {
        String textFormHtml = Html.fromHtml(text).toString();
        SpannableStringBuilder spannable = (SpannableStringBuilder) Html.fromHtml(text);

        for(Map.Entry<String, Drawable> entry : statsIcons.getIconMap().entrySet()) {
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