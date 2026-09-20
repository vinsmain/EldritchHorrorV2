package ru.mgusev.eldritchhorror.ui.activity.ancient_one_info;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityAncientOneInfoBinding;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.api.json_model.Files;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.presenter.ancient_one_info.AncientOneInfoPresenter;
import ru.mgusev.eldritchhorror.presentation.view.ancient_one_info.AncientOneInfoView;
import ru.mgusev.eldritchhorror.ui.activity.OptionMenuSupportMvpAppCompatActivity;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;
import ru.mgusev.eldritchhorror.utils.dropdowntextview.DropdownTextView;
import timber.log.Timber;

/**
 * Activity для вывода данных типа {@link Article}
 * Включает поиск по данным и ручное обновление данных
 */
public class AncientOneInfoActivity extends OptionMenuSupportMvpAppCompatActivity implements AncientOneInfoView, MaterialSpinner.OnItemSelectedListener, AppCompatSeekBar.OnSeekBarChangeListener {

    private ActivityAncientOneInfoBinding binding;

    /**
     * Presenter
     */
    @InjectPresenter
    AncientOneInfoPresenter presenter;
    /**
     * Toolbar
     */
    Toolbar toolbar;
    MaterialSpinner ancientOneSpinner;
    AppCompatImageView imageAncientOne;
    DropdownTextView expandableInfo;
    DropdownTextView expandableStory;
    NestedScrollView scrollView;
    AppCompatTextView noneResultsTV;

    AppCompatImageView btnAudioInfo;
    AppCompatImageView btnAudioStory;

    CardView audioPlayerContainer;
    AppCompatTextView textAudioTitle;
    AppCompatSeekBar audioProgressBar;
    AppCompatTextView audioCurrentDuration;
    AppCompatTextView audioTotalDuration;
    FloatingActionButton audioControlButton;
    AppCompatTextView textAudioCopyright;

    /**
     * Сообщение, выводимое если список {@link Article} пуст
     */
    /**
     * Пункт меню "Обновить данные"
     */
    private MenuItem refreshItem;
    /**
     * Snackbar для вывода сообщений о успешной загрузке данных или об ошибке
     */
    private Snackbar alertSnackbar;

    private ArrayAdapter<String> ancientOneAdapter;

