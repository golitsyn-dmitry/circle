package com.scorp.sharik_develop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    var anim_circle: Animation? = null
    var startCircle: ImageView? = null
    var imageNoAds: ImageView? = null
    var imageResults: ImageView? = null
    var imageChangeCircle: ImageView? = null
    var imageBuy: ImageView? = null
    var circle2: TextView? = null
    var tapToGame: TextView? = null
    var scoreText: TextView? = null
    var totalScore: Long = 0
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    var circle_blue2Bl = false
    var circle_red2Bl = false
    var circle_purple2Bl = false
    var typeOfCircle = "circle_black2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startCircle = findViewById(R.id.startCircle)
        imageNoAds = findViewById(R.id.imageNoAds)
        imageResults = findViewById(R.id.imageResults)
        imageChangeCircle = findViewById(R.id.imageChangeCircle)
        imageBuy = findViewById(R.id.imageBuy)
        circle2 = findViewById(R.id.circle2)
        tapToGame = findViewById(R.id.tapToGame)
        scoreText = findViewById(R.id.scoreText)

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.circle_black) // Set logo drawable
                        .setTheme(R.style.AppTheme) // Set theme
                        .build(),
                SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE)
    }

    fun onClick(view: View?) {
        val intent_onChoice = Intent(this, ChoiceActivity::class.java)
        intent_onChoice.putExtra("totalScore", totalScore)
        intent_onChoice.putExtra("circle_blue2Bl", circle_blue2Bl)
        intent_onChoice.putExtra("circle_red2Bl", circle_red2Bl)
        intent_onChoice.putExtra("circle_purple2Bl", circle_purple2Bl)
        startActivityForResult(intent_onChoice, REQUEST_ACCESS_TYPE_OnChoice)
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
                if (x1 > x2 + 300) {
                    val intent_onGain = Intent(this, UnlimitPlayActivity::class.java)
                    intent_onGain.putExtra("totalScore", totalScore)
                    intent_onGain.putExtra("typeOfCircle", typeOfCircle)
                    startActivityForResult(intent_onGain, REQUEST_ACCESS_TYPE_OnGain)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (x1 < x2 - 300) {
                    val intent = Intent(this, PlayActivity::class.java)
                    intent.putExtra("typeOfCircle", typeOfCircle)
                    intent.putExtra("totalScore", totalScore)
                    startActivityForResult(intent, REQUEST_ACCESS_TYPE_OnTime)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                } else if (y1 > y2 + 300){
                    val intent = Intent(this, PurchaseActivity::class.java)
                    intent.putExtra("typeOfCircle", typeOfCircle)
                    startActivityForResult(intent, REQUEST_ACCESS_TYPE_OnPurchase);
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
                }
            }
        }
        return false
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (requestCode == SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val user = Firebase.auth.currentUser ?: return

                lifecycleScope.launch {
                    val userData = getUserData(user)
                    totalScore = userData?.balance ?: 0;
                }
            }
        }

        when (requestCode) {
            REQUEST_ACCESS_TYPE_OnChoice -> {
                typeOfCircle = data.getStringExtra("typeOfCircle").toString()
                if (typeOfCircle == "circle_black2") {
                    startCircle!!.setImageResource(R.drawable.circle_black2)
                } else if (typeOfCircle == "circle_blue2") {
                    startCircle!!.setImageResource(R.drawable.circle_blue2)
                } else if (typeOfCircle == "circle_red2") {
                    startCircle!!.setImageResource(R.drawable.circle_red2)
                } else if (typeOfCircle == "circle_purple2") {
                    startCircle!!.setImageResource(R.drawable.circle_purple2)
                }
                totalScore = data.getLongExtra("totalScore", 5)
                scoreText!!.text = "TOTAL SCORE $totalScore"
                circle_blue2Bl = data.getBooleanExtra("circle_blue2Bl", circle_blue2Bl)
                circle_red2Bl = data.getBooleanExtra("circle_red2Bl", circle_red2Bl)
                circle_purple2Bl = data.getBooleanExtra("circle_purple2Bl", circle_purple2Bl)
            }
            REQUEST_ACCESS_TYPE_OnGain -> {
                totalScore = data.getLongExtra("totalScore", 5)
                scoreText!!.text = "TOTAL SCORE $totalScore"
            }
        }
        anim_circle = AnimationUtils.loadAnimation(this, R.anim.myalpha)
        startCircle!!.startAnimation(anim_circle)
    }

    private suspend fun getUserData(user: FirebaseUser): PlayerData? {
        try {
            val users = Firebase.firestore
                    .collection("users")

            val userData = users
                    .document(user.uid).get()
                    .await()

            if (userData.data != null)
                return userData.toObject<PlayerData>()

            // No data for current user yet, creating
            val newUserData = hashMapOf("balance" to 0)
            users.document(user.uid).set(newUserData)

            return userData.toObject<PlayerData>()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)

            return null
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE = 0
        private const val REQUEST_ACCESS_TYPE_OnChoice = 1
        private const val REQUEST_ACCESS_TYPE_OnGain = 2
        private const val REQUEST_ACCESS_TYPE_OnTime = 3
        private const val REQUEST_ACCESS_TYPE_OnPurchase = 4
    }
}