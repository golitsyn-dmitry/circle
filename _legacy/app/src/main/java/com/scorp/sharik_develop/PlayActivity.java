package com.scorp.sharik_develop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    ImageView circle;
    int totalScore;
    float x1, x2, y1, y2;
    int score = 0;
    int stopmark;
    int width;
    int height;
    String typeOfCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Bundle arguments = getIntent().getExtras();
        typeOfCircle = arguments != null ? arguments.getString("typeOfCircle") : "";
        circle = (ImageView) findViewById(R.id.circle);

        if (typeOfCircle.equals("circle_black2")) {
            circle.setImageResource(R.drawable.circle_black2);
        } else if (typeOfCircle.equals("circle_blue2")) {
            circle.setImageResource(R.drawable.circle_blue2);
        } else if (typeOfCircle.equals("circle_red2")) {
            circle.setImageResource(R.drawable.circle_red2);
        } else if (typeOfCircle.equals("circle_purple2")) {
            circle.setImageResource(R.drawable.circle_purple2);
        }

        //Intent intent = getIntent();
        //totalScore = intent.getIntExtra("totalScore",0);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    public void onClick (View view){

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ll);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        String scoretxt = "";
        String scoretotal;
        //int circleWidth = circle.getWidth();
        //int circleHeight = circle.getHeight();

        if(score == 0) {
            new CountDownTimer(10000, 1000) {

                TextView timerText = (TextView) findViewById(R.id.timerText);

                //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                public void onTick(long millisUntilFinished) {

                    String timertxt = "Осталось: " + millisUntilFinished / 1000;
                    timerText.setText(timertxt);
                }

                //Задаем действия после завершения отсчета
                public void onFinish() {
                    timerText.setText("Time's out!");
                    stopmark = 1;
                }
            }
            .start();
        }

        if(stopmark != 1) {

            score++;
            scoretxt = String.valueOf(score);

            //int maxWidth = (int) (Screenwidth() - circleWidth);
            //int maxHeight = (int) (Screenheight() - circleHeight);

            Random random = new Random();
            int xRand = random.nextInt(width - 360);
            int yRand = random.nextInt(height - 360);

            String string = "X: " + width + "; Y: " + height;

            textView.setText(string);
            scoreText.setText(scoretxt);

            layoutParams.leftMargin = xRand;
            layoutParams.topMargin = yRand;

            circle.setLayoutParams(layoutParams);
        } else {
            scoretotal = "Your score is " + score;
            textView.setText(scoretotal);

            layoutParams.gravity = Gravity.CENTER;
            layoutParams.weight = 1;
            circle.setLayoutParams(layoutParams);
        }
    }

    public boolean onTouchEvent (MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 =touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 =touchevent.getX();
                y2 = touchevent.getY();
                if(x1 > x2 + 300){
                    Intent intent = new Intent();
                    //intent.putExtra("totalScore", totalScore);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }
}
