package com.quascenta.petersroad.droidtag.SensorCollection.model;

import com.pedrogomez.renderers.AdapteeCollection;
import com.quascenta.petersroad.droidtag.viewmodel.EpisodeViewModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class SensorCollection implements AdapteeCollection<SensorViewModel> {

    private final List<SensorViewModel> sensorViewModelList;






    public SensorCollection() {

        this.sensorViewModelList = new LinkedList<SensorViewModel>();
    }







    @Override
    public int size() {
        return sensorViewModelList.size();
    }





    @Override
    public SensorViewModel get(int index) {
        return sensorViewModelList.get(index);
    }




    @Override
    public boolean add(SensorViewModel element) {
        if(element != null) {
            sensorViewModelList.add(element);
            return true;
        }
        else return false;
    }




    @Override
    public boolean remove(Object element) {
        sensorViewModelList.remove(element);
        return true;
    }





    @Override
    public boolean addAll(Collection<? extends SensorViewModel> elements) {
        if (elements == null){
            return false;
        }
        else {
            sensorViewModelList.addAll(elements);
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        return false;
    }



    @Override
    public void clear() {

    }

}
