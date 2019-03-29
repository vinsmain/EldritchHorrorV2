package ru.mgusev.eldritchhorror.support;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.mgusev.eldritchhorror.api.json_model.Article;

public class ArticleDiffUtilCallback extends DiffUtil.Callback {

    private final List<Article> oldList;
    private final List<Article> newList;

    public ArticleDiffUtilCallback(List<Article> oldList, List<Article> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Article oldArticle = oldList.get(oldItemPosition);
        Article newArticle = newList.get(newItemPosition);
        return oldArticle.getId() == newArticle.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Article oldArticle = oldList.get(oldItemPosition);
        Article newArticle = newList.get(newItemPosition);
        return oldArticle.getTitle().equals(newArticle.getTitle())
                && oldArticle.getIntrotext().equals(newArticle.getIntrotext());
    }
}