    /**
     * Инициирует {@link #toolbar}
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAncientOneInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.toolbar;
        ancientOneSpinner = binding.forgottenEndingsAncientOneSpinner;
        imageAncientOne = binding.imageAncientOne;
        expandableInfo = binding.expandableInfo;
        expandableStory = binding.expandableStory;
        scrollView = binding.ancientOneInfoScrollView;
        noneResultsTV = binding.noneResultsTV;
        btnAudioInfo = binding.btnAudioInfo;
        btnAudioStory = binding.btnAudioStory;
        audioPlayerContainer = binding.audioCard;
        textAudioTitle = binding.audioPlayer.textAudioTitle;
        audioProgressBar = binding.audioPlayer.audioProgressBar;
        audioCurrentDuration = binding.audioPlayer.textAudioCurrentDuration;
        audioTotalDuration = binding.audioPlayer.textAudioTotalDuration;
        audioControlButton = binding.audioPlayer.btnAudioControl;
        textAudioCopyright = binding.audioPlayer.textAudioCopyright;
        binding.viewClickableInfo.setOnClickListener(this::onClick);
        binding.viewClickableStory.setOnClickListener(this::onClick);
        audioControlButton.setOnClickListener(this::onClickAudioBtn);
        btnAudioInfo.setOnClickListener(this::onClickAudioBtn);
        btnAudioStory.setOnClickListener(this::onClickAudioBtn);
        EdgeToEdge.apply(this, toolbar, binding.ancientOneInfoContent);
        EdgeToEdge.applyHorizontalInsets(ancientOneSpinner);
        EdgeToEdge.applyHorizontalInsets(scrollView);
        EdgeToEdge.applyHorizontalInsets(noneResultsTV);

        initToolbar();
        initAncientOneSpinner();

        if (getIntent().getData() != null) {
            presenter.setAncientOneSpinnerCurrentPositionFromIntent(Integer.parseInt(getIntent().getData().toString()), false);
            Timber.d(getIntent().getData().toString());
        }
    }

    /**
     * Инициирует {@link #toolbar}
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.before_the_storm_starts);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initAncientOneSpinner() {
        ancientOneAdapter = new ArrayAdapter<>(this, R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ancientOneSpinner.setAdapter(ancientOneAdapter);
        ancientOneSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneNameList) {
        ancientOneAdapter.clear();
        ancientOneAdapter.addAll(ancientOneNameList);
        ancientOneAdapter.notifyDataSetChanged();
    }

    /**
     * Создает меню
     * getMvpDelegate().onAttach() необходим для присоединения {@link AncientOneInfoActivity} к {@link AncientOneInfoPresenter} только после создания меню
     * Подробное описание {@link OptionMenuSupportMvpAppCompatActivity}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        Timber.d(String.valueOf(getMvpDelegate()));

        refreshItem = menu.findItem(R.id.refresh);

        getMvpDelegate().onAttach();
        return true;
    }

    /**
     * Принудительно вызывает getMvpDelegate().onAttach(), если Activity была восстановлена, а не создана с нуля
     * Условие добавлено для того, чтобы анимация для {@link #refreshItem} работала корректно после восстановления Activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    /**
     * Принудительно вызывает getMvpDelegate().onAttach(), если Activity было восстановлена, а не создано с нуля
     * Условие добавлено для того, чтобы анимация для {@link #refreshItem} работала корректно после восстановления Activity
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    /**
     * Обрабатывает нажатия на пункты меню
     *
     * @param item пункт меню
     * @return параметр, подтверждающий нажатие на пункт меню
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
                presenter.clickRefresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Запускает анимацию вращения иконки {@link #refreshItem}
     */
    @Override
    public void startRefreshIconRotate() {
        Timber.d(String.valueOf(getMvpDelegate()));
        if (refreshItem != null) {
            /* Attach a rotating ImageView to the refresh item as an ActionView */
            LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView) inflater.inflate(R.layout.menu_item_refresh, null);

            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);

