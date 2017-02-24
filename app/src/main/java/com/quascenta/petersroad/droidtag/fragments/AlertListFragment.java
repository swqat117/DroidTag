package com.quascenta.petersroad.droidtag.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

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
import com.quascenta.petersroad.droidtag.widgets.BottomSheetFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by AKSHAY on 2/13/2017.
 */
public class AlertListFragment extends Fragment {

    public static final String TAG = "DevicesActivity";
    public static int DELAY_MILLIS = 10;
    public boolean isWindowSelected = false;
    public SensorCollection sensorCollection = new SensorCollection();
    DeviceRenderer.OnItemClickListener x;
    RVRendererAdapter<DeviceViewModel> adapter;
    DeviceCollectionRendererBuilder builder;

    @Inject
    DeviceViewCollection deviceViewModelCollection;

    @Bind(R.id.rv_devices)
    RecyclerView recyclerView;
    BottomSheetFragment bottomSheetDialogFragment = new BottomSheetFragment();


    LineChartView chart;
    PreviewLineChartView previewChart;
    LineChartData data, previewdata;
    MyAdapter adapte1r;
    DraggableView draggableView;
    View header;
    float lowerlimit = 25.0f, upperlimit = 55.0f;
    StickyListHeadersListView stickyList;
    private DateTime startDate;
    private DateTime endDate;
    private DeviceViewModel tvShowSelected;

