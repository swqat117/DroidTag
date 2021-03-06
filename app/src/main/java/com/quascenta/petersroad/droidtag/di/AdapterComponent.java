package com.quascenta.petersroad.droidtag.di;

import com.quascenta.petersroad.droidtag.activities.DevicesActivity2;
import com.quascenta.petersroad.droidtag.activities.ReportListActivity;
import com.quascenta.petersroad.droidtag.fragments.AlertListFragment;
import com.quascenta.petersroad.droidtag.fragments.ReportGenerationFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AKSHAY on 2/17/2017.
 */
@Singleton
@Component(modules = MainModule.class)
public interface AdapterComponent {

    void inject(DevicesActivity2 devicesActivity2);

    void inject(AlertListFragment alertListFragment);

    void inject(ReportGenerationFragment reportGenerationFragment);

    void inject(ReportListActivity reportListActivity);
}
