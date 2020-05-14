package com.scrooge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.displayBtn).setOnClickListener(v -> {
            Intent displayList = new Intent(this,DeptorsActivity.class);
            finish();
            startActivity(displayList);
        });
    }
}
