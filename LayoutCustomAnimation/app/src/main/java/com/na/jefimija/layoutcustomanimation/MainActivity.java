package com.na.jefimija.layoutcustomanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout smallArcLayout, bigArcLayout, rlHelper;
    private WidthAnimation smallArcWidthAnimation, bigArcWidthAnimation;
    private Button btnShowSmallArc, btnHideSmallArc;
    private Button btnShowBigArc, btnHideBigArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialization();

        Declaration();
    }

    private void Initialization() {
        smallArcLayout = (RelativeLayout) findViewById(R.id.smallArcLayout);
        bigArcLayout = (RelativeLayout) findViewById(R.id.bigArcLayout);
        rlHelper = (RelativeLayout) findViewById(R.id.rlHelper);
        //Initialize small arc buttons
        btnShowSmallArc = (Button) findViewById(R.id.btnShowSmallArc);
        btnHideSmallArc = (Button) findViewById(R.id.btnHideSmallArc);
        //Initialize big arc buttons
        btnShowBigArc = (Button) findViewById(R.id.btnShowBigArc);
        btnHideBigArc = (Button) findViewById(R.id.btnHideBigArc);

    }

    private void Declaration() {

        btnShowSmallArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smallArcWidthAnimation != null && !smallArcWidthAnimation.getDidReverse())
                    smallArcLayout.startAnimation(smallArcWidthAnimation.reverse());
            }
        });

        btnHideSmallArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smallArcWidthAnimation == null)
                    smallArcWidthAnimation = new WidthAnimation(smallArcLayout, smallArcLayout.getMeasuredWidth(), 1);
                else
                    smallArcWidthAnimation.restart();
                smallArcLayout.startAnimation(smallArcWidthAnimation);
            }
        });

        btnShowBigArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bigArcWidthAnimation != null && !bigArcWidthAnimation.getDidReverse())
                    bigArcLayout.startAnimation(bigArcWidthAnimation.reverse());
            }
        });

        btnHideBigArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bigArcWidthAnimation == null)
                    bigArcWidthAnimation = new WidthAnimation(bigArcLayout, bigArcLayout.getMeasuredWidth(), 1);
                else
                    bigArcWidthAnimation.restart();
                bigArcLayout.startAnimation(bigArcWidthAnimation);
            }
        });
    }


}

class WidthAnimation extends Animation {

    private final View view;
    private final int fromWidth;
    private float toWidth;
    private int initialWidth;
    private boolean didReverse = false;

    public WidthAnimation(View view, int fromWidth, int toWidth) {
        this.view = view;
        setInitialWidth(view.getMeasuredWidth());
        this.fromWidth = fromWidth;
        this.toWidth = (toWidth - fromWidth);
    }

    public void setup() {
        //Set default properites
        this.setDuration(500);
        this.setInterpolator(new DecelerateInterpolator());
    }

    public boolean getDidReverse() {
        return this.didReverse;
    }

    public void setInitialWidth(int initialWidth) {
        this.initialWidth = initialWidth;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public void restart() {
        this.didReverse = false;
    }

    public WidthAnimation reverse() {

        this.didReverse = true;

        WidthAnimation widthAnimation = new WidthAnimation(this.view, 1, this.getInitialWidth());
        return widthAnimation;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().width = (int) (fromWidth + toWidth * interpolatedTime);
        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}

