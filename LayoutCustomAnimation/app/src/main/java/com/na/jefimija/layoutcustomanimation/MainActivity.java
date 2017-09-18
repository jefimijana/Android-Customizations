package com.na.jefimija.layoutcustomanimation;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    private static final int ITEMS_NUMBER = 10;
    private RelativeLayout smallArcLayout, bigArcLayout;
    private WidthAnimation smallArcWidthAnimation, bigArcWidthAnimation;
    private Button btnShowSmallArc, btnHideSmallArc;
    private Button btnShowBigArc, btnHideBigArc;

    private RecyclerView rv;
    public RelativeLayout rlCard, rlCardShadow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize context
        mContext = this;

        Initialization();

        Declaration();
    }

    private void Initialization() {
        smallArcLayout = (RelativeLayout) findViewById(R.id.smallArcLayout);
        bigArcLayout = (RelativeLayout) findViewById(R.id.bigArcLayout);
        //Initialize small arc buttons
        btnShowSmallArc = (Button) findViewById(R.id.btnShowSmallArc);
        btnHideSmallArc = (Button) findViewById(R.id.btnHideSmallArc);
        //Initialize big arc buttons
        btnShowBigArc = (Button) findViewById(R.id.btnShowBigArc);
        btnHideBigArc = (Button) findViewById(R.id.btnHideBigArc);

        rv = (RecyclerView) findViewById(R.id.rv);
        rlCard = (RelativeLayout) findViewById(R.id.card);
        rlCardShadow = (RelativeLayout) findViewById(R.id.cardShadow);

        List<String> items = new ArrayList<>();
        for (int i = 0; i < ITEMS_NUMBER; i++) {
            items.add(String.valueOf(i));
        }

        rv.setAdapter(new CustomAdapter(items));
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
                    smallArcWidthAnimation = new WidthAnimation(smallArcLayout, smallArcLayout.getWidth(), 1);
                else
                    smallArcLayout.startAnimation(smallArcWidthAnimation.restart());
            }
        });

        btnShowBigArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bigArcWidthAnimation != null && !bigArcWidthAnimation.getDidReverse())
                    bigArcLayout.startAnimation(bigArcWidthAnimation.reverse());
                rv.requestLayout();
            }
        });

        btnHideBigArc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bigArcWidthAnimation == null)
                    bigArcWidthAnimation = new WidthAnimation(bigArcLayout, bigArcLayout.getMeasuredWidth(), 1);
                else
                    bigArcLayout.startAnimation(bigArcWidthAnimation.restart());
            }
        });
    }


}

class CustomAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<String> items;

    public CustomAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View view = inflater.inflate(R.layout.rv_item, parent, false);

        // Return a new holder instance
        final MyViewHolder viewHolder = new MyViewHolder(view);

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    viewHolder.rlCard.setBackgroundColor(Color.parseColor("#ffff19"));
                    viewHolder.rlCardShadow.setBackgroundColor(Color.parseColor("#ffff00"));

                    Animation fadeInAnimation = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.item_select_fade_animation);
                    Animation scaleOutAnimation = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.item_select_scale_animation);

                    fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    AnimationSet combineAnimations = new AnimationSet(false);
                    combineAnimations.addAnimation(fadeInAnimation);
                    combineAnimations.addAnimation(scaleOutAnimation);

                    view.startAnimation(combineAnimations);

                } else {
                    viewHolder.rlCard.setBackgroundColor(MainActivity.mContext.getResources().getColor(R.color.cardBasicColor));
                    viewHolder.rlCard.setBackgroundColor(MainActivity.mContext.getResources().getColor(R.color.cardShadowBasicColor));
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.rlCard.setBackgroundColor(Color.parseColor("#ffff19"));
                viewHolder.rlCardShadow.setBackgroundColor(Color.parseColor("#ffff00"));

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String text = items.get(position);
        TextView textView = holder.tv;
        textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


class WidthAnimation extends Animation {

    private final View view;
    private final int fromWidth;
    private float toWidth;
    private int initialWidth;
    private boolean didReverse = false;
    private final static int DEFAULT_DURATION = 500;

    public WidthAnimation(View view, int fromWidth, int toWidth) {
        this.view = view;
        setInitialWidth(view.getWidth());
        this.fromWidth = fromWidth;
        this.toWidth = (toWidth - fromWidth);

        setup();
    }

    public void setup() {
        //Set default properites
        this.setDuration(DEFAULT_DURATION);
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

    public WidthAnimation restart() {
        this.didReverse = false;

        WidthAnimation widthAnimation = new WidthAnimation(this.view, this.getInitialWidth(), 1);
        return widthAnimation;

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

