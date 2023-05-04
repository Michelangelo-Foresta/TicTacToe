package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Popup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout((int)(width *.8),(int)(height*.8));

        final Button getNames = (Button) findViewById(R.id.saveNames);
        getNames.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.setNames(((EditText) findViewById(R.id.playerX)).getText().toString() ,((EditText) findViewById(R.id.playerO)).getText().toString());
            }
        });

    }
}
