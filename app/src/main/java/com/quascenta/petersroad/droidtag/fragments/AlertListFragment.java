package com.quascenta.petersroad.droidtag.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.Renderer;
import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.EventListeners.RecyclerItemClickListener;
import com.quascenta.petersroad.droidtag.MyApp;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Renderer.DeviceCollectionRendererBuilder;
import com.quascenta.petersroad.droidtag.Renderer.DeviceRenderer;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorCollection;
import com.quascenta.petersroad.droidtag.adapters.MyAdapter;
import com.quascenta.petersroad.droidtag.widgets.LineChart1;
import com.quascenta.petersroad.droidtag.widgets.PreviewLineChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by AKSHAY on 2/13/2017.
 */
public class AlertListFragment extends Fragment {

    public static final String TAG = "DevicesActivity";
    public static int DELAY_MILLIS = 10;
    public boolean isWindowSelected = false;
    public SensorCollection sensorCollection = new SensorCollection();


    RVRendererAdapter<DeviceViewModel> adapter;

    DeviceCollectionRendererBuilder builder;

    @Inject
    DeviceViewCollection deviceViewModelCollection;

    @Bind(R.id.rv_devices)
    RecyclerView recyclerView;


    LineChart1 chart;
    PreviewLineChart previewChart;


    MyAdapter adapte1r;


    DraggableView draggableView;


    float lowerlimit = 25.0f, upperlimit = 55.0f;
    StickyListHeadersListView stickyList;
    Bitmap bitmap;
    private DateTime startDate;
    private DateTime endDate;
    private DeviceViewModel tvShowSelected;


    public AlertListFragment() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.activity_device, container, false);
        ButterKnife.bind(this, ConvertView);
        init(ConvertView);
        //TODO add manual dates from the app using a calender or a date picker ...
        return ConvertView;
    }


    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }




    void init(View ConvertView) {

        chart = (LineChart1) ConvertView.findViewById(R.id.cubiclinechart);
        previewChart = (PreviewLineChart) ConvertView.findViewById(R.id.chart_preview);
        builder = provideTvShowCollectionRendererBuilder(getActivity());
        draggableView = (DraggableView) ConvertView.findViewById(R.id.draggable_view);


        adapter = provideTvShowRendererAdapter(builder, deviceViewModelCollection);
        stickyList = (StickyListHeadersListView) ConvertView.findViewById(R.id.lv_episodes);
        initializeDraggableView();
        initializeRecyclerView(recyclerView, ConvertView);
    }


    private void initializeDraggableView() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            draggableView.setVisibility(View.GONE);
            draggableView.closeToLeft();
        }, DELAY_MILLIS);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        hookListeners();
    }

    protected DeviceCollectionRendererBuilder provideTvShowCollectionRendererBuilder(
            Context context) {
        List<Renderer<DeviceViewModel>> prototypes = new LinkedList<Renderer<DeviceViewModel>>();
        prototypes.add(new DeviceRenderer(context));
        return new DeviceCollectionRendererBuilder(prototypes);
    }

    protected RVRendererAdapter<DeviceViewModel> provideTvShowRendererAdapter(
            DeviceCollectionRendererBuilder tvShowCollectionRendererBuilder,
            DeviceViewCollection tvShowCollectionViewModel) {
        return new RVRendererAdapter<DeviceViewModel>(tvShowCollectionRendererBuilder,
                tvShowCollectionViewModel);
    }

    /**
     * Hook DraggableListener to draggableView to modify action bar title with the tv show
     * information.
     */
    private void hookListeners() {
        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                updateActionBarTitle();
            }

            @Override
            public void onMinimized() {

                resetActionBarTitle();
            }

            @Override
            public void onClosedToLeft() {
                resetActionBarTitle();
            }

            @Override
            public void onClosedToRight() {
                resetActionBarTitle();
            }
        });
    }


    private void LoadChart(LineChartData data, LineChartData previewdata) {

        chart.setOnValueTouchListener(new ValueTouchListener(getContext()));
        previewChart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                chart.setCurrentViewport(viewport);
            }
        });

        previewX(true);

    }


    private void resetActionBarTitle() {
        tvShowSelected = null;
        isWindowSelected = false;
        getActivity().setTitle("DroidTag");

    }

    /**
     * Update action bar title with the tv show selected title.
     */
    private void updateActionBarTitle() {
        if (tvShowSelected != null) {
            isWindowSelected = true;
            getActivity().setTitle(tvShowSelected.getTitle());
        }
    }

    private void initializeRecyclerView(RecyclerView recyclerView, View view) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // animate(recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        isWindowSelected = true;
                        tvShowSelected = adapter.getItem(position);
                        new MyTask().execute(tvShowSelected);
                        LoadChart(chart.generateDefaultData(tvShowSelected.getSensorCollection(), lowerlimit, upperlimit), previewChart.generateDefaultData(tvShowSelected.getSensorCollection(), lowerlimit, upperlimit));

                        draggableView.setVisibility(View.VISIBLE);
                        draggableView.maximize();
                    }
                    //  renderEpisodesHeader(tvShow);
                    // renderEpisodes(tvShow);

                }));


    }

    private void previewY() {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dy = tempViewport.height() / 4;
        tempViewport.inset(0, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
        previewChart.setZoomType(ZoomType.VERTICAL);
    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getCurrentViewport());
        float dx = tempViewport.width() / 2;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }

    private void previewXY() {
        // Better to not modify viewport of any chart directly so create a copy.
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        // Make temp viewport smaller.
        float dx = tempViewport.width() / 2;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }


    //Event Bus Methods


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Events.ActivityFragmentMessage fragmentMessage) {

        lowerlimit = fragmentMessage.getLow_limit();
        upperlimit = fragmentMessage.getHigh_limit();

        LoadChart(chart.generateDefaultData(sensorCollection, lowerlimit, upperlimit), previewChart.generateDefaultData(sensorCollection, lowerlimit, upperlimit));


    }


