package com.example.fnseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FnSeekBar extends LinearLayout {

    private FnSeekBarView fnSeekBarView;

    public FnSeekBar(Context context) {
        super(context);
        init();
    }

    public FnSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FnSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FnSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        fnSeekBarView = new FnSeekBarView(getContext());
        addView(fnSeekBarView);
        setGravity(Gravity.CENTER);
        requestLayout();
    }

    public void setWidthDp(int dp) {
        fnSeekBarView.setWidthDp(dp);
    }

    public void setWidthPixel(int pixel) {
        fnSeekBarView.setWidthPixel(pixel);
    }

    public void setHeightDp(int dp) {
        fnSeekBarView.setHeightDp(dp);
    }

    public void setHeightPixel(int pixel) {
        fnSeekBarView.setHeightPixel(pixel);
    }

    public void setTouchEnabled(boolean b) {
        fnSeekBarView.setTouchEnabled(b);
    }

    public void setProgressColor(int color) {
        fnSeekBarView.setProgressColor(color);
    }

    public void setBackgroundColor(int color) {
        fnSeekBarView.setBackgroundColor(color);
    }

    public void setLeftIcon(Drawable drawable) {
        fnSeekBarView.setLeftIcon(drawable);
    }

    public void setLeftIcon(Bitmap bitmap) {
        fnSeekBarView.setLeftIcon(bitmap);
    }

    public void setLeftImageTint(int color) {
        fnSeekBarView.setLeftImageTint(color);
    }

    public void setRightIcon(Drawable drawable) {
        fnSeekBarView.setRightIcon(drawable);
    }

    public void setRightIcon(Bitmap bitmap) {
        fnSeekBarView.setRightIcon(bitmap);
    }

    public void setRightImageTint(int color) {
        fnSeekBarView.setRightImageTint(color);
    }

    public void setMax(int value) {
        fnSeekBarView.setMax(value);
    }

    public void setProgress(@IntRange(from = 0, to = 100) int value) {
        fnSeekBarView.setProgress(value);
    }

    public void setEnableVibrate(boolean a) {
        fnSeekBarView.setEnableVibrate(a);
    }

    public void setEnableVibrate(boolean a, int volumeTime) {
        fnSeekBarView.setEnableVibrate(a, volumeTime);
    }

    public void setBorderRadius(int radius) {
        fnSeekBarView.setBorderRadius(radius);
    }

    OnSeekbarChangeListener onSeekbarChangeListener;

    public void setSeekbarChangeListener(OnSeekbarChangeListener onSeekbarChangeListener) {
        this.onSeekbarChangeListener = onSeekbarChangeListener;
        fnSeekBarView.setSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void values(int values) {
                onSeekbarChangeListener.values(values);
            }

            @Override
            public void stopTouch() {
                onSeekbarChangeListener.stopTouch();
            }

            @Override
            public void startTouch() {
                onSeekbarChangeListener.startTouch();
            }
        });
    }

    public interface OnSeekbarChangeListener {

        public void values(int values);

        public void stopTouch();

        public void startTouch();

    }

    private static class FnSeekBarView extends LinearLayout implements OnTouchListener {

        private ImageView mBack;
        private ImageView mFront;
        private ImageView LeftIcon;
        private ImageView RightIcon;
        private CardView Border;
        private Vibrator vibrator;
        private ConstraintLayout mParent;

        private float touchStartPos, size;
        private int value, viewSize;
        private FnSeekBar.OnSeekbarChangeListener onSeekbarChangeListener;

        private boolean getVal = false;
        private boolean enabledVibrate = false;
        private boolean maxValueVibrate = true;
        private boolean minValueVibrate = true;
        private boolean touchEnabled = true;

        int layoutWidth = 600;
        int layoutHeight = 200;
        int volumeTime = 20;
        int maxValue = 100;

        @SuppressLint("ClickableViewAccessibility")
        private FnSeekBarView(Context context) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);
            LayoutInflater.from(context).inflate(R.layout.mimseekbar, this, true);
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            mBack = findViewById(R.id.back);
            mFront = findViewById(R.id.front);
            mParent = findViewById(R.id.parent);
            LeftIcon = findViewById(R.id.left_icon);
            Border = findViewById(R.id.Border);
            RightIcon = findViewById(R.id.right_icon);
            mParent.setOnTouchListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mBack.setForceDarkAllowed(false);
                mFront.setForceDarkAllowed(false);
                mParent.setForceDarkAllowed(false);
            }
            mParent.setBackgroundColor(Color.TRANSPARENT);

            mParent.getLayoutParams().width = layoutWidth;
            mParent.getLayoutParams().height = layoutHeight;
            requestLayout();
            Border.getLayoutParams().width = layoutWidth;
            Border.getLayoutParams().height = layoutHeight;
            Border.requestLayout();

        }


        // set Width value by DPI
        public void setWidthDp(int dp) {
            layoutWidth = dp * 2;
            mParent.getLayoutParams().width = dp * 2;
            requestLayout();
            Border.getLayoutParams().width = dp * 2;
            Border.requestLayout();
        }

        // set Width value by Pixel
        public void setWidthPixel(int pixel) {
            layoutWidth = pixel;
            mParent.getLayoutParams().width = pixel;
            requestLayout();
            Border.getLayoutParams().width = pixel;
            Border.requestLayout();
        }

        // set Height value by DOI
        public void setHeightDp(int dp) {
            layoutHeight = dp * 2;
            mParent.getLayoutParams().height = dp * 2;
            requestLayout();
            Border.getLayoutParams().height = dp * 2;
            Border.requestLayout();
        }

        // set Height value by Pixel
        public void setHeightPixel(int pixel) {
            layoutHeight = pixel;
            mParent.getLayoutParams().height = pixel;
            requestLayout();
            Border.getLayoutParams().height = pixel;
            Border.requestLayout();
        }

        public void setTouchEnabled(boolean b) {
            touchEnabled = b;
        }

        // seekbar front Color
        public void setProgressColor(int Color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFront.setBackgroundTintList(ColorStateList.valueOf(Color));
            }
        }

        public void setBackgroundColor(int Color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBack.setBackgroundTintList(ColorStateList.valueOf(Color));
            }
        }

        public void setLeftIcon(Drawable drawable) {
            LeftIcon.setImageDrawable(drawable);
        }

        public void setLeftIcon(Bitmap bitmap) {
            LeftIcon.setImageBitmap(bitmap);
        }

        public void setLeftImageTint(int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LeftIcon.setImageTintList(ColorStateList.valueOf(color));
            }
        }

        public void setRightIcon(Drawable drawable) {
            RightIcon.setImageDrawable(drawable);
        }

        public void setRightIcon(Bitmap bitmap) {
            RightIcon.setImageBitmap(bitmap);
        }

        public void setRightImageTint(int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LeftIcon.setImageTintList(ColorStateList.valueOf(color));
            }
        }

        // set max progress
        public void setMax(int value) {
            maxValue = value;
        }

        // set progress seekbar
        public void setProgress(@IntRange(from = 0, to = 100) int value) {
            if (value > maxValue) {
                value = maxValue;
            } else {
                this.value = value;
            }
            mFront.getLayoutParams().width = (value * layoutWidth) / maxValue;
            mFront.requestLayout();
        }


        public void setSeekbarChangeListener(FnSeekBar.OnSeekbarChangeListener onSeekbarChangeListener) {
            this.onSeekbarChangeListener = onSeekbarChangeListener;
            getVal = true;
        }

        // calling method after addview
        public void init() {
            requestLayout();
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (touchEnabled) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        viewSize = Border.getWidth();
                        touchStartPos = event.getX();
                        mFront.getLayoutParams().width = (int) touchStartPos;
                        mFront.requestLayout();
                        size = mFront.getLayoutParams().width;
                        value = ((maxValue * mFront.getLayoutParams().width) / viewSize);
                        if (getVal) {
                            this.onSeekbarChangeListener.values(value);
                            this.onSeekbarChangeListener.startTouch();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mFront.getLayoutParams().width = (int) (size - (touchStartPos - event.getX()));
                        mFront.requestLayout();
                        if (mFront.getLayoutParams().width > viewSize) {
                            mFront.getLayoutParams().width = viewSize;
                        }
                        if (mFront.getLayoutParams().width < 0) {
                            mFront.getLayoutParams().width = 0;
                        }
                        value = ((maxValue * mFront.getLayoutParams().width) / viewSize);
                        if (getVal) {
                            this.onSeekbarChangeListener.values(value);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        value = ((maxValue * mFront.getLayoutParams().width) / viewSize);
                        if (getVal) {
                            this.onSeekbarChangeListener.stopTouch();
                        }
                        break;
                }

                if (enabledVibrate) {
                    setVibratorMethod(value);
                }
            }

            return true;
        }

        // set Vibrator
        public void setEnableVibrate(boolean a) {
            enabledVibrate = a;
            volumeTime = TIMEVIBRATE.TIME_LOW;
        }

        // set Vibrator time Value in TIMEVIBRATE class
        public void setEnableVibrate(boolean a, int VolumeTime) {
            enabledVibrate = a;
            volumeTime = VolumeTime;
        }

        //max border values = height/2
        public void setBorderRadius(int radius) {
            Border.setRadius((float) radius);
        }

        public void setVibratorMethod(int Values) {
            if (Values == 0) {
                if (minValueVibrate) {
                    vibrator.vibrate(volumeTime);
                    minValueVibrate = false;
                }
            } else if (Values == 100) {
                if (maxValueVibrate) {
                    vibrator.vibrate(volumeTime);
                    maxValueVibrate = false;
                }
            }

            if (Values < 90) {
                maxValueVibrate = true;
            }

            if (Values > 10) {
                minValueVibrate = true;
            }
        }


        public interface OnSeekbarChangeListener {

            public void values(int values);

            public void stopTouch();

            public void startTouch();

        }

    }

}
