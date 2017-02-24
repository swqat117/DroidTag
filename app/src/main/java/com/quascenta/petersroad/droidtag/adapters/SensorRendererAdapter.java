package com.quascenta.petersroad.droidtag.adapters;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.quascenta.petersroad.droidtag.Renderer.SensorRenderer;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorViewModel;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class SensorRendererAdapter extends RendererAdapter<SensorViewModel> {

    public SensorRendererAdapter(RendererBuilder rendererBuilder,
                                 AdapteeCollection<SensorViewModel> collection) {
        super(rendererBuilder, collection);
    }

    /**
     * Override method used to update the EpisodeRenderer position.
     */
    @Override
    protected void updateRendererExtraValues(SensorViewModel content,
                                             Renderer<SensorViewModel> renderer, int position) {
        super.updateRendererExtraValues(content, renderer, position);
        SensorRenderer a = (SensorRenderer) renderer;
        a.setPosition(position);
    }
}
