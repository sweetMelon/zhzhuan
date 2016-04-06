package com.tiangua.zhz.ui.view.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiangua.zhz.R;

public class ViewBottom extends FrameLayout implements OnClickListener {
    Context context = null;
    LayoutInflater inflater = null;

    View view = null;
    RelativeLayout ra_main = null;
    RelativeLayout ra_user_center = null;

    ImageView[] ivArray = new ImageView[2];
    TextView[] tvArray = new TextView[2];

    int lastIndex = 0;

    public ViewBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_bottom, null);
        addView(view);

        ra_main = (RelativeLayout) view.findViewById(R.id.ra_bottom_main);
        ra_user_center = (RelativeLayout) view.findViewById(R.id.ra_bottom_user_center);
//
//        ivArray[0] = (ImageView) view.findViewById(R.id.ra_bottom_main);
//        ivArray[1] = (ImageView) view.findViewById(R.id.iv_bottom_my);

        tvArray[0] = (TextView) view.findViewById(R.id.tv_bottom_main);
        tvArray[1] = (TextView) view.findViewById(R.id.tv_bottom_user_center);

        ra_main.setOnClickListener(this);
        ra_user_center.setOnClickListener(this);
    }

    public void select(int index) {
        refresh();
        switch (index) {
            case 0:
                lastIndex = 0;
                tvArray[0].setTextColor(context.getResources().getColor(
                        R.color.color_03a9f4));
//                ivArray[0].setImageResource(R.drawable.iv_home_press);
                break;
            case 1:
                lastIndex = 1;
                tvArray[1].setTextColor(context.getResources().getColor(
                        R.color.color_03a9f4));
//                ivArray[1].setImageResource(R.drawable.iv_cases_press);
                break;
        }
    }

    public void onClick(View v) {
        if (v == ra_main) {
            select(0);
        } else if (v == ra_user_center) {
            select(1);
        }
        bottomClickListener.onClick(lastIndex);
    }

    private void refresh() {
        tvArray[lastIndex].setTextColor(context.getResources().getColor(
                R.color.color_757575));
//
//        switch (lastIndex) {
//            case 0:
//                ivArray[0].setImageResource(R.drawable.iv_home_normal);
//                break;
//
//            case 1:
//                ivArray[1].setImageResource(R.drawable.iv_cases_normal);
//                break;
//        }
    }

    OnBottomClickListener bottomClickListener = null;

    public void setOnBottomListener(OnBottomClickListener listener) {
        this.bottomClickListener = listener;
    }

    public static interface OnBottomClickListener {
        void onClick(int index);
    }
}
