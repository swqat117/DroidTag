/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quascenta.petersroad.droidtag;

import android.view.LayoutInflater;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.quascenta.petersroad.droidtag.viewmodel.EpisodeViewModel;

/**
 * RendererAdapter implementation used to render episodes inside ListView or GridViews and to
 * update the Renderer information with the position to render. More info in this link: {@link
 * ://github.com/pedrovgs/Renderers}
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class EpisodeRendererAdapter extends RendererAdapter<EpisodeViewModel> {

  public EpisodeRendererAdapter( RendererBuilder rendererBuilder,
      AdapteeCollection<EpisodeViewModel> collection) {
    super( rendererBuilder, collection);
  }

  /**
   * Override method used to update the EpisodeRenderer position.
   */
  @Override protected void updateRendererExtraValues(EpisodeViewModel content,
      Renderer<EpisodeViewModel> renderer, int position) {
    super.updateRendererExtraValues(content, renderer, position);
    EpisodeRenderer episodeRenderer = (EpisodeRenderer) renderer;
    episodeRenderer.setPosition(position);
  }
}
