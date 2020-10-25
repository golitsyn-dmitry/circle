package com.scorp.sharik_develop

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionManager
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.coroutines.launch
import java.util.*


class PlayActivity : AppCompatActivity() {
    var totalScore: Long = 0
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    var score = 0
    var stopmark = 0
    var width = 0
    var height = 0
    var typeOfCircle: String? = null
    var constraintLayout: ConstraintLayout? = null
    var context: Context? = this
    val set = ConstraintSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        constraintLayout = findViewById(R.id.activity_play)

        lifecycleScope.launch {
            totalScore = FirebaseHelpers.getUserData()?.balance ?: 0
        }

        val arguments = intent.extras
        typeOfCircle = if (arguments != null) arguments.getString("typeOfCircle") else ""
        when (typeOfCircle) {
            "circle_black2" -> circle!!.setImageResource(R.drawable.circle_black2)
            "circle_blue2" -> circle!!.setImageResource(R.drawable.circle_blue2)
            "circle_red2" -> circle!!.setImageResource(R.drawable.circle_red2)
            "circle_purple2" -> circle!!.setImageResource(R.drawable.circle_purple2)
        }
        val displayMetrics = resources.displayMetrics
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
    }

    fun onClick(view: View?) {
        val textView = findViewById<View>(R.id.testText) as TextView
        val scoreText = findViewById<View>(R.id.scoreText) as TextView
        val relativeLayout = findViewById<View>(R.id.ll) as RelativeLayout
        set.clone(constraintLayout)
//        val layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        )
        var scoretxt = ""
        val scoretotal: String
        //int circleWidth = circle.getWidth();
        //int circleHeight = circle.getHeight();
        if (score == 0) {
            object : CountDownTimer(10000, 1000) {
                var timerText = findViewById<View>(R.id.timerText) as TextView

                //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                override fun onTick(millisUntilFinished: Long) {
                    val timertxt = "Осталось: " + millisUntilFinished / 1000
                    timerText.text = timertxt
                }

                //Задаем действия после завершения отсчета
                override fun onFinish() {
                    timerText.text = "Time's out!"
                    stopmark = 1
                }
            }
                    .start()
        }
        if (stopmark != 1) {
            score++
            scoretxt = score.toString()

            //int maxWidth = (int) (Screenwidth() - circleWidth);
            //int maxHeight = (int) (Screenheight() - circleHeight);
            val random = Random()
            val xRand = random.nextInt(width - 360)
            val yRand = random.nextInt(height - 360)
//            val string = "X: $width; Y: $height"
//            textView.text = string
            scoreText.text = scoretxt
//            layoutParams.leftMargin = xRand
//            layoutParams.topMargin = yRand
//            circle!!.layoutParams = layoutParams
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
        } else {
//            scoretotal = "Your score is $score"
//            textView.text = scoretotal

//            set.centerHorizontally(
//                    R.id.circle,
//                    R.id.activity_play,
//                    ConstraintSet.START,
//                    0,
//                    R.id.activity_play,
//                    ConstraintSet.END,
//                    0,
//                    0.5F)

//            set.centerHorizontally(R.id.circle, R.id.activity_play)
//            set.centerVertically(R.id.circle, R.id.activity_play)

            set.applyTo(constraintLayout)
//            layoutParams.gravity = Gravity.CENTER
//            layoutParams.weight = 1f
//            circle!!.layoutParams = layoutParams
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
                if (x1 > x2 + 300) {
                    val intent = Intent()
                    intent.putExtra("totalScore", totalScore)
                    setResult(RESULT_OK, intent)
                    finish()
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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