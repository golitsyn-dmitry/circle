package com.scorp.sharik_develop

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import java.util.*
import kotlinx.android.synthetic.main.activity_purchase.*

class PurchaseActivity : AppCompatActivity(), View.OnClickListener {
    var totalScore = 0
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    private var billingClient: BillingClient? = null
    private val mSkuDetailsMap: MutableMap<String, SkuDetails> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)
        tv_clicks_50?.setOnClickListener(this)
        tv_clicks_100?.setOnClickListener(this)
        tv_clicks_150?.setOnClickListener(this)
        tv_clicks_200?.setOnClickListener(this)
        initBilling()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_clicks_50 ->                 // кнопка ОК
                launchBilling(clicks_50)
            R.id.tv_clicks_100 ->                 // кнопка Cancel
                launchBilling(clicks_100)
            R.id.tv_clicks_150 ->                 // кнопка Cancel
                Toast.makeText(this, "В работе 150", Toast.LENGTH_SHORT).show()
            R.id.tv_clicks_200 ->                 // кнопка Cancel
                Toast.makeText(this, "В работе 200", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initBilling() {
        val builder = BillingClient.newBuilder(this)
        builder.enablePendingPurchases()
        billingClient = builder.setListener { billingResult, purchases ->
            val responseCode = billingResult.responseCode
            if (responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                //for (Purchase purchase : purchases) {
                val purchasesList = queryPurchases()
                for (i in purchasesList!!.indices) {
                    val purchaseId = purchasesList[i].sku
                    if (TextUtils.equals(clicks_50, purchaseId)) {
                        payComplete(clicks_50)
                    } else if (TextUtils.equals(clicks_100, purchaseId)) {
                        payComplete(clicks_100)
                    }
                }
                //}
            } /*else if (responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {

                } else if (responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {

                }*/
        }.build()
        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                val responseCode = billingResult.responseCode
                if (responseCode == BillingClient.BillingResponseCode.OK) {
                    querySkuDetails()
                    val purchasesList = queryPurchases()
                    for (i in purchasesList!!.indices) {
                        val purchaseId = purchasesList[i].sku
                        if (TextUtils.equals(clicks_50, purchaseId)) {
                            payComplete(clicks_50)
                        } else if (TextUtils.equals(clicks_100, purchaseId)) {
                            payComplete(clicks_100)
                        }
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                billingClient!!.startConnection(this)
            }
        })
    }

    private fun querySkuDetails() {
        val skuDetailsParamsBuilder = SkuDetailsParams.newBuilder()
        val skuList: MutableList<String> = ArrayList()
        skuList.add(clicks_50)
        skuList.add(clicks_100)
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient!!.querySkuDetailsAsync(skuDetailsParamsBuilder.build()) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (skuDetails in skuDetailsList!!) {
                    mSkuDetailsMap[skuDetails.sku] = skuDetails
                }
            }
        }
    }

    private fun queryPurchases(): List<Purchase>? {
        val purchasesResult = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
        return purchasesResult.purchasesList
    }

    //New code
    private fun launchBilling(skuId: String) {
        val skuDetails = mSkuDetailsMap[skuId]
        val billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsMap[skuId]!!)
                .build()
        billingClient!!.launchBillingFlow(this, billingFlowParams)
    }

    //New code
    private fun payComplete(clicks: String) {
        when (clicks) {
            clicks_50 -> {
                totalScore += 50
                Toast.makeText(this, "Вы купили 50 тапков", Toast.LENGTH_SHORT).show()
            }
            clicks_100 -> {
                totalScore += 100
                Toast.makeText(this, "Вы купили 100 тапков", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTouchEvent(touchevent: MotionEvent): Boolean {
        when (touchevent.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = touchevent.x
                y1 = touchevent.y
            }
            MotionEvent.ACTION_UP -> {
                x2 = touchevent.x
                y2 = touchevent.y
                if (y1 < y2 + 300) {
                    val intent = Intent()
                    //intent.putExtra("totalScore", totalScore);
                    setResult(RESULT_OK, intent)
                    finish()
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                }
            }
        }
        return false
    }

    companion object {
        private const val clicks_50 = "test.clicks_50"
        private const val clicks_100 = "test.clicks_100"
    }
}