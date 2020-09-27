package com.hfad.circle2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.circle2.data.DBHelper;

import static com.hfad.circle2.data.Contract.Entry.*;


public class MainActivity extends AppCompatActivity {

    Animation anim_circle;
    ImageView startCircle;
    ImageView imageNoAds;
    ImageView imageResults;
    ImageView imageChangeCircle;
    ImageView imageBuy;
    TextView circle2;
    TextView tapToGame;
    TextView scoreText;
    int totalScore;
    float x1, x2, y1, y2;
    boolean circle_blue2Bl = false, circle_red2Bl = false, circle_purple2Bl = false;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    String typeOfCircle = "circle_black2";
    private static final int REQUEST_ACCESS_TYPE_OnChoice = 1;
    private static final int REQUEST_ACCESS_TYPE_OnGain = 2;
    private static final int REQUEST_ACCESS_TYPE_OnTime = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startCircle = findViewById(R.id.startCircle);
        imageNoAds = findViewById(R.id.imageNoAds);
        imageResults = findViewById(R.id.imageResults);
        imageChangeCircle = findViewById(R.id.imageChangeCircle);
        imageBuy = findViewById(R.id.imageBuy);
        circle2 = findViewById(R.id.circle2);
        tapToGame = findViewById(R.id.tapToGame);
        scoreText = findViewById(R.id.scoreText);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CULUMN_ID, 0);
        values.put(CULUMN_PLAYER_NAME, "Demo Player");
        values.put(CULUMN_CURRENT_COUNT, 0);

        long newRowId = db.insert(TABLE_NAME, null, values);

        String[] projection = {
                CULUMN_ID,
                CULUMN_CURRENT_COUNT
        };

        String selection = CULUMN_ID + " = ?";
        String[] selectionArgs = { "0" };

        cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToNext();
        int columnIndex = cursor.getColumnIndex(CULUMN_CURRENT_COUNT);
        totalScore = cursor.getInt(columnIndex);
        scoreText.setText("TOTAL SCORE " + totalScore);
    }

    public void onClick (View view){
        Intent intent_onChoice = new Intent(this, ChoiceActivity.class);
        //intent_onChoice.putExtra("totalScore", totalScore);
        intent_onChoice.putExtra("circle_blue2Bl", circle_blue2Bl);
        intent_onChoice.putExtra("circle_red2Bl", circle_red2Bl);
        intent_onChoice.putExtra("circle_purple2Bl", circle_purple2Bl);
        startActivityForResult(intent_onChoice, REQUEST_ACCESS_TYPE_OnChoice);
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
                    Intent intent_onGain = new Intent(this, UnlimitPlayActivity.class);
                    //intent_onGain.putExtra("totalScore", totalScore);
                    intent_onGain.putExtra("typeOfCircle", typeOfCircle);
                    startActivityForResult(intent_onGain, REQUEST_ACCESS_TYPE_OnGain);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (x1 < x2 - 300){
                    Intent intent = new Intent(this, PlayActivity.class);
                    //intent.putExtra("totalScore", totalScore);
                    intent.putExtra("typeOfCircle", typeOfCircle);
                    startActivityForResult(intent, REQUEST_ACCESS_TYPE_OnTime);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        switch (requestCode){
            case REQUEST_ACCESS_TYPE_OnChoice:
                typeOfCircle = data.getStringExtra("typeOfCircle");
                if (typeOfCircle.equals("circle_black2")) {
                    startCircle.setImageResource(R.drawable.circle_black2);
                } else if (typeOfCircle.equals("circle_blue2")) {
                    startCircle.setImageResource(R.drawable.circle_blue2);
                } else if (typeOfCircle.equals("circle_red2")) {
                    startCircle.setImageResource(R.drawable.circle_red2);
                } else if (typeOfCircle.equals("circle_purple2")) {
                    startCircle.setImageResource(R.drawable.circle_purple2);
                }
                //totalScore = data.getIntExtra("totalScore",5);
                //scoreText.setText("TOTAL SCORE " + totalScore);
                circle_blue2Bl = data.getBooleanExtra("circle_blue2Bl",circle_blue2Bl);
                circle_red2Bl = data.getBooleanExtra("circle_red2Bl",circle_red2Bl);
                circle_purple2Bl = data.getBooleanExtra("circle_purple2Bl",circle_purple2Bl);
                break;
            case REQUEST_ACCESS_TYPE_OnGain:
                //totalScore = data.getIntExtra("totalScore",5);
                //scoreText.setText("TOTAL SCORE " + totalScore);
                break;
        }

        anim_circle = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        startCircle.startAnimation(anim_circle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] projection = {
                CULUMN_ID,
                CULUMN_CURRENT_COUNT
        };

        String selection = CULUMN_ID + " = ?";
        String[] selectionArgs = { "0" };

        cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToNext();
        int columnIndex = cursor.getColumnIndex(CULUMN_CURRENT_COUNT);
        totalScore = cursor.getInt(columnIndex);
        scoreText.setText("TOTAL SCORE " + totalScore);
    }
}
