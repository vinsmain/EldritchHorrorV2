package ru.mgusev.eldritchhorror.support;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Dice;
import timber.log.Timber;

public class DiceDiffUtilCallback extends DiffUtil.Callback {

    private final List<Dice> oldList;
    private final List<Dice> newList;

    public DiceDiffUtilCallback(List<Dice> oldList, List<Dice> newList) {
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
        Dice oldDice = oldList.get(oldItemPosition);
        Dice newDice = newList.get(newItemPosition);
        Timber.d(String.valueOf(oldDice.getFirstValue() == newDice.getFirstValue()));
        Timber.d(String.valueOf(oldDice.getFirstValue()));
        Timber.d(String.valueOf(newDice.getFirstValue()));
        return oldDice.getFirstValue() == newDice.getFirstValue() && oldDice.isAnimationRun() == newDice.isAnimationRun() && oldDice.isAnimationFinish() == newDice.isAnimationFinish();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Dice oldDice = oldList.get(oldItemPosition);
        Dice newDice = newList.get(newItemPosition);
        return oldDice.getFirstValue() == newDice.getFirstValue() && oldDice.getLineMode() == newDice.getLineMode() && oldDice.getRotateMode() == newDice.getRotateMode()
                && oldDice.isAnimationRun() == newDice.isAnimationRun() && oldDice.isAnimationFinish() == newDice.isAnimationFinish();
    }
}