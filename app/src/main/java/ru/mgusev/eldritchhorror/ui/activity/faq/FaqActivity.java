package ru.mgusev.eldritchhorror.ui.activity.faq;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.FaqAdapter;
import ru.mgusev.eldritchhorror.api.model.article.Result;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import ru.mgusev.eldritchhorror.presentation.view.faq.FaqView;

public class FaqActivity extends MvpAppCompatActivity implements FaqView {

    @InjectPresenter
    FaqPresenter faqPresenter;

    @BindView(R.id.faq_rv) RecyclerView articleListRV;

    private FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        articleListRV.setLayoutManager(gridLayoutManager);
        //articleListRV.setHasFixedSize(true);

        adapter = new FaqAdapter();
        articleListRV.setAdapter(adapter);
    }

    @Override
    public void setDataToAdapter(List<Result> articleList) {
        adapter.updateAllFaqCards(articleList);
    }
}
