package com.scorp.sharik_develop

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
    var testText: TextView? = null
    var scoretxt: String? = null
    var constraintLayout:ConstraintLayout? = null
    var context: Context? = this
    val set = ConstraintSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlimit_play)
        constraintLayout = findViewById(R.id.activity_unlimit_play)
        val arguments = intent.extras
        typeOfCircle = if (arguments != null) arguments.getString("typeOfCircle") else ""
        circle = findViewById(R.id.circle)
        scoreText = findViewById(R.id.scoreText)
        testText = findViewById(R.id.testText)
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
//        val layoutParams = ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT
//        )

        set.clone(constraintLayout)

        totalScore++

        //int maxWidth = (int) (Screenwidth() - circleWidth);
        //int maxHeight = (int) (Screenheight() - circleHeight);
        val random = Random()
        val xRand = random.nextInt(width - 360)
        val yRand = random.nextInt(height - 360)
        scoretxt = totalScore.toString()
        scoreText!!.text = scoretxt

//        layoutParams.leftMargin = xRand
//        layoutParams.topMargin = yRand

        set.clear(R.id.circle, ConstraintSet.END)
        set.clear(R.id.circle, ConstraintSet.BOTTOM)
        set.clear(R.id.scoreText, ConstraintSet.END)
        set.clear(R.id.scoreText, ConstraintSet.BOTTOM)

        set.setMargin(R.id.circle, ConstraintSet.START, xRand)
        set.setMargin(R.id.circle, ConstraintSet.TOP, yRand)
        set.setMargin(R.id.scoreText, ConstraintSet.START, xRand)
        set.setMargin(R.id.scoreText, ConstraintSet.TOP, yRand)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(constraintLayout)
        }
        set.applyTo(constraintLayout)

//        circle!!.layoutParams = layoutParams
//        scoreText!!.layoutParams = layoutParams
//        testText!!.text = xRand.toString() + " " + yRand.toString()
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