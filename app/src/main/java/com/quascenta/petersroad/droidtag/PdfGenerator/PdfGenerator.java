package com.quascenta.petersroad.droidtag.PdfGenerator;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.DefaultCategoryDataset;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by AKSHAY on 3/1/2017.
 */

public class PdfGenerator {
    float[] columnWidth = {1.5f, 2.5f, 2.5f, 2.5f};
    List<DeviceViewModel> deviceViewModels;
    AFreeChart chartl;
    float x = 0;
    private Context context;


    public PdfGenerator(List<DeviceViewModel> deviceViewModels, Context context) {
        this.context = context;
        this.deviceViewModels = deviceViewModels;


    }

    public File generateFile() throws Exception {
        String timeStamp = (String.valueOf(System.currentTimeMillis()));
        File file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "/Report" + timeStamp + ".pdf");
        ByteArrayOutputStream byteArrayOutputStream = generateEquipmentTrainPDF(deviceViewModels);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            byteArrayOutputStream.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d("PDF ", "1 Report Generated");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private ByteArrayOutputStream generateEquipmentTrainPDF(List<DeviceViewModel> models) throws Exception {


        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        ByteArrayOutputStream content = new ByteArrayOutputStream();


        try {
            if (models.size() > 0) {
                document.setMargins(20, 20, 100, 100);
                PdfWriter writer = PdfWriter.getInstance(document, content);
                PDFUtilityService event = new PDFUtilityService("Data Loggers", context);
                event.setHeader("Device 1");
                event.setUsername("User 1");
                event.setURL("pass");
                writer.setPageEvent(event);
                // writer.setPageSize(PageSize.A4.rotate());
                writer.setPageSize(PageSize.A4);
                writer.setMargins(20, 20, 20, 40);
                document.open();
                LineSeparator separator = new LineSeparator(FontFactory.getFont(FontFactory.HELVETICA, 5f, Font.BOLD));
                x = document.right() - document.left();
                for (int j = 0; j < models.size(); j++) {

                    document.add(addDeviceTable());
                    document.add(addSensorsTable(models, j, x));
                    document.newPage();


                    //   document.add(separator);


                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();

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
        deviceViewModels = null;
        return content;
    }

    public PdfPTable addDeviceTable() throws DocumentException {

        PdfPTable tbl_Title = new PdfPTable(4);
        tbl_Title.getDefaultCell().setBorder(0);
        tbl_Title.setTableEvent(new BorderEvent());
        tbl_Title.setSplitRows(true);
        tbl_Title.setWidthPercentage(93);
        tbl_Title.setWidths(columnWidth);
        tbl_Title.setPaddingTop(6);
        tbl_Title.addCell("Device Name : ");
        tbl_Title.addCell(deviceViewModels.get(0).getTitle());
        tbl_Title.addCell("Device ID : ");
        tbl_Title.addCell(String.valueOf(deviceViewModels.get(0).getDEVICE_ID()));
        tbl_Title.addCell("Device Source (Entrypoint) : ");
        tbl_Title.addCell(String.valueOf(deviceViewModels.get(0).getStart_company() + " " + deviceViewModels.get(0).getStart_from()));
        tbl_Title.addCell("Device Destination (Endpoint) : ");
        tbl_Title.addCell(String.valueOf(deviceViewModels.get(0).getDestination_company() + " " + deviceViewModels.get(0).getDestination_to()));
        tbl_Title.addCell("Status : ");
        tbl_Title.addCell(String.valueOf(deviceViewModels.get(0).getStringstatusState()));
        tbl_Title.addCell("Logging Inteval : ");
        tbl_Title.addCell(String.valueOf(25) + " - " + String.valueOf(55) + "C");
        tbl_Title.addCell("No of Alerts  : ");
        tbl_Title.addCell(String.valueOf(0));
        tbl_Title.addCell("Suggestive Measures : ");
        tbl_Title.addCell("N/A");
        tbl_Title.setSpacingAfter(20);
        return tbl_Title;

}


    PdfPTable addSensorsTable(List<DeviceViewModel> models, int j, float x) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidths(columnWidth);
        table.setTotalWidth(x);
        table.setHeaderRows(1);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(90);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(20f);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.BOLD);
        PdfPCell h1 = new PdfPCell(new Paragraph("S.No", headerFont));
        h1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        h1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPadding(5);
        table.addCell(h1);
        PdfPCell h2 = new PdfPCell(new Paragraph("Timeline", headerFont));
        h2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        h2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPadding(5);
        table.addCell(h2);
        PdfPCell h3 = new PdfPCell(new Paragraph("Temperature in C", headerFont));
        h3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        h3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPadding(5);
        table.addCell(h3);
        PdfPCell h4 = new PdfPCell(new Paragraph("RH in %", headerFont));
        h4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        h4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPadding(5);
        table.addCell(h4);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPaddingBottom(5);


        for (int row = 0; row < models.get(j).getSensorCollection().size(); row++) {
            Integer sno = row + 1;
            table.addCell("" + sno);
            table.addCell(models.get(j).getSensorCollection().get(row).getMonthAsLongText() + " " + models.get(j).getSensorCollection().get(row).getDateTime().getDayOfMonth());
            table.addCell(String.format("%.1f", models.get(j).getSensorCollection().get(row).getTemp_sensor_Sensor(0)));
            table.addCell(String.format("%.1f", models.get(j).getSensorCollection().get(row).getTemp_sensor_Sensor(1)));

        }
        table.completeRow();
        return table;
    }

    public AFreeChart getChart(List<DeviceViewModel> models, int i) {

        return ChartFactory.createLineChart(models.get(i).getTitle(), "Timeline", "Temperature in Celsius", createDataset(models, i), PlotOrientation.VERTICAL, true, true, false);

    }

    private DefaultCategoryDataset createDataset(List<DeviceViewModel> models, int i) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int row = 0; row < models.get(i).getSensorCollection().size(); row++) {
            dataset.addValue(models.get(i).getSensorCollection().get(row).getTemp_sensor_Sensor(0), "Timeline", models.get(i).getSensorCollection().get(row).getDateTime().getDayOfMonth() + models.get(i).getSensorCollection().get(row).getMonth());


        }
        return dataset;
    }
}




