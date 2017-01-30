package com.quascenta.petersroad.droidtag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;
import com.quascenta.petersroad.droidtag.viewmodel.EpisodeViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 1/30/2017.
 */


    public class EpisodeRenderer extends Renderer<EpisodeViewModel> {

        @Bind(R.id.tv_episode_number)
        TextView episodeNumberTextView;
    @Bind(R.id.tv_episode_title) TextView episodeTitleTextView;
    @Bind(R.id.tv_episode_publish_date) TextView episodeDateTextView;

        private int position;

        /**
         * Configure the position associated to this renderer.
         */
        public void setPosition(int position) {
            this.position = position;
        }

        /**
         * Apply ButterKnife inject method to support view injections.
         */
        @Override protected void setUpView(View view) {
           ButterKnife.bind(this,view);
        }

        @Override protected void hookListeners(View view) {
            //Empty
        }

        /**
         * Inflate the layout associated to this renderer
         */
        @Override protected View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            return layoutInflater.inflate(R.layout.epsiode_row, viewGroup, false);
        }

        /**
         * Render the EpisodeViewModel information.
         */
        @Override
        public void render() {
            EpisodeViewModel episode = getContent();
            episodeNumberTextView.setText(String.format("%02d", position + 1));
            episodeTitleTextView.setText(episode.getTitle());
            episodeDateTextView.setText(episode.getPublishDate());
        }
    }

