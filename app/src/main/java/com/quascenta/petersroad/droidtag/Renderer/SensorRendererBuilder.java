package com.quascenta.petersroad.droidtag.Renderer;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorViewModel;

import java.util.Collection;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class SensorRendererBuilder extends RendererBuilder<SensorViewModel> {

    public SensorRendererBuilder(Collection<Renderer<SensorViewModel>> prototypes) {
        super(prototypes);
    }

    @Override
    protected Class getPrototypeClass(SensorViewModel episodeViewModel) {
        return SensorRenderer.class;
    }
}
