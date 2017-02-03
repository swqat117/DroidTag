package com.quascenta.petersroad.droidtag;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;
import com.quascenta.petersroad.droidtag.viewmodel.EpisodeViewModel;
import com.quascenta.petersroad.droidtag.viewmodel.TvShowCollectionViewModel;
import com.quascenta.petersroad.droidtag.viewmodel.TvShowViewModel;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class TvShowsActivity extends BaseActivity {

    private static final int DELAY_MILLIS = 10;

     RendererAdapter<TvShowViewModel> adapter;
    TvShowCollectionRendererBuilder builder;
    TvShowCollectionViewModel tvShowCollectionViewModel;
    @Bind(R.id.gv_tv_shows) GridView tvShowsGridView;
    @Bind(R.id.iv_fan_art) ImageView fanArtImageView;
    @Bind(R.id.lv_episodes) ListView episodesListView;
    DraggableView draggableView;

    TextView header;
    HeaderView headerView;

    private TvShowViewModel tvShowSelected;

    /**
     * Initialize the Activity with some injected data.
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_sample);
        tvShowCollectionViewModel = new TvShowCollectionViewModel();
        builder = provideTvShowCollectionRendererBuilder(this);
        draggableView = (DraggableView)findViewById(R.id.draggable_view);
        adapter = provideTvShowRendererAdapter(builder,tvShowCollectionViewModel);
        ButterKnife.bind(this);
        initializeDraggableView();
        initializeGridView();
        hookListeners();
    }

    /**
     * Method triggered when the iv_fan_art widget is clicked. This method shows a toast with the tv
     * show selected.
     */
    @OnClick(R.id.iv_fan_art) void onFanArtClicked() {
        Toast.makeText(this, tvShowSelected.getTitle(), Toast.LENGTH_LONG).show();
    }

    /**
     * Initialize DraggableView.
     */
    private void initializeDraggableView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                draggableView.setVisibility(View.GONE);
                draggableView.closeToRight();
            }
        }, DELAY_MILLIS);
    }

    /**
     * Initialize GridView with some injected data and configure OnItemClickListener.
     */
    private void initializeGridView() {
        tvShowsGridView.setAdapter(adapter);
        tvShowsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                              long id) {
                TvShowViewModel tvShow = adapter.getItem(position);
                tvShowSelected = tvShow;
                Picasso.with(getBaseContext())
                        .load(tvShow.getFanArt())
                        .placeholder(R.drawable.tv_show_placeholder)
                        .into(fanArtImageView);
                renderEpisodesHeader(tvShow);
                renderEpisodes(tvShow);
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
            }
        });
    }
    protected TvShowCollectionRendererBuilder provideTvShowCollectionRendererBuilder(
            Context context) {
        List<Renderer<TvShowViewModel>> prototypes = new LinkedList<Renderer<TvShowViewModel>>();
        prototypes.add(new TvShowRenderer(context));
        return new TvShowCollectionRendererBuilder(prototypes);
    }

    protected RendererAdapter<TvShowViewModel> provideTvShowRendererAdapter(
            TvShowCollectionRendererBuilder tvShowCollectionRendererBuilder,
            TvShowCollectionViewModel tvShowCollectionViewModel) {
        return new RendererAdapter<TvShowViewModel>( tvShowCollectionRendererBuilder,
                tvShowCollectionViewModel);
    }

    /**
     * Hook DraggableListener to draggableView to modify action bar title with the tv show
     * information.
     */
    private void hookListeners() {
        draggableView.setDraggableListener(new DraggableListener() {
            @Override public void onMaximized() {
                updateActionBarTitle();
            }

            @Override public void onMinimized() {
                updateActionBarTitle();
            }

            @Override public void onClosedToLeft() {
                resetActionBarTitle();
            }

            @Override public void onClosedToRight() {
                resetActionBarTitle();
            }
        });
    }

    /**
     * Update action bar title with the default title value.
     */
    private void resetActionBarTitle() {
        tvShowSelected = null;
        setTitle(R.string.tv_shows_sample_activity_title);
    }

    /**
     * Update action bar title with the tv show selected title.
     */
    private void updateActionBarTitle() {
        if (tvShowSelected != null) {
            setTitle(tvShowSelected.getTitle());
        }
    }

    /**
     * Render a list of episodes using a tv show view model with the information. This method create
     * an adapter with the episodes information to be inserted in the ListView.
     *
     * @param tvShow to render
     */
    private void renderEpisodes(final TvShowViewModel tvShow) {
        List<Renderer<EpisodeViewModel>> episodeRenderers =
                new LinkedList<Renderer<EpisodeViewModel>>();
        episodeRenderers.add(new EpisodeRenderer());
        EpisodeRendererBuilder episodeRendererBuilder = new EpisodeRendererBuilder(episodeRenderers);
        EpisodeRendererAdapter episodesAdapter =
                new EpisodeRendererAdapter( episodeRendererBuilder,
                        tvShow.getEpisodes());
        episodesListView.setAdapter(episodesAdapter);
    }

    /**
     * Configure a view as episodes ListView header with the name of the tv show and the season.
     */
    private void renderEpisodesHeader(TvShowViewModel tvShow) {
        episodesListView.removeHeaderView(header);
        headerView = new HeaderView(this);
        header.setText(tvShow.getTitle().toUpperCase() + " - SEASON 1");
        episodesListView.setAdapter(null);
        episodesListView.addHeaderView(headerView);
        episodesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                              long id) {
                if (tvShowSelected != null) {
                    if (position > 0) {
                        EpisodeViewModel episodeViewModel = tvShowSelected.getEpisodes().get(position - 1);
                        Toast.makeText(getBaseContext(),
                                tvShowSelected.getTitle() + " - " + episodeViewModel.getTitle(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }
}