package ru.mgusev.eldritchhorror.ui.activity.about;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;

public class AboutActivity extends AppCompatActivity {

    private final static String donateURLYandex = "https://money.yandex.ru/to/410014579099919";
    private final static String donateURLPayPal = "https://www.paypal.me/mgusevstudio";

    @BindView(R.id.about_version) TextView version;
    @BindView(R.id.about_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initToolbar();

        String versionText = getResources().getString(R.string.version_header) + getVersionName();
        version.setText(versionText);
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

    @OnClick({R.id.about_donate_yandex_btn, R.id.about_donate_paypal_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_donate_yandex_btn:
                Intent browserIntentYandex = new Intent(Intent.ACTION_VIEW, Uri.parse(donateURLYandex));
                startActivity(browserIntentYandex);
                break;
            case R.id.about_donate_paypal_btn:
                Intent browserIntentPayPal = new Intent(Intent.ACTION_VIEW, Uri.parse(donateURLPayPal));
                startActivity(browserIntentPayPal);
                break;
            default:
                break;
        }
    }
}