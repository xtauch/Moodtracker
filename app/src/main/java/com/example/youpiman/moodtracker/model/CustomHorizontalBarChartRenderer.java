package com.example.youpiman.moodtracker.model;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.youpiman.moodtracker.R;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CustomHorizontalBarChartRenderer extends HorizontalBarChartRenderer{

    private final Bitmap commentImage;
    private Paint mPaint = new Paint();
    private Context context;


    public CustomHorizontalBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler, Bitmap commentImage, Context ctx) {
        super(chart, animator, viewPortHandler);
        this.commentImage = commentImage;
        this.context = ctx;

    }

    @Override
    public void drawData(Canvas c) {
        super.drawData(c);
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        super.drawDataSet(c, dataSet, index);
        drawBarImages(c, dataSet,index);
    }

    private void drawBarImages(Canvas c, IBarDataSet dataSet, int index) {
        BarBuffer buffer = mBarBuffers[index];

        float left; //avoid allocation inside loop
        float right;
        float top;
        float bottom;

        final Bitmap scaledBarImage = scaleBarImage(buffer);

        int commentWidth = scaledBarImage.getWidth();
        int commentOffset = commentWidth / 2;

        for (int j = 0; j < buffer.buffer.length * mAnimator.getPhaseX(); j += 4) {
            left = buffer.buffer[j + 2];
            right = buffer.buffer[j];
            top = buffer.buffer[j + 3];
            bottom = buffer.buffer[j + 1];

            float x = left - commentOffset;
            float y = (top + bottom)/2f - commentOffset/5;
            float xText = right + commentOffset/8;
            float yText = bottom + commentOffset/2;

            if (!mViewPortHandler.isInBoundsTop(bottom))
                break;

            if (!mViewPortHandler.isInBoundsX(right)
                    || !mViewPortHandler.isInBoundsBottom(bottom))
                continue;

            DBManagement mDBManagement = new DBManagement(context);

            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            float scaledSizeInPixels = context.getResources().getDimensionPixelSize(R.dimen.myFontSize);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setTextSize(scaledSizeInPixels);

            BarEntry entry = dataSet.getEntryForIndex(j / 4);
            float val = entry.getX();
            MoodData moodData;

            if (val == 0){
                drawText(c, "Hier", xText, yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -1);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 1){
                drawText(c, "Avant-hier", xText,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -2);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 2){
                drawText(c, "Il y'a trois jours",xText ,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -3);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 3){
                drawText(c, "Il y'a quatre jours", xText,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -4);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 4){
                drawText(c, "Il y'a cinq jours",xText ,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -5);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 5){
                drawText(c, "Il y'a six jours",xText ,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -6);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x , y);
                    }
                }
            }else if (val == 6){
                drawText(c, "Il y'a une semaine",xText ,yText);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.DATE, -7);
                Date mDate = mCalendar.getTime();
                moodData = mDBManagement.getData(dateFormat.format(mDate));
                if (moodData != null) {
                    if (moodData.getComment() != null) {
                        drawImage(c, commentImage, x, y);
                    }
                }
            }
        }
    }

    private Bitmap scaleBarImage(BarBuffer buffer) {
        float firstLeft = buffer.buffer[1];
        float firstRight = buffer.buffer[3];
        int firstWidth = (int) Math.ceil(firstRight - firstLeft);
        return Bitmap.createScaledBitmap(commentImage, firstWidth, firstWidth, false);
    }


    private void drawImage(Canvas c, Bitmap image, float x, float y) {
        if (image != null) {
            c.drawBitmap(image, x, y, null);
        }
    }
    private void drawText(Canvas c, String text,  float x, float y) {
        if (text != null) {
            c.drawText(text, x, y, mPaint);
        }

    }
}
