package ss.plingpay;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sammie on 7/18/2017.
 */

public class SettingField extends View {


    private String mTitle = "";
    private String mSummary = "";


    Paint titlePaint;
    Paint summaryPaint;
    Paint linePaint;

    private int parentWidth;
    private int parentHeight;


    public SettingField(Context context) {
        super(context);
        init(null);
    }

    public SettingField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SettingField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SettingField(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        titlePaint = new Paint();
        summaryPaint = new Paint();
        linePaint = new Paint();



        if (attrs == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingField);

        mTitle = typedArray.getString(R.styleable.SettingField_title);
        mSummary = typedArray.getString(R.styleable.SettingField_summary);

        typedArray.recycle();


    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
        postInvalidate();
    }


    public void setSummary(String mSummary) {
        this.mSummary = mSummary;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawPaint(titlePaint);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(18 * getResources().getDisplayMetrics().density);
        float titleY = titlePaint.getTextSize() + 20;
        canvas.drawText(mTitle, 16, titleY, titlePaint);


        canvas.drawPaint(summaryPaint);
        summaryPaint.setColor(Color.BLACK);
        summaryPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        canvas.drawText(mSummary, 16, 100, summaryPaint);


        linePaint.setColor(Color.GRAY);
        canvas.drawLine(0, 160, parentWidth, 160, linePaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Getting parent width and height
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        this.setMeasuredDimension(parentWidth, 170);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams margins = ViewGroup.MarginLayoutParams.class.cast(getLayoutParams());
        int margin = 10;
        margins.topMargin = margin;
        margins.bottomMargin = margin;
        margins.leftMargin = margin;
        margins.rightMargin = margin;
        setLayoutParams(margins);
    }
}
