package com.quascenta.petersroad.droidtag.SensorCollection;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

/**
 * Created by AKSHAY on 2/17/2017.
 */


public class MultiCheckCategory extends MultiCheckExpandableGroup {

    private int iconResId;

    public MultiCheckCategory(String title, List<DeviceViewModel> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

}
