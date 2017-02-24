package com.quascenta.petersroad.droidtag.Renderer;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;

import java.util.Collection;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceCollectionRendererBuilder extends RendererBuilder<DeviceViewModel> {

    public DeviceCollectionRendererBuilder(Collection<Renderer<DeviceViewModel>> prototypes) {
        super(prototypes);
    }

    @Override
    protected Class getPrototypeClass(DeviceViewModel tvShowViewModel) {
        return DeviceRenderer.class;
    }
}
