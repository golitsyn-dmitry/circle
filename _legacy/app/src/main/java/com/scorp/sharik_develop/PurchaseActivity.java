package com.scorp.sharik_develop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseActivity extends AppCompatActivity implements View.OnClickListener {

    int totalScore;
    float x1, x2, y1, y2;
    private BillingClient billingClient;
    private Map<String, SkuDetails> mSkuDetailsMap = new HashMap<>();

    private static final String clicks_50 = "test.clicks_50";
    private static final String clicks_100 = "test.clicks_100";

    TextView tv_clicks_50, tv_clicks_100, tv_clicks_150, tv_clicks_200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        tv_clicks_50 = findViewById(R.id.tv_clicks_50);
        tv_clicks_100 = findViewById(R.id.tv_clicks_100);
        tv_clicks_150 = findViewById(R.id.tv_clicks_150);
        tv_clicks_200 = findViewById(R.id.tv_clicks_200);

        tv_clicks_50.setOnClickListener(this);
        tv_clicks_100.setOnClickListener(this);
        tv_clicks_150.setOnClickListener(this);
        tv_clicks_200.setOnClickListener(this);

        initBilling();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clicks_50:
                // кнопка ОК
                launchBilling(clicks_50);
                break;
            case R.id.tv_clicks_100:
                // кнопка Cancel
                launchBilling(clicks_100);
                break;
            case R.id.tv_clicks_150:
                // кнопка Cancel
                Toast.makeText(this, "В работе 150", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_clicks_200:
                // кнопка Cancel
                Toast.makeText(this, "В работе 200", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initBilling() {
        final BillingClient.Builder builder = BillingClient.newBuilder(this);
        builder.enablePendingPurchases();

        billingClient = builder.setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
                int responseCode = billingResult.getResponseCode();
                if (responseCode == BillingClient.BillingResponseCode.OK && purchases != null){
                    //for (Purchase purchase : purchases) {
                    List<Purchase> purchasesList = queryPurchases();
                    for (int i = 0; i < purchasesList.size(); i++) {
                        String purchaseId = purchasesList.get(i).getSku();
                        if(TextUtils.equals(clicks_50, purchaseId)) {
                            payComplete(clicks_50);
                        } else if (TextUtils.equals(clicks_100, purchaseId)) {
                            payComplete(clicks_100);
                        }
                    }
                    //}
                } /*else if (responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {

                } else if (responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {

                }*/
            }
        }).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                int responseCode = billingResult.getResponseCode();
                if (responseCode == BillingClient.BillingResponseCode.OK) {
                    querySkuDetails();
                    List<Purchase> purchasesList = queryPurchases();

                    for (int i = 0; i < purchasesList.size(); i++) {
                        String purchaseId = purchasesList.get(i).getSku();
                        if(TextUtils.equals(clicks_50, purchaseId)) {
                            payComplete(clicks_50);
                        } else if (TextUtils.equals(clicks_100, purchaseId)) {
                            payComplete(clicks_100);
                        }
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                billingClient.startConnection(this);
            }
        });
    }

    private void querySkuDetails() {
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        List<String> skuList = new ArrayList<>();
        skuList.add(clicks_50);
        skuList.add(clicks_100);
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    for (SkuDetails skuDetails : skuDetailsList) {
                        mSkuDetailsMap.put(skuDetails.getSku(), skuDetails);
                    }
                }
            }
        });
    }

    private List<Purchase> queryPurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        return purchasesResult.getPurchasesList();
    }

    //New code
    private void launchBilling(String skuId) {
        SkuDetails skuDetails = mSkuDetailsMap.get(skuId);
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsMap.get(skuId))
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }

    //New code
    private void payComplete(String clicks) {
        switch (clicks){
            case clicks_50:
                totalScore = totalScore + 50;
                Toast.makeText(this, "Вы купили 50 тапков", Toast.LENGTH_SHORT).show();
                break;
            case clicks_100:
                totalScore = totalScore + 100;
                Toast.makeText(this, "Вы купили 100 тапков", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public boolean onTouchEvent (MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(y1 < y2 + 300){
                    Intent intent = new Intent();
                    //intent.putExtra("totalScore", totalScore);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                }
                break;
        }
        return false;
    }
}
