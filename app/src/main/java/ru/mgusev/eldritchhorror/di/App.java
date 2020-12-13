package ru.mgusev.eldritchhorror.di;

import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.SpannableStringBuilder;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.api.json_model.Files;
import ru.mgusev.eldritchhorror.di.component.AppComponent;
import ru.mgusev.eldritchhorror.di.component.DaggerAppComponent;
import ru.mgusev.eldritchhorror.di.module.AppModule;
import ru.mgusev.eldritchhorror.model.AudioState;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.ui.audio_player.AudioPlayerService;
import ru.mgusev.eldritchhorror.utils.log.LogDebugTree;
import timber.log.Timber;

import static androidx.core.util.Preconditions.checkNotNull;

public class App extends Application {

    @Inject
    Repository repository;

    private static AppComponent component;

    private static AudioPlayerService audioPlayerService;
    private AudioPlayerService.PlayerServiceBinder playerServiceBinder;
    private MediaControllerCompat mediaController;
    private MediaControllerCompat.Callback callback;
    private ServiceConnection serviceConnection;
    private Disposable audioPlayerProgressDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new LogDebugTree());
        }

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();

        App.getComponent().inject(this);

        Timber.d("ONCREATE");
        callback = new MediaControllerCompat.Callback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {
                if (state == null)
                    return;
                boolean playing = state.getState() == PlaybackStateCompat.STATE_PLAYING;
                boolean stopped = state.getState() != PlaybackStateCompat.STATE_STOPPED && state.getState() != PlaybackStateCompat.STATE_PAUSED;
                Timber.d(String.valueOf(state.getState()));
                repository.audioPlayerStateChangePublish(new AudioState(audioPlayerService.getCurrentTrack(), playing, audioPlayerService.getTotalDuration(), state.getState() != PlaybackStateCompat.STATE_STOPPED ? audioPlayerService.getCurrentPosition() : 0));
                updateProgress(playing, stopped);
            }
        };

        Timber.d("ONCREATE");
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerServiceBinder = (AudioPlayerService.PlayerServiceBinder) service;
                audioPlayerService = playerServiceBinder.getService();
                System.out.println(audioPlayerService);
                try {
                    mediaController = new MediaControllerCompat(App.this, playerServiceBinder.getMediaSessionToken());
                    mediaController.registerCallback(callback);
                    callback.onPlaybackStateChanged(mediaController.getPlaybackState());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    mediaController = null;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerServiceBinder = null;
                if (mediaController != null) {
                    mediaController.unregisterCallback(callback);
                    mediaController = null;
                }
            }
        };

        Intent intent = new Intent(this, AudioPlayerService.class);
        Objects.requireNonNull(this).bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static Uri resourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }

    public static SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        checkNotNull(spannable);
        int trimStart = 0;
        int trimEnd = 0;

        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }

        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }

        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    public static void updateAudioPlayerState(Files audio) {
        audioPlayerService.updateState(audio);
    }

    public static void audioPlayerSeekTo(long position) {
        audioPlayerService.setCurrentPosition(position);
    }

    private void updateProgress(boolean playing, boolean stopped) {
        if (playerServiceBinder != null) {
            if (playing) {
                audioPlayerProgressDisposable = Observable
                        .interval(100, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(p -> new AudioState(audioPlayerService.getCurrentTrack(), true, audioPlayerService.getTotalDuration(), audioPlayerService.getCurrentPosition()))
                        .subscribeOn(Schedulers.io())
                        .subscribe(state -> repository.audioPlayerProgressChangePublish(state), Timber::e);
            } else {
                if (stopped) {
                    audioPlayerSeekTo(0L);
                    repository.audioPlayerProgressChangePublish(new AudioState(audioPlayerService.getCurrentTrack(), true, audioPlayerService.getTotalDuration(), 0L));
                }
            }
        }
    }

    @Override
    public void onTerminate() {
        if (audioPlayerProgressDisposable != null) audioPlayerProgressDisposable.dispose();
        super.onTerminate();
    }
}