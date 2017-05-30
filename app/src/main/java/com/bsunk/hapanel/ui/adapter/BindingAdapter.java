package com.bsunk.hapanel.ui.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by bryan on 5/25/17.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter({"cardview_background_color", "state"})
    public static void setLightCardViewBackgroundColor(CardView cardView, List<Integer> rgbColor, String state) {

        int colorFrom = cardView.getCardBackgroundColor().getDefaultColor();
        int colorTo = 0;
        ValueAnimator colorAnimation = null;

        switch (state) {
            case "on":
                if(rgbColor!=null) {
                    //cardView.setCardBackgroundColor(Color.argb(255, rgbColor.get(0), rgbColor.get(1), rgbColor.get(2)));
                    colorTo = Color.argb(255, rgbColor.get(0), rgbColor.get(1), rgbColor.get(2));
                    colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                }
                else {
                    //cardView.setCardBackgroundColor(Color.argb(255, 191, 226, 109));
                    colorTo = Color.argb(255, 191, 226, 109);
                    colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                }
                break;
            case "off":
                //cardView.setCardBackgroundColor(Color.argb(255, 51, 25, 0));
                colorTo = Color.argb(255, 127, 98, 98);
                colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        }

        if (colorAnimation != null && colorFrom!=colorTo) {
            colorAnimation.setDuration(250); // milliseconds
            colorAnimation.addUpdateListener(animator -> cardView.setBackgroundColor((int) animator.getAnimatedValue()));
            colorAnimation.start();
        }
    }

    @android.databinding.BindingAdapter({"change_text_color", "state"})
    public static void setTextColorDependingOnBackgroundColor(TextView textView, List<Integer> rgbColor, String state) {
        if(rgbColor!=null) {
            if((rgbColor.get(0)*0.299 + rgbColor.get(1)*0.587 + rgbColor.get(2)*0.114) < 186) {
                textView.setTextColor(Color.WHITE);
            }
            else {
                textView.setTextColor(Color.BLACK);
            }
        }
        if(state.equals("off")) {
            textView.setTextColor(Color.WHITE);
        }
    }

}
