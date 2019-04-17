package ru.mgusev.eldritchhorror.support;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.mgusev.eldritchhorror.model.CardType;

public class CardTypeDiffUtilCallback extends DiffUtil.Callback {

    private final List<CardType> oldList;
    private final List<CardType> newList;

    public CardTypeDiffUtilCallback(List<CardType> oldList, List<CardType> newList) {
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
        CardType oldType = oldList.get(oldItemPosition);
        CardType newType = newList.get(newItemPosition);
        return oldType.getId() == newType.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CardType oldType = oldList.get(oldItemPosition);
        CardType newType = newList.get(newItemPosition);
        return oldType.getNameResourceID().equals(newType.getNameResourceID());
    }
}