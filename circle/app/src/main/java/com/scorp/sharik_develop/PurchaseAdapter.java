package com.scorp.sharik_develop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.Purchase;

import java.util.ArrayList;

public class PurchaseAdapter extends ArrayAdapter<PurchaseItem> {

    public PurchaseAdapter(@NonNull Context context, ArrayList<PurchaseItem> purchase) {
        super(context, 0, purchase);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PurchaseItem currentPurchase = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.purchase_list_item, parent, false);
        }

        TextView purchasePrice = (TextView) convertView.findViewById(R.id.purchase_price);
        purchasePrice.setText(currentPurchase.getmPrice() + " Clicks");
        ImageView purchaseImage = (ImageView) convertView.findViewById(R.id.purchase_image);
        purchaseImage.setImageResource(currentPurchase.getmImage());

        return convertView;
    }

}
