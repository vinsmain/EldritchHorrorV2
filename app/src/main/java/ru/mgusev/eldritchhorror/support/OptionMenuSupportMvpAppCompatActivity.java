package ru.mgusev.eldritchhorror.support;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpDelegate;

/**
 * Класс является аналогом com.arellomobile.mvp.MvpAppCompatActivity и применяется только для активити с меню
 *
 * Решает следующую проблему:
 * Если требуется выполнять какие-либо действия с MenuItem через getViewState(),
 * то после смены конфигурации onCreateOptionsMenu(Menu menu) вызывается после onStart() и OnResume(),
 * в которых происходит getMvpDelegate().onAttach() в стандарном com.arellomobile.mvp.MvpAppCompatActivity
 * и MenuItem на тот момент еще равен null, как следствие - NPE
 *
 * Особенность:
 * В методах onStart() и OnResume() не вызывается getMvpDelegate().onAttach()
 *
 * В дочернем классе необходимо вызвать getMvpDelegate().onAttach()
 * в onCreateOptionsMenu(Menu menu) перед return true;
 */

@SuppressLint("Registered")
public class OptionMenuSupportMvpAppCompatActivity extends AppCompatActivity {
    private MvpDelegate<? extends OptionMenuSupportMvpAppCompatActivity> mMvpDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }
}
