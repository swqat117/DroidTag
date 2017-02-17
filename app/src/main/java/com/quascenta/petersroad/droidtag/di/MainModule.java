package com.quascenta.petersroad.droidtag.di;

import android.app.Application;
import android.view.LayoutInflater;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AKSHAY on 2/17/2017.
 */


@Module()
public class MainModule {


    private final Application application;

    public MainModule(Application application) {
        this.application = application;
    }

    @Provides
    protected LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(application.getBaseContext());
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    DeviceViewCollection provideAllDevices() {
        return new DeviceViewCollection(new DateTime(2016, 6, 10, 5, 0, 0, 0), new DateTime(2016, 10, 10, 5, 0, 0, 0));
    }


}
