package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityRandomCardBinding;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

public class RandomCardActivity extends MvpAppCompatActivity implements RandomCardView {

    private ActivityRandomCardBinding binding;

    @InjectPresenter
    RandomCardPresenter randomCardPresenter;

    SimpleDraweeView image;
    @Nullable
    LinearLayout imageContainer;
    LinearLayout textBlock;
    TextView category;
    TextView type;
    TextView title;
    ImageButton logBtn;
    Button otherCardBtn;
    Button okBtn;

    private AlertDialog logDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRandomCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image = binding.randomCardImage;
        imageContainer = binding.randomCardLinear;
        textBlock = binding.randomCardTextBlock;
        category = binding.randomCardCategory;
        type = binding.randomCardType;
        title = binding.randomCardTitle;
        logBtn = binding.randomCardLogBtn;
        otherCardBtn = binding.randomCardOtherBtn;
        okBtn = binding.randomCardOkBtn;
        logBtn.setOnClickListener(this::onClick);
        otherCardBtn.setOnClickListener(this::onClick);
        okBtn.setOnClickListener(this::onClick);

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
                                image.setAspectRatio(0.616438356f);
                                changeViewSize(imageContainer, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                Timber.i("DraweeUpdate Image is fully loaded!");
                            }
                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                image.setAspectRatio(0f);
                                changeViewSize(imageContainer, 0, 0);
                                Timber.i("DraweeUpdate Image failed to load: %s", throwable.getMessage());
                            }
                        })
                        .build());
    }

    private void changeViewSize(View view, int width, int height) {
        if (view != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = height;
            params.width = width;
            view.setLayoutParams(params);
        }
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

    public void onClick(View view) {
        if (view.getId() == R.id.random_card_log_btn) {
                randomCardPresenter.onClickLogBtn();
        } else if (view.getId() == R.id.random_card_other_btn) {
                randomCardPresenter.onClickOtherBtn();
        } else if (view.getId() == R.id.random_card_ok_btn) {
                finish();
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
    public void showAlertIfOtherCardNone(boolean v) {
        Snackbar.make(findViewById(R.id.random_card_cv), R.string.random_card_no_other_cards, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(logDialog != null && logDialog.isShowing()) logDialog.dismiss();
    }
}
