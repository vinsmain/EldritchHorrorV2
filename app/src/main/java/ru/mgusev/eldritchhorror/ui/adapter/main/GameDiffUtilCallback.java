package ru.mgusev.eldritchhorror.ui.adapter.main;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.repository.Repository;

public class GameDiffUtilCallback extends DiffUtil.Callback {

    @Inject
    Repository repository;

    private final List<Game> oldList;
    private final List<Game> newList;

    public GameDiffUtilCallback(List<Game> oldList, List<Game> newList) {
        App.getComponent().inject(this);
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
        Game oldItem = oldList.get(oldItemPosition);
        Game newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Game oldItem = oldList.get(oldItemPosition);
        Game newItem = newList.get(newItemPosition);
        return oldItem.getAncientOneID() == newItem.getAncientOneID() && oldItem.getDate() == newItem.getDate() && oldItem.getIsWinGame() == newItem.getIsWinGame() &&
                oldItem.getIsDefeatByAwakenedAncientOne() == newItem.getIsDefeatByAwakenedAncientOne() && oldItem.getIsDefeatByElimination() == newItem.getIsDefeatByElimination() &&
                oldItem.getIsDefeatByMythosDepletion() == newItem.getIsDefeatByMythosDepletion() && oldItem.getIsDefeatByRumor() == newItem.getIsDefeatByRumor() &&
                oldItem.getIsDefeatBySurrender() == newItem.getIsDefeatBySurrender() && oldItem.getIsDefeatByPrelude() == newItem.getIsDefeatByPrelude() &&
                oldItem.getPlayersCount() == newItem.getPlayersCount() && oldItem.getScore() == newItem.getScore() && oldItem.getComment().equals(newItem.getComment()) &&
                oldItem.getImageFileList().size() == newItem.getImageFileList().size();
    }
}