package ru.mgusev.eldritchhorror.support;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.mgusev.eldritchhorror.model.ConditionType;

public class ConditionTypeDiffUtilCallback extends DiffUtil.Callback {

    private final List<ConditionType> oldList;
    private final List<ConditionType> newList;

    public ConditionTypeDiffUtilCallback(List<ConditionType> oldList, List<ConditionType> newList) {
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
        ConditionType oldType = oldList.get(oldItemPosition);
        ConditionType newType = newList.get(newItemPosition);
        return oldType.getId() == newType.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ConditionType oldType = oldList.get(oldItemPosition);
        ConditionType newType = newList.get(newItemPosition);
        return oldType.getId() == newType.getId();
    }
}