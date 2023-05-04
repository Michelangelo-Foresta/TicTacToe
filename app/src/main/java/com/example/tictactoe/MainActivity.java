package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private static String x="X";
    private static String y="O";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               gameReset(v);
            }
        });

        final FloatingActionButton addNames = (FloatingActionButton) findViewById(R.id.AddNames);
        addNames.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,Popup.class));
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("counter",counter);
        savedInstanceState.putString("my_text",((TextView) findViewById(R.id.status) ).getText().toString());
        savedInstanceState.putIntArray("gamestate",gameState);
        savedInstanceState.putString("x",x);
        savedInstanceState.putString("y",y);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        ((TextView) findViewById(R.id.status) ).setText(savedInstanceState.getString("my_text"));
        counter=savedInstanceState.getInt("counter");
        gameState=savedInstanceState.getIntArray("gamestate");
        setNames(savedInstanceState.getString("x"),savedInstanceState.getString("y"));

       LinearLayout layout =(LinearLayout)findViewById(R.id.linearLayout);
       int children = layout.getChildCount();// child count of linear layout .... should be 3 (linear layouts)
        for(int i=0;i<children;i++) {
            LinearLayout x =(LinearLayout) layout.getChildAt(i); // for each liner layout there should be 3 image views....
            int grandChildren = x.getChildCount();
            for (int j=0;j<grandChildren;j++) {
                if(gameState[j+(i*3)]==0)
                    ((ImageView) x.getChildAt(j)).setImageResource(R.drawable.x);
                 else if (gameState[j+(i*3)]==1)
                    ((ImageView) x.getChildAt(j)).setImageResource(R.drawable.o);
            }
        }

    }
    boolean gameActive = true;
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset(view);
            counter=0;
        }

        else if (gameState[tappedImage] == 2) {
            counter++;


            if (counter == 9) {
                gameActive = false;
            }

            gameState[tappedImage] = activePlayer;

            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                status.setText(y+"'s Turn - Tap to play");
            }
            else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                status.setText(x+"'s Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {
                flag = 1;
                String winnerStr;
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = x+" has won\nTap screen to Reset";
                }
                else {
                    winnerStr = y+" has won\nTap screen to Reset";
                }
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Match Draw\nTap screen to Reset");
        }
    }
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        counter=0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText(x+"'s Turn - Tap to play");
    }

    public static void setNames(String xName,String yName){
       x=xName;
       y=yName;
    }

}

