package com.na.jefimija.layoutcustomanimation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Jefimija Petkovic on 9/18/2017.
 */

class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tv;
    public RelativeLayout rlCard;
    public RelativeLayout rlCardShadow;


    public MyViewHolder(View itemView) {
        super(itemView);

        tv = (TextView) itemView.findViewById(R.id.tv);
        rlCard = (RelativeLayout) itemView.findViewById(R.id.card);
        rlCardShadow = (RelativeLayout) itemView.findViewById(R.id.cardShadow);
    }


}
