package com.example.kotytoger.memgame2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DifficultyChoiceActivity extends AppCompatActivity {


    private TextView textName;
    private TextView textAge;


    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_choice);

        buttonEasy = findViewById(R.id.button_easy);
        buttonMedium = findViewById(R.id.button_medium);
        buttonHard = findViewById(R.id.button_hard);

        textName = findViewById(R.id.welcomedNameText);
        textName.setText(getIntent().getStringExtra("outputName"));
        textAge = findViewById(R.id.welcomedAgeText);
        textAge.setText(getIntent().getStringExtra("outputAge"));

        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyChoiceActivity.this,EasyGameActivity.class);
                intent.putExtra("outputNameEasy",getIntent().getStringExtra("outputName"));
                startActivity(intent);
            }
        });

        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyChoiceActivity.this,MediumGameActivity.class);
                intent.putExtra("outputNameMedium",getIntent().getStringExtra("outputName"));
                startActivity(intent);
            }
        });

        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyChoiceActivity.this,HardGameActivity.class);
                intent.putExtra("outputNameHard",getIntent().getStringExtra("outputName"));
                startActivity(intent);
            }
        });
    }
}
