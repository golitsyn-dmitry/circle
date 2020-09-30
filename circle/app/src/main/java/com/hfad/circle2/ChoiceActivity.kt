package com.hfad.circle2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChoiceActivity : AppCompatActivity() {
    var bigCircle: ImageView? = null
    var circle_black2: ImageView? = null
    var circle_blue2: ImageView? = null
    var circle_red2: ImageView? = null
    var circle_purple2: ImageView? = null
    var circle_blue2Bl = false
    var circle_red2Bl = false
    var circle_purple2Bl = false
    var typeOfCircle: String? = null
    var data = Intent()
    var anim: Animation? = null
    var totalScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
        bigCircle = findViewById(R.id.bigCircle)
        getValueOfCircle("circle_black2")
        val intent = intent
        totalScore = intent.getIntExtra("totalScore", 0)
        circle_blue2Bl = intent.getBooleanExtra("circle_blue2Bl", circle_blue2Bl)
        circle_red2Bl = intent.getBooleanExtra("circle_red2Bl", circle_red2Bl)
        circle_purple2Bl = intent.getBooleanExtra("circle_purple2Bl", circle_purple2Bl)
        circle_black2 = findViewById(R.id.circle_black2)
        circle_blue2 = findViewById(R.id.circle_blue2)
        circle_red2 = findViewById(R.id.circle_red2)
        circle_purple2 = findViewById(R.id.circle_purple2)
        circle_black2?.setOnClickListener(onClickCircle)
        circle_blue2?.setOnClickListener(onClickCircle)
        circle_red2?.setOnClickListener(onClickCircle)
        circle_purple2?.setOnClickListener(onClickCircle)
        if (circle_blue2Bl) {
            circle_blue2?.setImageResource(R.drawable.circle_blue2)
        }
        if (circle_red2Bl) {
            circle_red2?.setImageResource(R.drawable.circle_red2)
        }
        if (circle_purple2Bl) {
            circle_purple2?.setImageResource(R.drawable.circle_purple2)
        }
    }

    var onClickCircle = View.OnClickListener { v ->
        when (v.id) {
            R.id.circle_black2 -> {
                bigCircle!!.setImageResource(R.drawable.circle_black2_big)
                getValueOfCircle("circle_black2")
                anim = AnimationUtils.loadAnimation(this@ChoiceActivity, R.anim.myscale)
                bigCircle!!.startAnimation(anim)
            }
            R.id.circle_blue2 -> {
                if (circle_blue2Bl) {
                    bigCircle!!.setImageResource(R.drawable.circle_blue2_big)
                    circle_blue2!!.setImageResource(R.drawable.circle_blue2)
                } else {
                    bigCircle!!.setImageResource(R.drawable.circle_locked)
                }
                getValueOfCircle("circle_blue2")
                anim = AnimationUtils.loadAnimation(this@ChoiceActivity, R.anim.myscale)
                bigCircle!!.startAnimation(anim)
            }
            R.id.circle_red2 -> {
                if (circle_red2Bl) {
                    bigCircle!!.setImageResource(R.drawable.circle_red2_big)
                    circle_red2!!.setImageResource(R.drawable.circle_red2)
                } else {
                    bigCircle!!.setImageResource(R.drawable.circle_locked)
                }
                getValueOfCircle("circle_red2")
                anim = AnimationUtils.loadAnimation(this@ChoiceActivity, R.anim.myscale)
                bigCircle!!.startAnimation(anim)
            }
            R.id.circle_purple2 -> {
                if (circle_purple2Bl) {
                    bigCircle!!.setImageResource(R.drawable.circle_purple2_big)
                    circle_purple2!!.setImageResource(R.drawable.circle_purple2)
                } else {
                    bigCircle!!.setImageResource(R.drawable.circle_locked)
                }
                getValueOfCircle("circle_purple2")
                anim = AnimationUtils.loadAnimation(this@ChoiceActivity, R.anim.myscale)
                bigCircle!!.startAnimation(anim)
            }
        }
    }

    fun OnClickChoiceCircleButton(view: View?) {
        if (data.getStringExtra("typeOfCircle") == "circle_black2") {
            data.putExtra("totalScore", totalScore)
            setResult(RESULT_OK, data)
            finish()
        }
        if (data.getStringExtra("typeOfCircle") == "circle_blue2") {
            if (circle_blue2Bl) {
                data.putExtra("totalScore", totalScore)
                data.putExtra("circle_blue2Bl", circle_blue2Bl)
                setResult(RESULT_OK, data)
                finish()
            } else {
                if (totalScore >= 10) {
                    totalScore = totalScore - 10
                    circle_blue2Bl = true
                    bigCircle!!.setImageResource(R.drawable.circle_blue2_big)
                    circle_blue2!!.setImageResource(R.drawable.circle_blue2)
                } else {
                    val toast = Toast.makeText(applicationContext,
                            "Не хватает $((", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
        } else if (data.getStringExtra("typeOfCircle") == "circle_red2") {
            if (circle_red2Bl) {
                data.putExtra("totalScore", totalScore)
                data.putExtra("circle_red2Bl", circle_red2Bl)
                setResult(RESULT_OK, data)
                finish()
            } else {
                if (totalScore >= 20) {
                    totalScore = totalScore - 20
                    circle_red2Bl = true
                    bigCircle!!.setImageResource(R.drawable.circle_red2_big)
                    circle_red2!!.setImageResource(R.drawable.circle_red2)
                } else {
                    val toast = Toast.makeText(applicationContext,
                            "Не хватает $((", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        } else if (data.getStringExtra("typeOfCircle") == "circle_purple2") {
            if (circle_purple2Bl) {
                data.putExtra("totalScore", totalScore)
                data.putExtra("circle_purple2Bl", circle_purple2Bl)
                setResult(RESULT_OK, data)
                finish()
            } else {
                if (totalScore >= 30) {
                    totalScore = totalScore - 30
                    circle_purple2Bl = true
                    bigCircle!!.setImageResource(R.drawable.circle_purple2_big)
                    circle_purple2!!.setImageResource(R.drawable.circle_purple2)
                } else {
                    val toast = Toast.makeText(applicationContext,
                            "Не хватает $((", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun getValueOfCircle(message: String) {
        data.putExtra("typeOfCircle", message)
    }
}