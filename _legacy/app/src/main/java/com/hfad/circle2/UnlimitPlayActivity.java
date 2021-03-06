package com.hfad.circle2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.sip.SipSession;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class UnlimitPlayActivity extends AppCompatActivity {

    ImageView circle;
    float x1, x2, y1, y2;
    int totalScore;
    int width;
    int height;
    String typeOfCircle;
    TextView scoreText;
    String scoretxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlimit_play);

        Bundle arguments = getIntent().getExtras();
        typeOfCircle = arguments != null ? arguments.getString("typeOfCircle") : "";
        circle = findViewById(R.id.circle);
        scoreText = findViewById(R.id.scoreText);

        if (typeOfCircle.equals("circle_black2")) {
            circle.setImageResource(R.drawable.circle_black2);
        } else if (typeOfCircle.equals("circle_blue2")) {
            circle.setImageResource(R.drawable.circle_blue2);
        } else if (typeOfCircle.equals("circle_red2")) {
            circle.setImageResource(R.drawable.circle_red2);
        } else if (typeOfCircle.equals("circle_purple2")) {
            circle.setImageResource(R.drawable.circle_purple2);
        }

        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore",0);
        scoretxt = String.valueOf(totalScore);
        scoreText.setText(scoretxt);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    public void onClick (View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        totalScore++;

        //int maxWidth = (int) (Screenwidth() - circleWidth);
        //int maxHeight = (int) (Screenheight() - circleHeight);

        Random random = new Random();
        int xRand = random.nextInt(width - 360);
        int yRand = random.nextInt(height - 360);

        scoretxt = String.valueOf(totalScore);
        scoreText.setText(scoretxt);

        layoutParams.leftMargin = xRand;
        layoutParams.topMargin = yRand;

        circle.setLayoutParams(layoutParams);
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
                if (x1 < x2 - 300){
                    Intent intent = new Intent();
                    intent.putExtra("totalScore", totalScore);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }
}
