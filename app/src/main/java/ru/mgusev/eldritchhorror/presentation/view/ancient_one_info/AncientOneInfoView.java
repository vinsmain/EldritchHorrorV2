package ru.mgusev.eldritchhorror.presentation.view.ancient_one_info;

import android.widget.SeekBar;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.api.json_model.Files;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.strategy.AddToEndSingleByTagStateStrategy;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AncientOneInfoView extends MvpView {

    String ROTATE_ICON_TAG = "rotate icon";

    void initAncientOneSpinner(List<String> ancientOneNameList);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ROTATE_ICON_TAG)
    void startRefreshIconRotate();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ROTATE_ICON_TAG)
    void stopRefreshIconRotate();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showAlert(int messageResId);

    void setNoneResultsTVVisibility(boolean isVisible);

    void setAncientOneSpinnerPosition(int position);

    void setAncientOneImage(AncientOne ancientOne);

    void expandInfoView(boolean isExpand);

    void expandStoryView(boolean isExpand);

    void setInfoText(String text);

    void setStoryText(String text);

    void setVisibilityContent(boolean visible);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateAudioPlayerState(Files audio);

    void setVisibilityAudioPlayer(boolean visible);

    void setVisibilityInfoAudioButton(boolean visible);

    void setVisibilityStoryAudioButton(boolean visible);

    void changeAudioInfoIcon(boolean isPlaying);

    void changeAudioStoryIcon(boolean isPlaying);

    void setAudioTitle(String title);

    void updateDuration(long total, long current);

    void changeAudioControlButtonIcon(boolean playing);

    void onProgressChanged(SeekBar seekBar, int i, boolean b);
}