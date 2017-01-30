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

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.quascenta.petersroad.droidtag.viewmodel.TvShowViewModel;

import java.util.Collection;

/**
 * RendererBuilder implementation created to map TvShowViewModel with TvShowRenderer
 * implementations. More info in this link: {@link https://github.com/pedrovgs/Renderers}
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class TvShowCollectionRendererBuilder extends RendererBuilder<TvShowViewModel> {

  public TvShowCollectionRendererBuilder(Collection<Renderer<TvShowViewModel>> prototypes) {
    super(prototypes);
  }

  @Override protected Class getPrototypeClass(TvShowViewModel tvShowViewModel) {
    return TvShowRenderer.class;
  }
}
