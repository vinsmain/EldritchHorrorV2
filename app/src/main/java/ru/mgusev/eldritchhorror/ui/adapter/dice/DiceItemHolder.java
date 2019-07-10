package ru.mgusev.eldritchhorror.ui.adapter.dice;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpDelegate;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DiceItemPresenter;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceItemView;
import ru.mgusev.eldritchhorror.utils.dice_view.DiceLoadingView;
import timber.log.Timber;

public class DiceItemHolder extends RecyclerView.ViewHolder implements DiceItemView {

    private MvpDelegate mParentDelegate;
    private MvpDelegate<DiceItemHolder> mMvpDelegate;
    private Animation rotation;

    @InjectPresenter
    DiceItemPresenter presenter;

    private Dice dice;
    private int successColorId;
    private int defaultColorId;

    @BindView(R.id.item_dice_dlv)
    DiceLoadingView diceDLV;

    @ProvidePresenter
    DiceItemPresenter provideDiceItemPresenter() {
        return new DiceItemPresenter(dice);
    }

    DiceItemHolder(View view, MvpDelegate parentDelegate) {
        super(view);

        ButterKnife.bind(this, view);

        mParentDelegate = parentDelegate;
        rotation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate);
        successColorId = ContextCompat.getColor(diceDLV.getContext(), R.color.color_dice_point);
        defaultColorId = ContextCompat.getColor(diceDLV.getContext(), R.color.color_dice_point_default);
    }

    void bind(Dice dice) {
        if (this.dice != null && this.dice.getId() != dice.getId()) {
            mMvpDelegate = null;
        }
            this.dice = dice;
    }


    void onAttach() {
        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    public void startAnimation() {
        if (dice.isAnimationMode()) {
            diceDLV.setDuration(dice.getStopAnimationTimeOut());
            diceDLV.start();
        } else {
            rotation.setDuration(dice.getStopAnimationTimeOut());
            rotation.setRepeatCount(0);
            diceDLV.startAnimation(rotation);
        }
        Timber.d("START ANIMATION");
    }

    @Override
    public void stopAnimation() {
        rotation.setDuration(0);
        rotation.setRepeatCount(0);
        diceDLV.startAnimation(rotation);

        diceDLV.setDuration(0);
        diceDLV.stop();
        Timber.d("STOP ANIMATION");

        presenter.updateSuccessCount();
    }

    @Override
    public void updateDice() {
        diceDLV.setFirstSideDiceNumber(dice.getFirstValue());
        diceDLV.setSecondSideDiceNumber(dice.getSecondValue());
        diceDLV.setThirdSideDiceNumber(dice.getThirdValue());
        diceDLV.setFourthSideDiceNumber(dice.getFourthValue());

        Timber.d(String.valueOf(dice.getSuccessMode()));

        diceDLV.setFirstSidePointColor(dice.getFirstValue() >= dice.getSuccessMode() ? successColorId : defaultColorId);
        diceDLV.setSecondSidePointColor(dice.getSecondValue() >= dice.getSuccessMode() ? successColorId : defaultColorId);
        diceDLV.setThirdSidePointColor(dice.getThirdValue() >= dice.getSuccessMode() ? successColorId : defaultColorId);
        diceDLV.setFourthSidePointColor(dice.getFourthValue() >= dice.getSuccessMode() ? successColorId : defaultColorId);
    }

    private MvpDelegate<DiceItemHolder> getMvpDelegate() {
        if (mMvpDelegate != null) {
            Timber.d("DELEGATE %s", dice.getId());
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(dice.getId()));
        Timber.d("DELEGATE %s", dice.getId());
        return mMvpDelegate;
    }

    @OnClick(R.id.item_dice_dlv)
    public void onClick() {
        presenter.onClickDice();
    }
}