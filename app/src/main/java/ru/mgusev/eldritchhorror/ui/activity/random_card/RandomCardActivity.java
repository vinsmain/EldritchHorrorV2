package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;
import timber.log.Timber;

public class RandomCardActivity extends MvpAppCompatActivity implements RandomCardView {
    /**
     * Репозиторий для работы с данными
     */
    @InjectPresenter
    RandomCardPresenter randomCardPresenter;

    @BindView(R.id.random_card_image)
    SimpleDraweeView image;
    @BindView(R.id.random_card_text_block)
    LinearLayout textBlock;
    @BindView(R.id.random_card_category)
    TextView category;
    @BindView(R.id.random_card_type)
    TextView type;
    @BindView(R.id.random_card_title)
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card);

        ButterKnife.bind(this);
    }

    @Override
    public void loadImage(Uri source) {
        Timber.d("load start");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(source)
                //.setResizeOptions(new ResizeOptions(225, 365))
                .build();
        image.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(image.getController())
                        .setImageRequest(request)
                        .build());
        Timber.d("load finish");
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
}
