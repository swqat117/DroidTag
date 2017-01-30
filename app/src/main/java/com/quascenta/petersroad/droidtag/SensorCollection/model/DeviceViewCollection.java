package com.quascenta.petersroad.droidtag.SensorCollection.model;

import com.pedrogomez.renderers.AdapteeCollection;
import com.quascenta.petersroad.droidtag.viewmodel.TvShowViewModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceViewCollection implements AdapteeCollection<DeviceViewModel> {



    private List<DeviceViewModel> deviceViewModelsList;


    public DeviceViewCollection(){
        this.deviceViewModelsList = new LinkedList<DeviceViewModel>();
      //  DeviceViewModel deviceViewModel = new DeviceViewModel(0001l,"Device 1",)
    }





    @Override
    public int size() {
        return 0;
    }

    @Override
    public DeviceViewModel get(int index) {
        return null;
    }

    @Override
    public boolean add(DeviceViewModel element) {
        return false;
    }

    @Override
    public boolean remove(Object element) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends DeviceViewModel> elements) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        return false;
    }

    @Override
    public void clear() {

    }
}