    public AlertListFragment() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        setHasOptionsMenu(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int x = item.getItemId();
        if (!isWindowSelected) {
            switch (x) {
                case R.id.Settings:
                    //Load Settings page
                case R.id.change_theme:
                    //Load theme

            }
        } else {
            switch (x) {
                case R.id.change_limits:
                    //Load Context Menu
                    new BottomSheetFragment().show(getActivity().getSupportFragmentManager(), "pass");

                case R.id.Share:

            }
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (!isWindowSelected) {
            menu.clear();
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);

        } else {
            menu.clear();
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.draggable_menu, menu);

        }

    }


    void init(View ConvertView) {

        chart = (LineChartView) ConvertView.findViewById(R.id.cubiclinechart);
        previewChart = (PreviewLineChartView) ConvertView.findViewById(R.id.chart_preview);
        builder = provideTvShowCollectionRendererBuilder(getActivity());
        draggableView = (DraggableView) ConvertView.findViewById(R.id.draggable_view);
        adapter = provideTvShowRendererAdapter(builder, deviceViewModelCollection);
        stickyList = (StickyListHeadersListView) ConvertView.findViewById(R.id.lv_episodes);
        initializeDraggableView();
        initializeRecyclerView(recyclerView, ConvertView);
    }


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

    private void initChart(DeviceViewModel tvShow, View view) {
        generateDefaultData(tvShow.getSensorCollection(), lowerlimit, upperlimit);
        LoadChart();

    }

    private void LoadChart() {
        chart.setLineChartData(data);
        // Disable zoom/scroll for previewed chart, visible chart ranges depends on preview chart viewport so
        // zoom/scroll is unnecessary.
        chart.setZoomEnabled(false);
        chart.setScrollEnabled(false);
        chart.setOnValueTouchListener(new ValueTouchListener());
        previewChart.setLineChartData(previewdata);
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
        animate(recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        isWindowSelected = true;
                        DeviceViewModel tvShow = adapter.getItem(position);
                        tvShowSelected = tvShow;
                        sensorCollection = tvShow.getSensorCollection();
                        adapte1r = new MyAdapter(getActivity().getApplicationContext(), tvShow);
                        stickyList.setAdapter(adapte1r);
                        initChart(tvShow, view);
                        //  renderEpisodesHeader(tvShow);
                        // renderEpisodes(tvShow);
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
    private Line generateLine(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.CYAN).setFilled(false).setHasPoints(true).setPointColor(Color.CYAN).setHasLabels(true).setPointRadius(0);
    }

    private Line generatePreviewLine(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.CYAN).setFilled(false).setHasPoints(false).setPointColor(Color.CYAN).setHasLabelsOnlyForSelected(false);
    }

    private Line generateLimits(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(2).setFilled(false).setColor(Color.RED).setHasLabelsOnlyForSelected(true).setHasPoints(true).setPointRadius(2);
    }

    private Line generatepreviewLimits(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setFilled(false).setColor(Color.RED).setHasLabelsOnlyForSelected(true);
    }


    private void generateDefaultData(SensorCollection sensorCollection, float lowerlimit, float upperlimit) {


        List<PointValue> values = new ArrayList<>();
        //   List<PointValue> values1 = new ArrayList<>();
        List<PointValue> values2 = new ArrayList<>();
        List<PointValue> values3 = new ArrayList<>();
        List<PointValue> values4 = new ArrayList<>();
        List<PointValue> values5 = new ArrayList<>();
        List<AxisValue> values6 = new ArrayList<>();

        for (int i = 0; i < sensorCollection.size(); ++i) {
            values.add(new PointValue(i, sensorCollection.get(i).getTemp_sensor_Sensor(0)).setLabel(String.format("%.1f", sensorCollection.get(i).getTemp_sensor_Sensor(0)) + " C"));
            // values1.add(new PointValue(i, sensorCollection.get(i).getTemp_sensor_Sensor(1)).setLabel(String.format("%.1f",sensorCollection.get(i).getTemp_sensor_Sensor(1))+" C"));
            values2.add(new PointValue(i, lowerlimit).setLabel("Limit(Start) = " + String.valueOf(lowerlimit) + "C"));
            values3.add(new PointValue(i, upperlimit).setLabel("Limit(End) = " + String.valueOf(upperlimit) + "C"));
            values4.add(new PointValue(i, 0.0f));
            values5.add(new PointValue(i, upperlimit + 20.0f));
            values6.add(new AxisValue(i).setLabel(String.valueOf(sensorCollection.get(i).getMonth()) + " " + String.valueOf(sensorCollection.get(i).getDateTime().dayOfMonth().get())));
        }

        Line line = generateLine(values);
        Line linex = generatePreviewLine(values);
        // Line line1 = generateLine(values1);
        // Line linex1 = generatePreviewLine(values1);
        Line line2 = generateLimits(values2);
        Line line3 = generateLimits(values3);
        Line line4 = generateLineDefault(values4);
        Line line5 = generateLineDefault(values5);
        Line line6 = generatepreviewLimits(values2);
        Line line7 = generatepreviewLimits(values3);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        // lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);

        List<Line> lines1 = new ArrayList<>();
        lines1.add(linex);
        //   lines1.add(linex1);
        lines1.add(line6);
        lines1.add(line7);
        lines1.add(line4);
        lines1.add(line5);

        data = new LineChartData(lines);
        data.setValueLabelTextSize(10);
        data.setValueLabelBackgroundEnabled(false);
        previewdata = new LineChartData(lines1);



        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.
        //data = new LineChartData(data);
        data.setAxisYLeft(new Axis().setHasLines(false));
        data.setAxisXBottom(new Axis(values6).setHasLines(false));
        previewdata.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);

    }

    private Line generateLineDefault(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.TRANSPARENT);
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

    //Utility Functions


    //Event Bus Methods


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Events.ActivityFragmentMessage fragmentMessage) {

        lowerlimit = fragmentMessage.getLow_limit();
        upperlimit = fragmentMessage.getHigh_limit();
        generateDefaultData(sensorCollection, lowerlimit, upperlimit);
        LoadChart();


    }


    //Value Touch Listener for graph


    private class ValueTouchListener implements LineChartOnValueSelectListener {
        float x = 0;

        @Override
        @SuppressWarnings("deprecation")
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            x = value.getY();

        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(), "Selected: " + x, Toast.LENGTH_SHORT).show();

        }

    }


}
