package com.quascenta.petersroad.droidtag.SensorCollection;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.Renderer;
import com.quascenta.petersroad.droidtag.BaseActivity;
import com.quascenta.petersroad.droidtag.HeaderView;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.RecyclerItemClickListener;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorViewModel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

/**
 * Created by AKSHAY on 1/31/2017.
 */

public class DevicesActivity extends BaseActivity {
    public static final String TAG = "DevicesActivity";
    public static int DELAY_MILLIS = 10;
    DeviceRenderer.OnItemClickListener x;
    RVRendererAdapter<DeviceViewModel> adapter;
    DeviceCollectionRendererBuilder builder;
    DeviceViewCollection deviceViewModelCollection;
    @Bind(R.id.rv_devices)
    RecyclerView recyclerView;
    LineChartView chart;
    PreviewLineChartView previewChart;
    LineChartData data, previewdata;
    @Bind(R.id.lv_episodes)
    ListView episodesListView;
    DraggableView draggableView;
    HeaderView headerView;
    View header;
    private DateTime startDate;
    private DateTime endDate;
    private DeviceViewModel tvShowSelected;

    /**
     * Initialize the Activity with some injected data.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        //TODO add manual dates from the app using a calender or a date picker ...
        startDate = new DateTime(2016, 6, 10, 5, 0, 0, 0);
        endDate = new DateTime(2016, 10, 10, 5, 0, 0, 0);
        headerView = new HeaderView(this);
        deviceViewModelCollection = new DeviceViewCollection(startDate, endDate);
        builder = provideTvShowCollectionRendererBuilder(this);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);
        adapter = provideTvShowRendererAdapter(builder, deviceViewModelCollection);

        initializeDraggableView();
        initializeRecyclerView(recyclerView);
        hookListeners();
    }

    /**
     * Method triggered when the iv_fan_art widget is clicked. This method shows a toast with the tv
     * show selected.
     */


    /**
     * Initialize DraggableView.
     */
    private void initializeDraggableView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                draggableView.setVisibility(View.GONE);
                draggableView.closeToLeft();
            }
        }, DELAY_MILLIS);
    }

    /**
     * Initialize GridView with some injected data and configure OnItemClickListener.
     */
   /* private void initializeGridView() {
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
    }*/

    /**
     * Initialize recyclerview with some data and configure onItemClickListener.
     */

    private void initChart() {
        chart = (LineChartView) findViewById(R.id.cubiclinechart);
        previewChart = (PreviewLineChartView) findViewById(R.id.chart_preview);
        generateDefaultData();
        chart.setLineChartData(data);
        // Disable zoom/scroll for previewed chart, visible chart ranges depends on preview chart viewport so
        // zoom/scroll is unnecessary.
        chart.setZoomEnabled(false);
        chart.setScrollEnabled(false);
        previewChart.setLineChartData(previewdata);

        previewChart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                chart.setCurrentViewport(viewport);
            }
        });

        previewX(false);

    }


    private void initializeRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animate(recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DeviceViewModel tvShow = adapter.getItem(position);
                        tvShowSelected = tvShow;

                        initChart();

                        renderEpisodesHeader(tvShow);
                        renderEpisodes(tvShow);
                        draggableView.setVisibility(View.VISIBLE);
                        draggableView.maximize();
                    }
                }));


    }

    void animate(RecyclerView recyclerView) {
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

    /**
     * Update action bar title with the default title value.
     */
    private void resetActionBarTitle() {
        tvShowSelected = null;
        setTitle("DroidTag");
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

    /**
     * Configure a view as episodes ListView header with the name of the tv show and the season.
     */
    private void renderEpisodesHeader(DeviceViewModel tvShow) {
        try {
            episodesListView.removeHeaderView(headerView);
            //   header = (View) getLayoutInflater().inflate(R.layout.view_header, null);
            episodesListView.setAdapter(null);
            episodesListView.addHeaderView(headerView);

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


    private void generateDefaultData() {
        int numValues = 50;

        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, (float) Math.random() * 100f));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN);
        line.setHasPoints(false);// too many values so don't draw points.

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        previewdata = new LineChartData(lines);
        data = new LineChartData(lines);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(false));

        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.
        //data = new LineChartData(data);
        previewdata.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);

    }

    private void previewY() {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dy = tempViewport.height() / 4;
        tempViewport.inset(0, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
        previewChart.setZoomType(ZoomType.VERTICAL);
    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 4;
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
        float dx = tempViewport.width() / 4;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }


}