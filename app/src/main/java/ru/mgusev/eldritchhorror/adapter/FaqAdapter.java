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
    private Map<String, ImageSpan> iconMap;

    public FaqAdapter(Context context) {
        //App.getComponent().inject(this);
        this.articleList = new ArrayList<>();
        this.context = context;
        initIconMap();
    }

    private void initIconMap() {
        iconMap = new HashMap<>();
        Drawable drawable = context.getResources().getDrawable(R.drawable.lore);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        iconMap.put("#lore", new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM));
        drawable = context.getResources().getDrawable(R.drawable.influence);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        iconMap.put("#influence", new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM));
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
//        holder.faqAnswer.setText(trim(Html.fromHtml(articleList.get(position).getIntrotext())));
//        holder.faqAnswer.setMovementMethod(LinkMovementMethod.getInstance());

        replaceTestIcon(articleList.get(position).getIntrotext(), holder.faqAnswer);

        //holder.invCardView.setOnClickListener(v -> onClick.onItemClick(holder.getAdapterPosition()));
    }

    private void replaceTestIcon(String text, TextView textView) {
//        Drawable d;
//        ImageSpan imageSpan;
        StringBuilder textSB = new StringBuilder(text);
        SpannableStringBuilder ss = (SpannableStringBuilder) Html.fromHtml(text);

        for(Map.Entry<String, ImageSpan> entry : iconMap.entrySet()) {
            String key = entry.getKey();
            ImageSpan value = entry.getValue();

            while (text.contains(key)) {
                int startSpan = text.indexOf(key);
//                d = context.getResources().getDrawable(R.drawable.masks_of_nyarlathotep); // I have kept laugh.png of size 24x24 in drawable folder.
//                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                imageSpan = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                ss.setSpan(value, startSpan - 3, startSpan + (key.length() - 3), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                Timber.d(String.valueOf(ss));
                StringBuilder string = new StringBuilder();
                while (string.length() != key.length()) {
                    string.append(" ");
                }
                text = text.replaceFirst(key, String.valueOf(string)); //replace with two blank spaces.
                Timber.d(text);
                Timber.d(String.valueOf(ss));
            }
            Timber.d(String.valueOf(ss));
        }
        Timber.d(String.valueOf(ss));

        textView.setText(trimSpannable(ss), TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
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