package com.martial.salaryup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author: surajsahani
 * @Date: 20/06/22
 */
public class HorizentalLine extends View {

    private int startX = 0;
    private int startY = 0;

    private int endX = 0;
    private int endY = 0;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setDither(true);
            setColor(Color.RED);
        }
    };

    public HorizentalLine(Context context) {
        super(context);
    }

    public HorizentalLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizentalLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizentalLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(startX, startY, endX, endY, paint);

        if (endX != 300 && endY != 300) { // set end points
            endY++;
            endX++;

            postInvalidateDelayed(15); // set time here
        }
    }

}
