package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import text.TextFunction;

public class MainActivity extends AppCompatActivity {
//TODO add new Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        for (int count = 0; count < 10; count++) {
            Log.d("MainActivity", "counter="+count);
        }

//        displayText();


    }

    private void displayText() {
        TextFunction tf = new TextFunction();
        TextView nt = findViewById(R.id.newtest);
        nt.setText(tf.getValue());
    }
}