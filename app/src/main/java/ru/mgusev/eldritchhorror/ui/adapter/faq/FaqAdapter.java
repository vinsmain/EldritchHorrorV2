package ru.mgusev.eldritchhorror.ui.adapter.faq;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
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

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ItemFaqBinding;
import ru.mgusev.eldritchhorror.databinding.ItemFaqErrataBinding;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.utils.StatsIcons;

import static androidx.core.util.Preconditions.checkNotNull;

public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        final CardView faqCardView; final TextView faqQuestion; final TextView faqAnswer;

        FaqViewHolder(ItemFaqBinding binding) {
            super(binding.getRoot()); faqCardView = binding.itemFaqCardView; faqQuestion = binding.itemFaqQuestion; faqAnswer = binding.itemFaqAnswer;
        }
    }

    static class ErrataViewHolder extends RecyclerView.ViewHolder {
        final CardView errataCardView; final TextView errataText;

        ErrataViewHolder(ItemFaqErrataBinding binding) {
            super(binding.getRoot()); errataCardView = binding.itemFaqErrataCardView; errataText = binding.itemFaqErrataText;
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
        if (viewType == 1) {
            return new ErrataViewHolder(ItemFaqErrataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new FaqViewHolder(ItemFaqBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                FaqViewHolder faqViewHolder = (FaqViewHolder) holder;
                faqViewHolder.faqQuestion.setText(getSpannableFormText(articleList.get(position).getTitle()), TextView.BufferType.SPANNABLE);
                faqViewHolder.faqAnswer.setText(App.trimSpannable(getSpannableFormText(articleList.get(position).getIntrotext())), TextView.BufferType.SPANNABLE);
                faqViewHolder.faqAnswer.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 1:
                ErrataViewHolder errataViewHolder = (ErrataViewHolder) holder;
                errataViewHolder.errataText.setText(App.trimSpannable(getSpannableFormText(articleList.get(position).getIntrotext())), TextView.BufferType.SPANNABLE);
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

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
