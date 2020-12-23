package com.scorp.sharik_develop

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var anim_circle: Animation? = null
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
        totalScore = intent.getLongExtra("score", 0)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View?) {
        when (view?.id) {
            btn_signout?.id -> {
                AuthUI.getInstance().signOut(this)
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            else -> {
                val intentOnChoice = Intent(this, ChoiceActivity::class.java)
                intentOnChoice.putExtra("totalScore", totalScore)
                intentOnChoice.putExtra("circle_blue2Bl", circle_blue2Bl)
                intentOnChoice.putExtra("circle_red2Bl", circle_red2Bl)
                intentOnChoice.putExtra("circle_purple2Bl", circle_purple2Bl)
                startActivityForResult(intentOnChoice, REQUEST_ACCESS_TYPE_OnChoice)
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
                when {
                    x1 > x2 + 300 -> {
                        val intentOnGain = Intent(this, UnlimitPlayActivity::class.java)
                        intentOnGain.putExtra("totalScore", totalScore)
                        intentOnGain.putExtra("typeOfCircle", typeOfCircle)
                        startActivityForResult(intentOnGain, REQUEST_ACCESS_TYPE_OnGain)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    x1 < x2 - 300 -> {
                        val intent = Intent(this, PlayActivity::class.java)
                        intent.putExtra("typeOfCircle", typeOfCircle)
                        intent.putExtra("totalScore", totalScore)
                        startActivityForResult(intent, REQUEST_ACCESS_TYPE_OnTime)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                    y1 > y2 + 300 -> {
                        val intent = Intent(this, PurchaseActivity::class.java)
                        intent.putExtra("typeOfCircle", typeOfCircle)
                        startActivityForResult(intent, REQUEST_ACCESS_TYPE_OnPurchase)
                        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
                    }
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

        when (requestCode) {
            REQUEST_ACCESS_TYPE_OnChoice -> {
                typeOfCircle = data.getStringExtra("typeOfCircle").toString()
                when (typeOfCircle) {
                    "circle_black2" -> startCircle.setImageResource(R.drawable.circle_black2)
                    "circle_blue2" -> startCircle.setImageResource(R.drawable.circle_blue2)
                    "circle_red2" -> startCircle.setImageResource(R.drawable.circle_red2)
                    "circle_purple2" -> startCircle.setImageResource(R.drawable.circle_purple2)
                }
                totalScore = data.getLongExtra("totalScore", 5)
                scoreText.text = "TOTAL SCORE $totalScore"
                circle_blue2Bl = data.getBooleanExtra("circle_blue2Bl", circle_blue2Bl)
                circle_red2Bl = data.getBooleanExtra("circle_red2Bl", circle_red2Bl)
                circle_purple2Bl = data.getBooleanExtra("circle_purple2Bl", circle_purple2Bl)
            }
            REQUEST_ACCESS_TYPE_OnGain -> {
                totalScore = data.getLongExtra("totalScore", 5)
                scoreText.text = "TOTAL SCORE $totalScore"
            }
        }
        anim_circle = AnimationUtils.loadAnimation(this, R.anim.myalpha)
        startCircle.startAnimation(anim_circle)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            totalScore = FirebaseHelpers.getUserData()?.balance ?: 0
            scoreText.text = "TOTAL SCORE $totalScore"
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_ACCESS_TYPE_OnChoice = 1
        private const val REQUEST_ACCESS_TYPE_OnGain = 2
        private const val REQUEST_ACCESS_TYPE_OnTime = 3
        private const val REQUEST_ACCESS_TYPE_OnPurchase = 4
    }
}