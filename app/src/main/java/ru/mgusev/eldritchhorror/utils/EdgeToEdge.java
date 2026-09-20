package ru.mgusev.eldritchhorror.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Настраивает полноэкранное отображение без перекрытия интерактивных элементов
 * системными панелями.
 */
public final class EdgeToEdge {

    private EdgeToEdge() {
    }

    public static void apply(Activity activity, View topBar, View content) {
        configureSystemBars(activity);
        applyTopInset(topBar);
        applyBottomInset(content);
    }

    /**
     * CollapsingToolbarLayout самостоятельно располагает Toolbar ниже status bar,
     * сохраняя фоновую картинку под системной панелью.
     */
    public static void applyCollapsingHeader(Activity activity, View content) {
        configureSystemBars(activity);
        applyBottomInset(content);
    }

    private static void configureSystemBars(Activity activity) {
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(
                activity.getWindow(), activity.getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        controller.setAppearanceLightNavigationBars(true);
    }

    public static void applyVerticalInsets(View view) {
        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft,
                    paddingTop + insets.top,
                    paddingRight,
                    paddingBottom + insets.bottom);
            return windowInsets;
        });
        requestInsets(view);
    }

    private static void applyTopInset(View view) {
        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int height = layoutParams.height;

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft,
                    paddingTop + insets.top,
                    paddingRight,
                    paddingBottom);
            if (height >= 0) {
                ViewGroup.LayoutParams targetLayoutParams = target.getLayoutParams();
                targetLayoutParams.height = height + insets.top;
                target.setLayoutParams(targetLayoutParams);
            }
            return windowInsets;
        });
        requestInsets(view);
    }

    private static void applyBottomInset(View view) {
        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.navigationBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft,
                    paddingTop,
                    paddingRight,
                    paddingBottom + insets.bottom);
            return windowInsets;
        });
        requestInsets(view);
    }

    private static void requestInsets(View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
            return;
        }

        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View attachedView) {
                attachedView.removeOnAttachStateChangeListener(this);
                ViewCompat.requestApplyInsets(attachedView);
            }

            @Override
            public void onViewDetachedFromWindow(View detachedView) {
            }
        });
    }
}
