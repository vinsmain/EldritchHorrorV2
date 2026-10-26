package ru.mgusev.eldritchhorror.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;


import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

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
    public static void applyCollapsingHeader(
            Activity activity, Toolbar toolbar, View content, View startHeaderView, View endHeaderView) {
        configureSystemBars(activity);
        final int contentPaddingLeft = content.getPaddingLeft();
        final int contentPaddingTop = content.getPaddingTop();
        final int contentPaddingRight = content.getPaddingRight();
        final int contentPaddingBottom = content.getPaddingBottom();
        final int toolbarPaddingLeft = toolbar.getPaddingLeft();
        final int toolbarPaddingTop = toolbar.getPaddingTop();
        final int toolbarPaddingRight = toolbar.getPaddingRight();
        final int toolbarPaddingBottom = toolbar.getPaddingBottom();
        final float startHeaderTranslationX = startHeaderView.getTranslationX();
        final float endHeaderTranslationX = endHeaderView.getTranslationX();

        ViewCompat.setOnApplyWindowInsetsListener(content, (target, windowInsets) -> {
            Insets insets = windowInsets.getInsetsIgnoringVisibility(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    contentPaddingLeft,
                    contentPaddingTop,
                    contentPaddingRight,
                    contentPaddingBottom + insets.bottom);
            toolbar.setPadding(
                    toolbarPaddingLeft + insets.left,
                    toolbarPaddingTop,
                    toolbarPaddingRight + insets.right,
                    toolbarPaddingBottom);
            startHeaderView.setTranslationX(startHeaderTranslationX + insets.left);
            endHeaderView.setTranslationX(endHeaderTranslationX - insets.right);
            return windowInsets;
        });
        requestInsets(content);
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
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft + insets.left,
                    paddingTop + insets.top,
                    paddingRight + insets.right,
                    paddingBottom + insets.bottom);
            return windowInsets;
        });
        requestInsets(view);
    }

    /**
     * Сохраняет фон view на всю ширину экрана и сдвигает только его содержимое
     * от левого выреза и правой системной панели.
     */
    public static void applyHorizontalInsets(View view) {
        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft + insets.left,
                    paddingTop,
                    paddingRight + insets.right,
                    paddingBottom);
            return windowInsets;
        });
        requestInsets(view);
    }

    /**
     * Защищает содержимое шапки от status bar и выреза, сохраняя её фон на всю ширину.
     */
    public static void applyTopAndHorizontalInsets(View view) {
        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft + insets.left,
                    paddingTop + insets.top,
                    paddingRight + insets.right,
                    paddingBottom);
            return windowInsets;
        });
        requestInsets(view);
    }

    /**
     * Отступает иконки и подписи пунктов navigation drawer, не уменьшая сам drawer.
     */
    public static void applyNavigationMenuInsets(NavigationView navigationView) {
        final int itemHorizontalPadding = navigationView.getItemHorizontalPadding();

        ViewCompat.setOnApplyWindowInsetsListener(navigationView, (target, windowInsets) -> {
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            int sideInset = Math.max(insets.left, insets.right);
            navigationView.setItemHorizontalPadding(itemHorizontalPadding + sideInset);
            return windowInsets;
        });
        requestInsets(navigationView);
    }

    /**
     * Сдвигает плавающую кнопку от системных зон, не уменьшая её фон или размер.
     */
    public static void applyFloatingActionButtonInsets(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        final int marginStart = MarginLayoutParamsCompat.getMarginStart(marginLayoutParams);
        final int marginTop = marginLayoutParams.topMargin;
        final int marginEnd = MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
        final int marginBottom = marginLayoutParams.bottomMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            ViewGroup.MarginLayoutParams targetLayoutParams =
                    (ViewGroup.MarginLayoutParams) target.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(targetLayoutParams, marginStart + insets.left);
            targetLayoutParams.topMargin = marginTop;
            MarginLayoutParamsCompat.setMarginEnd(targetLayoutParams, marginEnd + insets.right);
            targetLayoutParams.bottomMargin = marginBottom + insets.bottom;
            target.setLayoutParams(targetLayoutParams);
            return windowInsets;
        });
        requestInsets(view);
    }

    /**
     * Добавляет безопасные боковые поля элементу, привязанному к краю контейнера.
     */
    public static void applyHorizontalMarginInsets(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        final int marginStart = MarginLayoutParamsCompat.getMarginStart(marginLayoutParams);
        final int marginTop = marginLayoutParams.topMargin;
        final int marginEnd = MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
        final int marginBottom = marginLayoutParams.bottomMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, (target, windowInsets) -> {
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            ViewGroup.MarginLayoutParams targetLayoutParams =
                    (ViewGroup.MarginLayoutParams) target.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(targetLayoutParams, marginStart + insets.left);
            targetLayoutParams.topMargin = marginTop;
            MarginLayoutParamsCompat.setMarginEnd(targetLayoutParams, marginEnd + insets.right);
            targetLayoutParams.bottomMargin = marginBottom;
            target.setLayoutParams(targetLayoutParams);
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
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            target.setPadding(
                    paddingLeft + insets.left,
                    paddingTop + insets.top,
                    paddingRight + insets.right,
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
            Insets insets = getRootInsets(target, windowInsets).getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
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

    private static WindowInsetsCompat getRootInsets(
            View view, WindowInsetsCompat dispatchedInsets) {
        WindowInsetsCompat rootInsets = ViewCompat.getRootWindowInsets(view);
        return rootInsets != null ? rootInsets : dispatchedInsets;
    }
}
