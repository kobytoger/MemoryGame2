package com.example.kotytoger.memgame2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.GridLayout;

/**
 * Created by Koby on 06/04/2018.
 */

public class MemoryButton extends AppCompatButton {

    protected int row;
    protected int column;
    protected int frontDrawableID;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int r, int c, int frontImageDrawableId)
    {
        super(context);

        row = r;
        column = c;
        frontDrawableID = frontImageDrawableId;

        front = AppCompatDrawableManager.get().getDrawable(context,frontImageDrawableId);
        back = AppCompatDrawableManager.get().getDrawable(context,R.drawable.button_cover);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r),GridLayout.spec(c));
        tempParams.width = (int)getResources().getDisplayMetrics().density * 125;
        tempParams.height = (int)getResources().getDisplayMetrics().density * 125;

        setLayoutParams(tempParams);

    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontDrawableID() {
        return frontDrawableID;
    }

    public void flip()
    {
        if(isMatched)
            return;
        if(isFlipped)
        {
            setBackground(back);
            isFlipped = false;
        }
        else
        {
            setBackground(front);
            isFlipped = true;
        }
    }
}

