package com.example.kotytoger.memgame2;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

//

//

import java.util.Locale;
import java.util.Random;

public class MediumGameActivity extends AppCompatActivity implements View.OnClickListener{

    private static final long START_TIME_IN_MILLIS = 45000;
    private TextView timer_view;
    private CountDownTimer countDownTimer;
    private boolean isTimeRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    private int counter = 0;

    private TextView textName;
    private TextView textMediumScore;
    private int score=0;

    private int numberOfElements;

    private MemoryButton[] buttons;

    private int[] buttonGraphicLocations;
    private int[] buttonGraphics;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    //


    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_game);

        timer_view = findViewById(R.id.medium_timer);
        startTimer();


        updateCountDownText();

        textMediumScore = findViewById(R.id.medium_score);
        textMediumScore.setText("Score: " + score);

        textName = findViewById(R.id.textViewName);
        textName.setText(getIntent().getStringExtra("outputNameMedium"));

        GridLayout gridLayout = findViewById(R.id.grid_layout_medium);

        int numColumns = gridLayout.getColumnCount();
        int numRows = gridLayout.getRowCount();

        numberOfElements = numColumns * numRows;

        buttons = new MemoryButton[numberOfElements];

        buttonGraphics = new int[numberOfElements / 2];

        buttonGraphics[0] = R.drawable.pic_1;
        buttonGraphics[1] = R.drawable.pic_2;
        buttonGraphics[2] = R.drawable.pic_3;
        buttonGraphics[3] = R.drawable.pic_4;
        buttonGraphics[4] = R.drawable.pic_5;
        buttonGraphics[5] = R.drawable.pic_6;
        buttonGraphics[6] = R.drawable.pic_7;
        buttonGraphics[7] = R.drawable.pic_8;


        buttonGraphicLocations = new int[numberOfElements];

        shuffleButtonGraphics();

        for(int r = 0; r < numRows; r++)
        {
            for(int c = 0; c < numColumns; c++)
            {
                MemoryButton tempButton = new MemoryButton(this, r, c, buttonGraphics[buttonGraphicLocations[r * numColumns + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColumns + c] = tempButton;
                gridLayout.addView(tempButton);
            }
        }

    }

    protected void startTimer()
    {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimeRunning = false;
                Toast.makeText(MediumGameActivity.this,"TIME'S UP! START AGAIN!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MediumGameActivity.this,MainActivity.class);
                String calculatedScore = Integer.toString(score);
                intent.putExtra("outputScore",calculatedScore);
                startActivity(intent);

                finish();

            }
        }.start();

        isTimeRunning = true;
    }

    protected void updateCountDownText()
    {
        int minutes = (int)(timeLeftInMillis/1000) / 60;
        int seconds = (int)(timeLeftInMillis/1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);

        timer_view.setText(timeLeftFormatted);

    }

    protected void shuffleButtonGraphics()
    {
        Random rand = new Random();

        for(int i = 0; i < numberOfElements; i++)
        {
            buttonGraphicLocations[i] = i % (numberOfElements / 2);
        }

        for(int i = 0; i < numberOfElements; i++)
        {
            int temp = buttonGraphicLocations[i];

            int swapIndex = rand.nextInt(16);

            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];

            buttonGraphicLocations[swapIndex] = temp;

        }
    }


    //

    @Override
    public void onClick(View view) {

        if(isBusy)
            return;

        MemoryButton button = (MemoryButton)view;

        if(button.isMatched)
            return;

        if(selectedButton1 == null)
        {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        if(selectedButton1.getId() == button.getId())
        {
            return;
        }

        if(selectedButton1.getFrontDrawableID() == button.getFrontDrawableID())
        {

            this.counter++;

            button.flip();

            button.setMatched(true);

            selectedButton1.setMatched(true);

            selectedButton1 = null;

            score += 15;
            textMediumScore.setText("Score: " + score);

            if(counter==8)
            {
                Toast.makeText(this,"YOU WON!!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MediumGameActivity.this,MainActivity.class);
                String calculatedScore = Integer.toString(score+60); //bonus for winning
                intent.putExtra("outputScore",calculatedScore);
                startActivity(intent);
                finish();

            }

            return;
        }
        else
        {
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    isBusy = false;
                }
            }, 1000);
        }
    }
}
