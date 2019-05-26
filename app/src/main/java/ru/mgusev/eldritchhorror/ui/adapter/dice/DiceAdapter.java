package ru.mgusev.eldritchhorror.ui.adapter.dice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DiceAdapterPresenter;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceAdapterView;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.ui.adapter.MvpBaseAdapter;
import timber.log.Timber;

public class DiceAdapter  extends MvpBaseAdapter implements DiceAdapterView {

    @InjectPresenter
    DiceAdapterPresenter presenter;

    private List<Dice> diceList;

    public DiceAdapter(MvpDelegate<?> parentDelegate) {
        super(parentDelegate, String.valueOf(0));

        diceList = new ArrayList<>();
    }

    @Override
    public void setData(List<Dice> diceList) {
        this.diceList = new ArrayList<>();
        this.diceList.addAll(diceList);
        notifyDataSetChanged();
        Timber.d("NOTIFY");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dice, viewGroup, false);
        return new DiceItemHolder(view, getMvpDelegate());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((DiceItemHolder)viewHolder).bind(diceList.get(i));
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ((DiceItemHolder)holder).onAttach();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return diceList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return diceList.size();
    }
}