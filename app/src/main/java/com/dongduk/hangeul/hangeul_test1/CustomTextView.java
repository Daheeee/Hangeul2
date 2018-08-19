package com.dongduk.hangeul.hangeul_test1;

/**
 * Created by jiyoungwon on 2018. 8. 19..
 */

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas){
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();

        canvas.save();

        canvas.translate(getWidth(), 0);
        canvas.rotate(90); //원하는 방향 설정


        canvas.translate(20, 0 );

        Log.d("canvas" , " top : " + getCompoundPaddingLeft());
        getLayout().draw(canvas);
        canvas.restore();
    }
}
