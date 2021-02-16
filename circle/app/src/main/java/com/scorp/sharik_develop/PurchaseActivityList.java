package com.scorp.sharik_develop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.billingclient.api.Purchase;

import java.util.ArrayList;

public class PurchaseActivityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        final ArrayList<PurchaseItem> purchases = new ArrayList<>();
        purchases.add(new PurchaseItem(R.drawable.circle_black2, 50));
        purchases.add(new PurchaseItem(R.drawable.circle_blue2, 150));
        purchases.add(new PurchaseItem(R.drawable.circle_red2, 200));
        purchases.add(new PurchaseItem(R.drawable.circle_purple2, 250));
        purchases.add(new PurchaseItem(R.drawable.circle_black2, 50));
        purchases.add(new PurchaseItem(R.drawable.circle_blue2, 150));
        purchases.add(new PurchaseItem(R.drawable.circle_red2, 200));
        purchases.add(new PurchaseItem(R.drawable.circle_purple2, 250));
        purchases.add(new PurchaseItem(R.drawable.circle_black2, 50));
        purchases.add(new PurchaseItem(R.drawable.circle_blue2, 150));
        purchases.add(new PurchaseItem(R.drawable.circle_red2, 200));
        purchases.add(new PurchaseItem(R.drawable.circle_purple2, 250));

        PurchaseAdapter purchaseAdapter =new PurchaseAdapter(this, purchases);
        GridView gridView = (GridView) findViewById(R.id.purchase_list);
        gridView.setAdapter(purchaseAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }



}