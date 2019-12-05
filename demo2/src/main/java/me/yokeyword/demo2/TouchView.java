package me.yokeyword.demo2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class TouchView {
    private View.OnClickListener mClickListener = null;
    private final Context mContext;
    private  TextView mTouchView=null;

    public TouchView(Activity activity) {
        this.mContext = activity;
        View root = activity.findViewById(android.R.id.content);
        if (root instanceof FrameLayout) {
            this.mTouchView= this.buildView((FrameLayout) root);
        } else {
            throw new RuntimeException("The current Activity rootView must be FrameLayout");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private TextView buildView(FrameLayout root) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#0EA738"));
        final TextView touchView = new TextView(root.getContext());
        touchView.setTextColor(Color.WHITE);
        touchView.setText("0");
        touchView.setMaxLines(1);
        touchView.setTextSize(13f);
        touchView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            touchView.setBackground(gradientDrawable);
        }
        gradientDrawable.setCornerRadius(dp( 25));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp( 50), dp( 50));
        params.gravity = Gravity.END;
        params.topMargin = dp(18) * 7;
        params.rightMargin = dp( 18);
        touchView.setLayoutParams(params);
        root.addView(touchView);
        touchView.setOnTouchListener(new StackViewTouchListener(touchView, dp( 18) / 4));
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            }
        });
        return touchView;
    }

    private int dp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, mContext.getResources().getDisplayMetrics());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.mClickListener = listener;
    }

    public final void setBackgroundColor(String color){
        this.setBackgroundColor(Color.parseColor(color));
    }

    public final void setBackgroundColor( @ColorInt  int  color){
        if (this.mTouchView !=null){
            Drawable background = this.mTouchView.getBackground();
            if (background instanceof GradientDrawable){
                ((GradientDrawable)background).setColor(color);
            }
        }
    }
    public final void setTextColor(String color){
        this.setTextColor(Color.parseColor(color));
    }

    public final void setTextColor( @ColorInt  int  color){
        if (this.mTouchView !=null){
            this.mTouchView.setTextColor(color);
        }
    }
    public final void setText(@NonNull CharSequence text){
        if (this.mTouchView !=null){
            this.mTouchView.setText(text);
        }
    }

    class StackViewTouchListener implements View.OnTouchListener {
        private View stackView;
        private float dX, dY = 0f;
        private float downX, downY = 0f;
        private boolean isClickState;
        private int clickLimitValue;

        public StackViewTouchListener(View stackView, int clickLimitValue) {
            this.stackView = stackView;
            this.clickLimitValue = clickLimitValue;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float X = event.getRawX();
            float Y = event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isClickState = true;
                    downX = X;
                    downY = Y;
                    dX = stackView.getX() - event.getRawX();
                    dY = stackView.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(X - downX) < clickLimitValue && Math.abs(Y - downY) < clickLimitValue && isClickState) {
                        isClickState = true;
                    } else {
                        isClickState = false;
                        stackView.setX(event.getRawX() + dX);
                        stackView.setY(event.getRawY() + dY);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (X - downX < clickLimitValue && isClickState) {
                        stackView.performClick();
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }
    }
}
