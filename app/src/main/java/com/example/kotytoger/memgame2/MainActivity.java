package com.example.kotytoger.memgame2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.button_start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = findViewById(R.id.nameText);
                EditText ageEditText = findViewById(R.id.ageText);

                String nameEditString = nameEditText.getText().toString();
                String ageEditString = ageEditText.getText().toString();

                Intent intent = new Intent(view.getContext(),DifficultyChoiceActivity.class);
                intent.putExtra("outputName",nameEditString);
                intent.putExtra("outputAge",ageEditString);
                startActivity(intent);



            }
        });

    }
}
