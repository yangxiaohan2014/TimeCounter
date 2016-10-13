package com.yangxiaohan.timecountview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by ${yangxiaohan} on 2016/10/12.
 */

public class TimeCountView extends View {
    private  final String TAG = getClass().getSimpleName();
    private long allTime;
    private Rect textBound;
    private Paint mPaint;
    private Handler mHander;
    private float textSize;
    private int textColor;
    private String timeStr;

    public TimeCountView(Context context) {
        super(context);
    }

    public TimeCountView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TimeCountView);
        textColor = typedArray.getColor(R.styleable.TimeCountView_textColor,Color.GREEN);
        textSize = typedArray.getDimension(R.styleable.TimeCountView_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
        mPaint = new Paint();
        textBound = new Rect();
        allTime = 3600000;

        timeStr = TimeFormatUtils.formatMillis(allTime);
        mPaint.getTextBounds(timeStr,0,timeStr.length(),textBound);
        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(allTime>=1000){
                    allTime = allTime - 1000;
                    mHander.sendEmptyMessageDelayed(0,1000);
                    postInvalidate();
                }

            }
        };

        mHander.sendEmptyMessage(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int width=0,height=0;
        if(widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(timeStr,0,timeStr.length(),textBound);
            int desireWidth = textBound.width()+getPaddingLeft()+getPaddingRight()+20;
            width = desireWidth;
        }
        if(heightMode==MeasureSpec.EXACTLY){
            height = heighSize;
        }else{
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(timeStr,0,timeStr.length(),textBound);
            int desireHeight = textBound.height()+getPaddingTop()+getPaddingBottom()+10;
            height = desireHeight;
        }
        Log.i(TAG,"width----"+width+"---height--"+height);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        timeStr = TimeFormatUtils.formatMillis(allTime);
        mPaint.getTextBounds(timeStr,0,timeStr.length(),textBound);
        Log.i(TAG,"Rectwidth----"+textBound.width()+"RectHeight--"+textBound.height());
        canvas.drawText(timeStr,0, timeStr.length(),getWidth() / 2 - textBound.width() / 2,getHeight()/2+textBound.height()/2,mPaint);
//        canvas.drawText(timeStr,0, timeStr.length(),0,0,mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHander.removeCallbacksAndMessages(null);
    }

    public void setTime(long time){
        this.allTime = time;
        postInvalidate();
    }
}
