package com.quascenta.petersroad.droidtag;

import android.app.Application;

import com.quascenta.petersroad.droidtag.di.AdapterComponent;
import com.quascenta.petersroad.droidtag.di.DaggerAdapterComponent;
import com.quascenta.petersroad.droidtag.di.MainModule;

/**
 * Created by AKSHAY on 2/17/2017.
 */

public class MyApp extends Application {
    private AdapterComponent component;

    public AdapterComponent getComponent() {
        return component;
    }

    /**
     * Override method used to initialize the dependency container graph with the MainModule.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        super.onCreate();
        component = DaggerAdapterComponent.builder().mainModule(new MainModule(this)).build();

    }

    /**
     * Inject an object to provide all the needed dependencies.
     */
    public void inject(Object object) {

    }
}
