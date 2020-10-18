package com.scorp.sharik_develop

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class UnlimitPlayActivity : AppCompatActivity() {
    var circle: ImageView? = null
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    var totalScore:Long = 0
    var width = 0
    var height = 0
    var typeOfCircle: String? = null
    var scoreText: TextView? = null
    var scoretxt: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlimit_play)
        val arguments = intent.extras
        typeOfCircle = if (arguments != null) arguments.getString("typeOfCircle") else ""
        circle = findViewById(R.id.circle)
        scoreText = findViewById(R.id.scoreText)
        when (typeOfCircle) {
            "circle_black2" ->
                circle?.setImageResource(R.drawable.circle_black2)
            "circle_blue2" ->
                circle?.setImageResource(R.drawable.circle_blue2)
            "circle_red2" ->
                circle?.setImageResource(R.drawable.circle_red2)
            "circle_purple2" ->
                circle?.setImageResource(R.drawable.circle_purple2)
        }
        lifecycleScope.launch {
            totalScore = FirebaseHelpers.getUserData()?.balance ?: 0
            scoretxt = totalScore.toString()
            scoreText?.text = scoretxt
        }

        val displaymetrics = resources.displayMetrics
        width = displaymetrics.widthPixels
        height = displaymetrics.heightPixels
    }

    fun onClick(view: View?) {
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        totalScore++

        //int maxWidth = (int) (Screenwidth() - circleWidth);
        //int maxHeight = (int) (Screenheight() - circleHeight);
        val random = Random()
        val xRand = random.nextInt(width - 360)
        val yRand = random.nextInt(height - 360)
        scoretxt = totalScore.toString()
        scoreText!!.text = scoretxt
        layoutParams.leftMargin = xRand
        layoutParams.topMargin = yRand
        circle!!.layoutParams = layoutParams
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
                if (x1 < x2 - 300) {
                    val intent = Intent()
                    intent.putExtra("totalScore", totalScore)
                    setResult(RESULT_OK, intent)
                    finish()
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            FirebaseHelpers.setUserBalance(totalScore)
        }
    }
}