package ru.mgusev.eldritchhorror.ui.activity.donate;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;

public class DonateActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private BillingClient billingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        initBillingClient();
    }

    private void initBillingClient() {
        billingClient = BillingClient.newBuilder(this).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.

                    List<String> skuList = new ArrayList<>();
                    /*skuList.add("donate_1_usd");
                    skuList.add("donate_1_usd");
                    skuList.add("donate_2_usd");
                    skuList.add("donate_5_usd");
                    skuList.add("donate_10_usd");
                    skuList.add("donate_50_usd");
                    skuList.add("donate_100_usd");*/
                    skuList.add("android.test.purchased");
                    skuList.add("android.test.purchased");
                    skuList.add("android.test.purchased");
                    skuList.add("android.test.purchased");
                    skuList.add("android.test.purchased");
                    skuList.add("android.test.purchased");

                    SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(BillingClient.SkuType.INAPP).build();
                    billingClient.querySkuDetailsAsync(skuDetailsParams,
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                    //BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetailsList.get(0)).build();
                                    //int billingResponseCode = billingClient.launchBillingFlow(DonateActivity.this, flowParams);
                                    Log.d("SKU SIZE", String.valueOf(skuDetailsList.size()));
                                    if (responseCode == BillingClient.BillingResponse.OK) {
                                        // do something you want
                                        for (SkuDetails sku : skuDetailsList) Log.d("SKU", sku.getSku() + " " + sku.getPrice());
                                    }
                                }
                            });

                }
            }

            @Override public void onBillingServiceDisconnected() {
                showError();
            }
        });
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {

            // do something you want

        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
        } else {
            showError();
        }
    }

    private void showError() {
        Toast.makeText(this, R.string.donate_error_header, Toast.LENGTH_SHORT).show();
    }

    /*@OnClick({R.id.about_donate_1_btn, R.id.about_donate_2_btn, R.id.about_donate_5_btn, R.id.about_donate_10_btn, R.id.about_donate_50_btn, R.id.about_donate_100_btn})
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
    }*/

    private void startPurchase() {
        //BillingFlowParams mParams = BillingFlowParams.newBuilder().setSkuDetails(skuId).setType(billingType).build();
    }
}
