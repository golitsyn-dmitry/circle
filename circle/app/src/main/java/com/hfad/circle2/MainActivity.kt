package com.hfad.circle2

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
    var totalScore = 0
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
                }
            }
        }
        return false
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        when (requestCode) {
            REQUEST_ACCESS_TYPE_OnChoice -> {
                typeOfCircle = data.getStringExtra("typeOfCircle")
                if (typeOfCircle == "circle_black2") {
                    startCircle!!.setImageResource(R.drawable.circle_black2)
                } else if (typeOfCircle == "circle_blue2") {
                    startCircle!!.setImageResource(R.drawable.circle_blue2)
                } else if (typeOfCircle == "circle_red2") {
                    startCircle!!.setImageResource(R.drawable.circle_red2)
                } else if (typeOfCircle == "circle_purple2") {
                    startCircle!!.setImageResource(R.drawable.circle_purple2)
                }
                totalScore = data.getIntExtra("totalScore", 5)
                scoreText!!.text = "TOTAL SCORE $totalScore"
                circle_blue2Bl = data.getBooleanExtra("circle_blue2Bl", circle_blue2Bl)
                circle_red2Bl = data.getBooleanExtra("circle_red2Bl", circle_red2Bl)
                circle_purple2Bl = data.getBooleanExtra("circle_purple2Bl", circle_purple2Bl)
            }
            REQUEST_ACCESS_TYPE_OnGain -> {
                totalScore = data.getIntExtra("totalScore", 5)
                scoreText!!.text = "TOTAL SCORE $totalScore"
            }
        }
        anim_circle = AnimationUtils.loadAnimation(this, R.anim.myalpha)
        startCircle!!.startAnimation(anim_circle)
    }

    companion object {
        private const val REQUEST_ACCESS_TYPE_OnChoice = 1
        private const val REQUEST_ACCESS_TYPE_OnGain = 2
        private const val REQUEST_ACCESS_TYPE_OnTime = 3
    }
}