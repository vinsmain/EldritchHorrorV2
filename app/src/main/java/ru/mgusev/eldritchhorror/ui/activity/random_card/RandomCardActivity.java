package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

public class RandomCardActivity extends MvpAppCompatActivity implements RandomCardView {
    /**
     * Репозиторий для работы с данными
     */
    @InjectPresenter
    RandomCardPresenter randomCardPresenter;

    @BindView(R.id.random_card_image)
    SimpleDraweeView image;
    @BindView(R.id.random_card_scroll)
    ScrollView scrollView;
    @BindView(R.id.random_card_text_block)
    LinearLayout textBlock;
    @BindView(R.id.random_card_category)
    TextView category;
    @BindView(R.id.random_card_type)
    TextView type;
    @BindView(R.id.random_card_title)
    TextView title;
    @BindView(R.id.random_card_log_btn)
    ImageButton logBtn;
    @BindView(R.id.random_card_other_btn)
    Button otherCardBtn;
    @BindView(R.id.random_card_ok_btn)
    Button okBtn;

    private AlertDialog logDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card);

        ButterKnife.bind(this);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }
    }

    @Override
    public void loadImage(Uri source) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(source)
                //.setResizeOptions(new ResizeOptions(225, 365))
                .build();
        image.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(image.getController())
                        .setImageRequest(request)
                        .setControllerListener(new BaseControllerListener<ImageInfo>() {
                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
                                image.setAspectRatio(0.616438356f);
                                Timber.i("DraweeUpdate Image is fully loaded!");
                            }
                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
                                //scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
                                image.setAspectRatio(0f);
                                Timber.i("DraweeUpdate Image failed to load: %s", throwable.getMessage());
                            }
                        })
                        .build());
    }

    @Override
    public void setTitle(String resource_id) {
        title.setText(getResources().getIdentifier(resource_id, "string", getPackageName()));
    }

    @Override
    public void setCategory(String resource_id) {
        category.setText(getResources().getIdentifier(resource_id, "string", getPackageName()));
    }

    @Override
    public void setType(String resource_id) {
        type.setText(getResources().getIdentifier(resource_id, "string", getPackageName()));
    }

    @OnClick ({R.id.random_card_log_btn, R.id.random_card_other_btn, R.id.random_card_ok_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.random_card_log_btn :
                randomCardPresenter.onClickLogBtn();
                break;
            case R.id.random_card_other_btn :
                randomCardPresenter.onClickOtherBtn();
                break;
            case R.id.random_card_ok_btn :
                finish();
                break;
        }
    }

    @Override
    public void showLogDialog(List<Card> cardList) {
        if (logDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.LogDialogTheme));
            builder.setTitle(R.string.random_card_log_dialog_header)
                    .setMessage(getLogText(cardList))
                    .setIcon(R.drawable.about)
                    .setCancelable(false)
                    .setNegativeButton(R.string.random_card_ok, (dialog, id) -> randomCardPresenter.dismissLogDialog());
            logDialog = builder.show();
        }
    }

    @Override
    public void hideLogDialog() {
        logDialog = null;
        //Delete showLogDialog() from currentState with DismissDialogStrategy
    }

    private String getLogText(List<Card> cardList) {
        StringBuilder logText = new StringBuilder();
        for (Card card : cardList) {
            logText.append(getString(getResources().getIdentifier(card.getNameResourceID(), "string", getPackageName()))).append("\n");
        }
        Timber.d(logText.toString());
        return logText.toString();
    }

    @Override
    public void showAlertIfOtherCardNone() {
        Toast toast = Toast.makeText(this, R.string.random_card_no_other_cards, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(logDialog != null && logDialog.isShowing()) logDialog.dismiss();
    }
}