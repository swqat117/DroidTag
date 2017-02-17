package com.quascenta.petersroad.droidtag;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class MultiCheckGenre extends MultiCheckExpandableGroup {

    private int iconResId;

    public MultiCheckGenre(String title, List<Artist> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }
}