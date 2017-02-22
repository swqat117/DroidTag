package com.quascenta.petersroad.droidtag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class MultiCheckCategoryAdapter extends
        CheckableChildRecyclerViewAdapter<CategoryViewHolder, MultiCheckDeviceViewHolder1> {

    DeviceViewCollection deviceViewCollection;

    public MultiCheckCategoryAdapter(List<MultiCheckCategory> groups) {
        super(groups);
    }

    @Override
    public MultiCheckDeviceViewHolder1 onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_show_row1, parent, false);
        return new MultiCheckDeviceViewHolder1(view);
    }

    @Override
    public void onBindCheckChildViewHolder(MultiCheckDeviceViewHolder1 holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {

        //TODO add dependency for artist object
        final DeviceViewModel artist = (DeviceViewModel) group.getItems().get(childIndex);
        holder.setCustomer_tracking_id(String.valueOf(artist.getDEVICE_ID()));
        holder.setmSource_location(artist.getStart_from());
        holder.setmDestination_location(artist.getDestination_to());
        holder.setmSource_company_name(artist.getStart_company());
        holder.setmDestination_company_name(artist.getDestination_company());
        holder.getState(artist.getStatus());


    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        //-------------------------Created category for headers

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_report_list_group_header, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
    }


}