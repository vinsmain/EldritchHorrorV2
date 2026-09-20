package ru.mgusev.eldritchhorror.ui.activity.about;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityAboutBinding;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;
import timber.log.Timber;

public class AboutActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private ActivityAboutBinding binding;

    private final Map<String, ProductDetails> donationProducts = new HashMap<>();
    private BillingClient billingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.apply(this, binding.aboutToolbar, binding.aboutContent);
        initToolbar();
        binding.aboutVersion.setText(getString(R.string.version_header) + getVersionName());
        binding.aboutDonate1Btn.setOnClickListener(this::onDonationClick);
        binding.aboutDonate2Btn.setOnClickListener(this::onDonationClick);
        binding.aboutDonate5Btn.setOnClickListener(this::onDonationClick);
        binding.aboutDonate10Btn.setOnClickListener(this::onDonationClick);
        binding.aboutDonate50Btn.setOnClickListener(this::onDonationClick);
        binding.aboutDonate100Btn.setOnClickListener(this::onDonationClick);
        setDonationButtonsEnabled(false);

        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
                .enableAutoServiceReconnection()
                .build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult result) {
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    loadDonationProducts();
                } else {
                    showBillingUnavailable(result);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                setDonationButtonsEnabled(false);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(binding.aboutToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.about);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.aboutToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void loadDonationProducts() {
        List<QueryProductDetailsParams.Product> products = Arrays.asList(
                product("donate_1_usd"), product("donate_2_usd"), product("donate_5_usd"),
                product("donate_10_usd"), product("donate_50_usd"), product("donate_100_usd")
        );
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder().setProductList(products).build();
        billingClient.queryProductDetailsAsync(params, (result, queryResult) -> {
            donationProducts.clear();
            for (ProductDetails product : queryResult.getProductDetailsList()) {
                donationProducts.put(product.getProductId(), product);
            }
            setDonationButtonsEnabled(!donationProducts.isEmpty());
            if (donationProducts.isEmpty()) {
                showBillingUnavailable(result);
            }
        });
    }

    private QueryProductDetailsParams.Product product(String productId) {
        return QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build();
    }

    public void onDonationClick(View view) {
        String productId;
        if (view.getId() == R.id.about_donate_1_btn) productId = "donate_1_usd";
        else if (view.getId() == R.id.about_donate_2_btn) productId = "donate_2_usd";
        else if (view.getId() == R.id.about_donate_5_btn) productId = "donate_5_usd";
        else if (view.getId() == R.id.about_donate_10_btn) productId = "donate_10_usd";
        else if (view.getId() == R.id.about_donate_50_btn) productId = "donate_50_usd";
        else if (view.getId() == R.id.about_donate_100_btn) productId = "donate_100_usd";
        else return;
        ProductDetails product = donationProducts.get(productId);
        if (product == null) {
            Toast.makeText(this, R.string.donate_error_header, Toast.LENGTH_SHORT).show();
            return;
        }
        BillingFlowParams.ProductDetailsParams.Builder productParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product);
        List<ProductDetails.OneTimePurchaseOfferDetails> offers = product.getOneTimePurchaseOfferDetailsList();
        if (offers != null && !offers.isEmpty()) {
            productParams.setOfferToken(offers.get(0).getOfferToken());
        }
        BillingResult result = billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(Collections.singletonList(productParams.build()))
                .build());
        if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) {
            showBillingUnavailable(result);
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult result, List<Purchase> purchases) {
        if (result.getResponseCode() != BillingClient.BillingResponseCode.OK || purchases == null) {
            if (result.getResponseCode() != BillingClient.BillingResponseCode.USER_CANCELED) showBillingUnavailable(result);
            return;
        }
        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(),
                        (consumeResult, token) -> {
                            if (consumeResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                Toast.makeText(this, R.string.donate_success_header, Toast.LENGTH_SHORT).show();
                            } else {
                                showBillingUnavailable(consumeResult);
                            }
                        });
            }
        }
    }

    private void setDonationButtonsEnabled(boolean enabled) {
        binding.aboutDonate1Btn.setEnabled(enabled); binding.aboutDonate2Btn.setEnabled(enabled); binding.aboutDonate5Btn.setEnabled(enabled); binding.aboutDonate10Btn.setEnabled(enabled); binding.aboutDonate50Btn.setEnabled(enabled); binding.aboutDonate100Btn.setEnabled(enabled);
    }

    private void showBillingUnavailable(BillingResult result) {
        Timber.w("Google Play Billing недоступен: %s", result.getDebugMessage());
        setDonationButtonsEnabled(false);
        Toast.makeText(this, R.string.donate_error_header, Toast.LENGTH_SHORT).show();
    }

    private String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException exception) {
            Timber.e(exception, "Не удалось получить версию приложения");
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        if (billingClient != null) billingClient.endConnection();
        super.onDestroy();
    }
}
