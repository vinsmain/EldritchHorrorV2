package ru.mgusev.eldritchhorror.adapter;

import ru.mgusev.eldritchhorror.model.Dice;

public interface DiceItemView {
    void updateDice(Dice dice);

    void startAnimation();

    void stopAnimation();

//    void setCounterName(String name);
//
//    void setCounterValue(int value);
//
//    void setMinusButtonEnabled(boolean enabled);
//
//    void setPlusButtonEnabled(boolean enabled);
//
//    void goToDetailView(Counter counter);

}
