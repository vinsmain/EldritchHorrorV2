package ru.mgusev.eldritchhorror.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.iigo.library.DiceLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Dice;

public class DiceItemViewHolder extends MvpViewHolder<DiceItemPresenter> implements DiceItemView {
    @BindView(R.id.item_dice_dlv)
    DiceLoadingView diceDLV;
    @Nullable
    private OnCounterClickListener listener;

    public DiceItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        //presenter.startAnimation();

//        minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.onMinusButtonClicked();
//            }
//        });
//        plusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.onPlusButtonClicked();
//            }
//        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDiceClicked();
            }
        });
    }

    public void setListener(@Nullable OnCounterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateDice(Dice dice) {
        diceDLV.setFirstSideDiceNumber(dice.getFirstValue());
        diceDLV.setSecondSideDiceNumber(dice.getSecondValue());
        diceDLV.setThirdSideDiceNumber(dice.getThirdValue());
        diceDLV.setFourthSideDiceNumber(dice.getFourthValue());
    }

    @Override
    public void startAnimation() {
        diceDLV.setDuration(3000);
        diceDLV.start();
    }

    @Override
    public void stopAnimation() {
        diceDLV.setDuration(0);
        diceDLV.stop();
    }
//
//    @Override
//    public void setPlusButtonEnabled(boolean enabled) {
//        plusButton.getDrawable().setTint(enabled ? Color.BLACK : Color.GRAY);
//        plusButton.setClickable(enabled);
//    }
//
//    @Override
//    public void goToDetailView(Counter counter) {
//        if (listener != null) {
//            listener.onCounterClick(counter);
//        }
//    }

    public interface OnCounterClickListener {
        void onCounterClick(Dice dice);
    }
}