/* void animate(RecyclerView recyclerView) {
        SlideInLeftAnimationAdapter slideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(adapter);
        slideInLeftAnimationAdapter.setDuration(1343);
        slideInLeftAnimationAdapter.setFirstOnly(false);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setAdapter(slideInLeftAnimationAdapter);
        slideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
    }*/
    /**
     * Render a list of episodes using a tv show view model with the information. This method create
     * an adapter with the episodes information to be inserted in the ListView.
     *
     * @param tvShow to render
    /*  *//*
    private void renderEpisodes(final DeviceViewModel tvShow) {
        List<Renderer<SensorViewModel>> episodeRenderers =
                new LinkedList<Renderer<SensorViewModel>>();
        episodeRenderers.add(new SensorRenderer());
        SensorRendererBuilder episodeRendererBuilder = new SensorRendererBuilder(episodeRenderers);
        SensorRendererAdapter episodesAdapter =
                new SensorRendererAdapter(episodeRendererBuilder,
                        tvShow.getSensorCollection());
        episodesListView.setAdapter(episodesAdapter);
    }

    */

    /**
     * Configure a view as episodes ListView header with the name of the tv show and the season.
     *//*
    private void renderEpisodesHeader(DeviceViewModel tvShow) {
        try {
            episodesListView.removeHeaderView(headerView);
            //   header = (View) getLayoutInflater().inflate(R.layout.view_header, null);
            episodesListView.setAdapter(null);
            episodesListView.addHeaderView(headerView,null,false);


            episodesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long id) {
                    if (tvShowSelected != null) {
                        if (position > 0) {
                            SensorViewModel episodeViewModel = tvShowSelected.getSensorCollection().get(position - 1);
                            Toast.makeText(getBaseContext(),
                                    tvShowSelected.getTitle() + " - " + episodeViewModel.getTitle(), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    class MyTask extends AsyncTask<DeviceViewModel, Void, Boolean> {

        @Override
        protected Boolean doInBackground(DeviceViewModel... deviceViewModels) {
            adapte1r = new MyAdapter(getActivity().getApplicationContext(), deviceViewModels[0]);

            return true;
        }


        @Override
        protected void onPostExecute(Boolean aVoid) {
            if (aVoid)
                stickyList.setAdapter(adapte1r);


        }


    }

}
