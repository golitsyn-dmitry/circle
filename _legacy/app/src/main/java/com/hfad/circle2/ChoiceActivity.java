package com.hfad.circle2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.hfad.circle2.data.DBHelper;

import static com.hfad.circle2.data.Contract.Entry.CULUMN_CURRENT_COUNT;
import static com.hfad.circle2.data.Contract.Entry.CULUMN_ID;
import static com.hfad.circle2.data.Contract.Entry.TABLE_NAME;

public class ChoiceActivity extends AppCompatActivity {

    ImageView bigCircle;
    ImageView circle_black2, circle_blue2, circle_red2, circle_purple2;
    boolean circle_blue2Bl, circle_red2Bl, circle_purple2Bl;
    String typeOfCircle;
    Intent data = new Intent();
    Animation anim;
    int totalScore;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        bigCircle = findViewById(R.id.bigCircle);
        getValueOfCircle("circle_black2");

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

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

        Intent intent = getIntent();
        //totalScore = intent.getIntExtra("totalScore",0);
        circle_blue2Bl = intent.getBooleanExtra("circle_blue2Bl",circle_blue2Bl);
        circle_red2Bl = intent.getBooleanExtra("circle_red2Bl",circle_red2Bl);
        circle_purple2Bl = intent.getBooleanExtra("circle_purple2Bl",circle_purple2Bl);

        circle_black2 = findViewById(R.id.circle_black2);
        circle_blue2 = findViewById(R.id.circle_blue2);
        circle_red2 = findViewById(R.id.circle_red2);
        circle_purple2 = findViewById(R.id.circle_purple2);

        circle_black2.setOnClickListener(onClickCircle);
        circle_blue2.setOnClickListener(onClickCircle);
        circle_red2.setOnClickListener(onClickCircle);
        circle_purple2.setOnClickListener(onClickCircle);

        if (circle_blue2Bl) {
            circle_blue2.setImageResource(R.drawable.circle_blue2);
        }
        if (circle_red2Bl) {
            circle_red2.setImageResource(R.drawable.circle_red2);
        }
        if (circle_purple2Bl) {
            circle_purple2.setImageResource(R.drawable.circle_purple2);
        }
    }

    View.OnClickListener onClickCircle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.circle_black2:
                    bigCircle.setImageResource(R.drawable.circle_black2_big);
                    getValueOfCircle("circle_black2");
                    anim = AnimationUtils.loadAnimation(ChoiceActivity.this, R.anim.myscale);
                    bigCircle.startAnimation(anim);
                    break;
                case R.id.circle_blue2:
                    if (circle_blue2Bl) {
                        bigCircle.setImageResource(R.drawable.circle_blue2_big);
                        circle_blue2.setImageResource(R.drawable.circle_blue2);
                    } else {
                        bigCircle.setImageResource(R.drawable.circle_locked);
                    }
                    getValueOfCircle("circle_blue2");
                    anim = AnimationUtils.loadAnimation(ChoiceActivity.this, R.anim.myscale);
                    bigCircle.startAnimation(anim);
                    break;
                case R.id.circle_red2:
                    if (circle_red2Bl){
                        bigCircle.setImageResource(R.drawable.circle_red2_big);
                        circle_red2.setImageResource(R.drawable.circle_red2);
                    } else {
                        bigCircle.setImageResource(R.drawable.circle_locked);
                    }
                    getValueOfCircle("circle_red2");
                    anim = AnimationUtils.loadAnimation(ChoiceActivity.this, R.anim.myscale);
                    bigCircle.startAnimation(anim);
                    break;
                case R.id.circle_purple2:
                    if (circle_purple2Bl){
                        bigCircle.setImageResource(R.drawable.circle_purple2_big);
                        circle_purple2.setImageResource(R.drawable.circle_purple2);
                    } else {
                        bigCircle.setImageResource(R.drawable.circle_locked);
                    }
                    getValueOfCircle("circle_purple2");
                    anim = AnimationUtils.loadAnimation(ChoiceActivity.this, R.anim.myscale);
                    bigCircle.startAnimation(anim);
                    break;
            }
        }
    };

    public void OnClickChoiceCircleButton(View view) {
        if (data.getStringExtra("typeOfCircle").equals("circle_black2")) {
                //data.putExtra("totalScore", totalScore);
                setResult(RESULT_OK, data);
                finish();
        }
        if (data.getStringExtra("typeOfCircle").equals("circle_blue2")) {
            if (circle_blue2Bl){
                //data.putExtra("totalScore", totalScore);
                data.putExtra("circle_blue2Bl", circle_blue2Bl);
                setResult(RESULT_OK, data);
                finish();
            } else {
                if (totalScore >= 10) {
                    totalScore = totalScore - 10;
                    circle_blue2Bl = true;
                    bigCircle.setImageResource(R.drawable.circle_blue2_big);
                    circle_blue2.setImageResource(R.drawable.circle_blue2);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не хватает $((", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }else if (data.getStringExtra("typeOfCircle").equals("circle_red2")) {
            if (circle_red2Bl){
                //data.putExtra("totalScore", totalScore);
                data.putExtra("circle_red2Bl", circle_red2Bl);
                setResult(RESULT_OK, data);
                finish();
            } else {
                if (totalScore >= 20) {
                    totalScore = totalScore - 20;
                    circle_red2Bl = true;
                    bigCircle.setImageResource(R.drawable.circle_red2_big);
                    circle_red2.setImageResource(R.drawable.circle_red2);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не хватает $((", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        } else if (data.getStringExtra("typeOfCircle").equals("circle_purple2")) {
            if (circle_purple2Bl){
                //data.putExtra("totalScore", totalScore);
                data.putExtra("circle_purple2Bl", circle_purple2Bl);
                setResult(RESULT_OK, data);
                finish();
            } else {
                if (totalScore >= 30) {
                    totalScore = totalScore - 30;
                    circle_purple2Bl = true;
                    bigCircle.setImageResource(R.drawable.circle_purple2_big);
                    circle_purple2.setImageResource(R.drawable.circle_purple2);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не хватает $((", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    private void getValueOfCircle(String message){
        data.putExtra("typeOfCircle", message);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ContentValues values = new ContentValues();
        values.put(CULUMN_CURRENT_COUNT, totalScore);

        String selection = CULUMN_CURRENT_COUNT + " = ?";
        String[] selectionArgs = { "0" };

        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}

