package com.jsdm.spark.chessclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_INIT_TIME = "INIT_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        Toast toast = Toast.makeText(this, "onCreate - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast toast = Toast.makeText(this, "onStart - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast toast = Toast.makeText(this, "onResume - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast toast = Toast.makeText(this, "onRestart - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast toast = Toast.makeText(this, "onPause - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast toast = Toast.makeText(this, "onStop - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast toast = Toast.makeText(this, "onDestroy - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
    }

    public void buttonOkClick(View view) {
//        Toast toast = Toast.makeText(this, "buttonOkClick - > MenuActivity", Toast.LENGTH_SHORT);
//        toast.show();
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTime);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_INIT_TIME, message);
        startActivity(intent);
    }
}

// TODO: add dialog before finish
// TODO: fix buttons style
