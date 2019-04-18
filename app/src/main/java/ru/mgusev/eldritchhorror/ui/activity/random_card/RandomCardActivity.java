package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.content.DialogInterface;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import timber.log.Timber;

public class RandomCardActivity extends MvpAppCompatActivity implements RandomCardView, View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card);

        ButterKnife.bind(this);
        logBtn.setOnClickListener(this);
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
                                //image.setVisibility(View.VISIBLE);
                                scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
                                image.setAspectRatio(0.616438356f);
                                Timber.i("DraweeUpdate " + "Image is fully loaded!");
                            }
                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                //image.setVisibility(View.GONE);
                                //scrollView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                                scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
                                image.setMaxHeight(0);
                                Timber.i("DraweeUpdate " + "Image failed to load: " + throwable.getMessage());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.random_card_log_btn :
                randomCardPresenter.onClickLogBtn();
                break;
        }
    }

    @Override
    public void showLogDialog(List<Card> cardList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.LogDialogTheme));
        builder.setTitle("Карты в рандоме:")
                .setMessage(getLogText(cardList))
                .setIcon(R.drawable.about)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String getLogText(List<Card> cardList) {
        String logText = "";
        for (Card card : cardList) {
            logText = logText + getString(getResources().getIdentifier(card.getNameResourceID(), "string", getPackageName())) + "\n";
        }
        Timber.d(logText);
        return logText;
    }
}
