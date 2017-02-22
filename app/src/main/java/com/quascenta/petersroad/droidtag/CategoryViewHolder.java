package com.quascenta.petersroad.droidtag;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class CategoryViewHolder extends GroupViewHolder {

    private TextView genreName;
    private ImageView arrow;
    private ImageView icon;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        genreName = (TextView) itemView.findViewById(R.id.list_item_category_tv_name);
        arrow = (ImageView) itemView.findViewById(R.id.list_item_category_iv_down);
        icon = (ImageView) itemView.findViewById(R.id.list_item_category_iv_icon);
    }

    public void setGenreTitle(ExpandableGroup genre) {
        if (genre instanceof Genre) {
            genreName.setText(genre.getTitle());
            icon.setBackgroundResource(((Genre) genre).getIconResId());
        }
        if (genre instanceof MultiCheckCategory) {
            genreName.setText(genre.getTitle());
            icon.setBackgroundResource(((MultiCheckCategory) genre).getIconResId());

        }
        if (genre instanceof SingleCheckGenre) {
            genreName.setText(genre.getTitle());
            icon.setBackgroundResource(((SingleCheckGenre) genre).getIconResId());
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }


    public void setImage(String x) {

        if (x.equalsIgnoreCase("data not uploaded")) {
            icon.setImageResource(R.drawable.ic_place_yellow);
        } else if (x.equalsIgnoreCase("data alert")) {
            icon.setImageResource(R.drawable.ic_place_black_24dp);
        } else if (x.equalsIgnoreCase(" success")) {
            icon.setImageResource(R.drawable.ic_place_green_24dp);
        }

    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