            refreshItem.setActionView(iv);
        }
    }

    /**
     * Останавливает анимацию вращения иконки {@link #refreshItem}
     */
    @Override
    public void stopRefreshIconRotate() {
        Timber.d(String.valueOf(refreshItem));
        if (refreshItem != null && refreshItem.getActionView() != null) {
            refreshItem.getActionView().clearAnimation();
            refreshItem.setActionView(null);
        }
    }

    /**
     * Выводит {@link #alertSnackbar}
     *
     * @param messageResId id строкового ресурса, который будет выведен
     */
    @Override
    public void showAlert(int messageResId) {
        alertSnackbar = Snackbar.make(scrollView, messageResId, Snackbar.LENGTH_LONG);
        alertSnackbar.show();
    }

    /**
     * Управляет видимостью TextView {@link }
     *
     * @param isVisible параметр, определяющий видимость
     */
    @Override
    public void setNoneResultsTVVisibility(boolean isVisible) {
        noneResultsTV.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemSelected(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
        presenter.onAncientOneSelected(materialSpinner.getSelection());
    }

    @Override
    public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {

    }

    @Override
    public void setAncientOneSpinnerPosition(int position) {
        Timber.d(String.valueOf(position));
        ancientOneSpinner.setSelection(position);
    }

    @Override
    public void setAncientOneImage(AncientOne ancientOne) {
        imageAncientOne.setImageResource(getResources().getIdentifier(ancientOne.getImageResource(), "drawable", getPackageName()));
    }

    public void onClick(View view) {
        if (view.getId() == R.id.view_clickable_info) {
                presenter.expandableInfoOnClick();
        } else if (view.getId() == R.id.view_clickable_story) {
                presenter.expandableStoryOnClick();
        }
    }

    public void onClickAudioBtn(View view) {
        if (view.getId() == R.id.btnAudioControl) {
                presenter.onAudioControlButtonClicked();
        } else if (view.getId() == R.id.btn_audio_info) {
                presenter.onAudioInfoButtonClicked();
        } else if (view.getId() == R.id.btn_audio_story) {
                presenter.onAudioStoryButtonClicked();
        }
    }

    @Override
    public void expandInfoView(boolean isExpand) {
        Timber.d(String.valueOf(isExpand));
        if (isExpand) {
            expandableInfo.expand(true);
        } else {
            expandableInfo.collapse(false);
        }
    }

    @Override
    public void expandStoryView(boolean isExpand) {
        if (isExpand) {
            expandableStory.expand(true);
        } else {
            expandableStory.collapse(true);
        }
    }

    @Override
    public void setInfoText(String text) {
        expandableInfo.setHtmlContent(text);
    }

    @Override
    public void setStoryText(String text) {
        expandableStory.setHtmlContent(text);
    }

    @Override
    public void setVisibilityContent(boolean visible) {
        scrollView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    //Audio

    @Override
    public void updateAudioPlayerState(Files audio) {
        App.updateAudioPlayerState(audio);
    }

    @Override
    public void setVisibilityAudioPlayer(boolean visible) {
        audioPlayerContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            audioProgressBar.setOnSeekBarChangeListener(this);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                textAudioCopyright.setText(getResources().getString(R.string.audio_copyright_land));
        } else
            audioProgressBar.setOnSeekBarChangeListener(null);
    }

    @Override
    public void setVisibilityInfoAudioButton(boolean visible) {
        btnAudioInfo.setVisibility(visible ? View.VISIBLE : View.GONE);
        expandableInfo.setTitleText(visible ? "           Краткая информация" : "Краткая информация");
    }

    @Override
    public void setVisibilityStoryAudioButton(boolean visible) {
        btnAudioStory.setVisibility(visible ? View.VISIBLE : View.GONE);
        expandableStory.setTitleText(visible ? "           Вступительная история" : "Вступительная история");
    }

    @Override
    public void changeAudioInfoIcon(boolean isPlaying) {
        btnAudioInfo.setImageResource(isPlaying ? R.drawable.pause : R.drawable.play);
    }

    @Override
    public void changeAudioStoryIcon(boolean isPlaying) {
        btnAudioStory.setImageResource(isPlaying ? R.drawable.pause : R.drawable.play);
    }

    @Override
    public void setAudioTitle(String title) {
        textAudioTitle.setText(title);
    }

    @Override
    public void updateDuration(long total, long current) {
        audioTotalDuration.setText(getDuration(total));
        audioCurrentDuration.setText(getDuration(current));
        audioProgressBar.setMax((int) total);
        audioProgressBar.setProgress((int) current);
    }

    @Override
    public void changeAudioControlButtonIcon(boolean isPlaying) {
        Timber.d(String.valueOf(isPlaying));
        audioControlButton.setImageResource(isPlaying ? R.drawable.pause : R.drawable.play);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            audioCurrentDuration.setText(getDuration(i));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        presenter.onUserTrackingTouch(true);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        App.audioPlayerSeekTo(seekBar.getProgress() * 1000);
        presenter.onUserTrackingTouch(false);
    }

    public static String getDuration(long durationInSeconds) {
        //Timber.d(String.valueOf(durationInSeconds));
        return String.format(Locale.getDefault(), "%02d:%02d", (durationInSeconds % 3600) / 60, (durationInSeconds % 60));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getData().toString());
        if (intent.getData() != null) {
            presenter.setAncientOneSpinnerCurrentPositionFromIntent(Integer.parseInt(intent.getData().toString()), true);
        }
    }
}
