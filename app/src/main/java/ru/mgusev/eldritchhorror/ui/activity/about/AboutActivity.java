package ru.mgusev.eldritchhorror.ui.activity.about;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.util.IabHelper;
import timber.log.Timber;

public class AboutActivity extends AppCompatActivity {

    // Индефикатор нашего товара
    static final String SKU_DONATE_1 = "donate_1_usd";
    static final String SKU_DONATE_2 = "donate_2_usd";
    static final String SKU_DONATE_5 = "donate_5_usd";
    static final String SKU_DONATE_10 = "donate_10_usd";
    static final String SKU_DONATE_50 = "donate_50_usd";
    static final String SKU_DONATE_100 = "donate_100_usd";

    // Код для обратного вызова
    static final int REQUEST_CODE = 505;
    // Код при отмене пользователем
    static final int USER_CANCELED_CODE = -1005;
    // Экземпляр класса для работы с магазином
    private IabHelper helper;
    private IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener;


    @BindView(R.id.about_version) TextView version;
    @BindView(R.id.about_toolbar) Toolbar toolbar;
    @BindView(R.id.about_donate_1_btn) Button button1;
    @BindView(R.id.about_donate_2_btn) Button button2;
    @BindView(R.id.about_donate_5_btn) Button button5;
    @BindView(R.id.about_donate_10_btn) Button button10;
    @BindView(R.id.about_donate_50_btn) Button button50;
    @BindView(R.id.about_donate_100_btn) Button button100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initToolbar();

        String versionText = getResources().getString(R.string.version_header) + getVersionName();
        version.setText(versionText);

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgCRIeI/aoP1rB2NflmIq4ZCegk1AbvcMl4S99puq" +
                "LOVUEgVpcUH4K1G+j5G4bSrWVQJY/qApp0T2hy3CanIYjv1Jub1X26C5ZwyNUN3TU8T71EY4YVwy1n3JGRRR6RFKYd8VngECBthjNdeZ3pLA3" +
                "5adDsJuf+OzGhf8LDTto1Ex6ynToPgL3vgGVf+ShOemQXTZaaaqYByiiAIwTYnF3ttbxR9MG17CgfsgMt+45Vq+YfH/m/Cj+3mXMrDUBFYEfI" +
                "j1Pz2VlNpyZuYqneE+xnfZu7+frazoT4U1BdieqXh4VXYdPy37rNcnuc+MTt/UlJ8wm9YtYf3Y9GJWJohwtwIDAQAB";
        helper = new IabHelper(this, base64EncodedPublicKey);
        helper.enableDebugLogging(false);
        helper.startSetup(result -> {
            if (!result.isSuccess()) {
                // Произошла ошибка авторизации библиотеки, скрываем кнопку от пользователя
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button5.setVisibility(View.GONE);
                button10.setVisibility(View.GONE);
                button50.setVisibility(View.GONE);
                button100.setVisibility(View.GONE);
            }
        });

        purchaseFinishedListener = (result, purchase) -> {
            Timber.tag("DONATE_ANSWER").d(result.getMessage() + " " + result.getResponse() + " " + purchase);
            if (result.isFailure() && result.getResponse() != USER_CANCELED_CODE) {
                // Обработка произошедшей ошибки покупки
                Toast.makeText(this, R.string.donate_error_header, Toast.LENGTH_SHORT).show();
                return;
            }
            if (purchase != null) {
                // Говорим пользователю спасибо за перечисление средств
                Toast.makeText(this, R.string.donate_success_header, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.about);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private String getVersionName() {
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @OnClick({R.id.about_donate_1_btn, R.id.about_donate_2_btn, R.id.about_donate_5_btn, R.id.about_donate_10_btn, R.id.about_donate_50_btn, R.id.about_donate_100_btn})
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.about_donate_1_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_1, REQUEST_CODE, purchaseFinishedListener);
                    break;
                case R.id.about_donate_2_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_2, REQUEST_CODE, purchaseFinishedListener);
                    break;
                case R.id.about_donate_5_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_5, REQUEST_CODE, purchaseFinishedListener);
                    break;
                case R.id.about_donate_10_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_10, REQUEST_CODE, purchaseFinishedListener);
                    break;
                case R.id.about_donate_50_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_50, REQUEST_CODE, purchaseFinishedListener);
                    break;
                case R.id.about_donate_100_btn:
                    helper.launchPurchaseFlow(this, SKU_DONATE_100, REQUEST_CODE, purchaseFinishedListener);
                    break;
                default:
                    break;
            }
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!helper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            try {
                helper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
            helper = null;
        }
    }
}