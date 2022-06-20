package com.martial.salaryup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @Author: surajsahani
 * @Date: 20/06/22
 */
public class MyScanningView extends View {
    private Paint paint = new Paint();
    private int mPosY = 0;
    private boolean runAnimation = true;
    private boolean showLine = true;
    private Handler handler;
    private Runnable refreshRunnable;
    private boolean isGoingDown = true;
    private int mHeight;
    private int DELAY = 0;

    public MyScanningView(Context context) {
        super(context);
        init();
    }

    public MyScanningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyScanningView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5.0f);//make sure add stroke width otherwise line not display
        handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                refreshView();
            }
        };
    }

    @Override
    public void onDraw(Canvas canvas) {
        mHeight = canvas.getHeight();
        if (showLine) {
            canvas.drawLine(0, mPosY, canvas.getWidth(), mPosY, paint);
        }
        if (runAnimation) {
            //handler.postDelayed(refreshRunnable, DELAY);
        }
    }

    public void startAnimation() {
        runAnimation = true;
        showLine = true;
        this.invalidate();
    }

    public void stopAnimation() {
        runAnimation = false;
        showLine = false;
        reset();
        this.invalidate();
    }

    private void reset() {
        mPosY = 0;
        isGoingDown = true;
    }

    private void refreshView() {
        //Update new position of the line
        if (isGoingDown) {
            mPosY += 5;
            if (mPosY > mHeight) {
                //We invert the direction of the animation
                mPosY = mHeight;
                isGoingDown = false;
            } else {
                mPosY -= 5;
                if (mPosY < 0) {
                    //We invert the direction of the animation
                    mPosY = 0;
                    isGoingDown = true;
                }
                this.invalidate();
            }

        }
    }
}