package com.quascenta.petersroad.droidtag.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.MyApp;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.widgets.CircularProgress.ListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportListActivity extends AppCompatActivity {


    public static final int STATUS_NOT_UPLOADED = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ALERT = 2;


    @Bind(R.id.lists_rv)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton generate_Report;
    List<DeviceViewModel> deviceViewModels = new ArrayList<>();
    List<DeviceViewModel> Report_deviceViewModels = new ArrayList<>();
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fab)
    protected void onClicktoGeneratePdf() {


        //Snackbar.make(getApplicationContext(),Report_deviceViewModels.size()+"Devices added",Snackbar.LENGTH_SHORT).show();
    }


    private void setupRecyclerView(RecyclerView recyclerView, List<DeviceViewModel> deviceViewModels) {
        adapter = new ListAdapter(this, deviceViewModels, new ListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(DeviceViewModel item) {
                ValidateClick(item, Report_deviceViewModels);
            }

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onStart() {

        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessage(Events.DeviceList fragmentMessage) {
        deviceViewModels = fragmentMessage.getList();
        setupRecyclerView(recyclerView, deviceViewModels);
    }


    void ValidateClick(DeviceViewModel item, List<DeviceViewModel> report) {

        if (!item.isEnabled()) {
            item.setEnabled(true);
            report.add(item);
            generate_Report.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), " " + Report_deviceViewModels.size() + "Device(s) selected", Toast.LENGTH_SHORT).show();
        } else if (item.isEnabled()) {
            item.setEnabled(false);
            if (report != null) {
                Log.d("Report item", "removed");
                generate_Report.setVisibility(View.INVISIBLE);
                report.remove(item);
            } else {
                Toast.makeText(getApplicationContext(), "Nothing added", Toast.LENGTH_SHORT).show();
            }
        }

    }

  /*  public ByteArrayOutputStream generateEquipmentTrainPDF(List<DeviceViewModel> models) throws Exception
    {



        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        boolean groupingOption = false;


        try {
            if (models.size() > 0) {

                document.setMargins(20, 20, 100, 100);

                PdfWriter writer = PdfWriter.getInstance(document, content);
                PDFUtilityService event = new PDFUtilityService("Data Loggers");

                event.setHeader(models.get(j).getTitle());
                event.setUsername(models.get(j).getTitle());
                event.setURL("pass");
                writer.setPageEvent(event);
                // writer.setPageSize(PageSize.A4.rotate());
                writer.setPageSize(PageSize.A4);
                writer.setMargins(20, 20, 20, 40);
                document.open();

                    PdfPTable table = new PdfPTable(4);
                    float[] columnWidth = { 1.5f, 2f, 2.5f, 3f };
                    table.setWidths(columnWidth);
                    table.setTotalWidth(document.right() - document.left());
                    table.setHeaderRows(1);
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.setWidthPercentage(95);
                    table.setSpacingBefore(1f);
                    table.setSpacingAfter(1f);

                    Header headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, Color.BLACK);
                    PdfPCell h1 = new PdfPCell(new Paragraph("S.No", headerFont));

                    h1.setBackgroundColor(Color.GRAY);
                    h1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    h1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    h1.setPadding(5);
                    table.addCell(h1);
                    PdfPCell h2 = new PdfPCell(new Paragraph("Time", headerFont));
                    h2.setBackgroundColor(Color.LIGHT_GRAY);
                    h2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    h2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    h2.setPadding(5);
                    table.addCell(h2);
                    PdfPCell h3 = new PdfPCell(new Paragraph("Temperature in C", headerFont));
                    h3.setBackgroundColor(Color.LIGHT_GRAY);
                    h3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    h3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    h3.setPadding(5);
                    table.addCell(h3);
                    PdfPCell h4 = new PdfPCell(new Paragraph("RH in %", headerFont));
                    h4.setBackgroundColor(Color.LIGHT_GRAY);
                    h4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    h4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    h4.setPadding(5);
                    table.addCell(h4);

                    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.getDefaultCell().setPaddingBottom(5);
                    for (int j = 0 ; j<models.size();j++){
                    for (int row = 0; row < models.get(j).getSensorCollection().size(); row++) {
                        Integer sno = row + 1;
                        table.addCell("" + sno);
                        table.addCell(models.get(j).getSensorCollection().get(row).getDateTime().toString());
                        table.addCell();
                        equipmentNameList.clear();

                        // Equipment Bracket name
                        equipmentNameList = new ArrayList<String>();
                        Set<String> bracketEquipments = null;
                        for (Brackets bracket : equipmentGroup.get(row).getBrackets()) {
                            bracketEquipments = new HashSet<String>();
                            for (Equipment eq : bracket.getEquipments()) {
                                bracketEquipments.add(eq.getName());
                            }
                            equipmentNameList.add(bracket.getName() + " (" + StringUtils.join(bracketEquipments, ", ") + ")");
                        }
                        table.addCell(StringUtils.join(equipmentNameList, ", ").isEmpty() ? "-" : StringUtils.join(equipmentNameList, ", "));
                        equipmentNameList.clear();
                    }
                    table.completeRow();
                    document.add(table);

                }

            }
        } catch (DocumentException e) {

        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (content != null) {
                    content.close();
                }
            } catch (Exception e) {
                // Utilities.logError(e,
                // logger,CommonConstants.LOG_LEVEL_ERROR);
            }
        }
        equipmentGroup = null; // For GC MEMLEAK
        return content;
    }

*/

}
