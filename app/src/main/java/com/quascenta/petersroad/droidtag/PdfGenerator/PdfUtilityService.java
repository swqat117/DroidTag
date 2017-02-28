package com.quascenta.petersroad.droidtag.PdfGenerator;

/**
 * Created by AKSHAY on 2/28/2017.
 */

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by AKSHAY on 2/28/2017.
 */

public class PDFUtilityService extends PdfPageEventHelper {

    // Heading of the table
    private String title;
    /**
     * The header text.
     */
    private String header;
    /**
     * The User Name.
     */
    private String username;
    /**
     * The User Name.
     */
    private String URL;
    /**
     * The template with the total number of pages.
     */
    private PdfTemplate total;

    public PDFUtilityService(String title) {
        this.title = title;
    }

    /**
     * Fills out the total number of pages before the document is closed.
     *
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
     * com.itextpdf.text.Document)
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        total.reset();
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);

    }

    /**
     * Adds a header to every page
     *
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter,
     * com.itextpdf.text.Document)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfPTable table = new PdfPTable(4);
        try {
            table.setWidths(new float[]{3, (float) 1.5, 11, 10});
            table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - 12);
            table.setLockedWidth(false);
            table.setWidthPercentage(100);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(String.format("Page %d of", writer.getPageNumber()));
            total.setWidth(100);
            Image img = Image.getInstance(total);
            Chunk conte = new Chunk(img, 0, 0);
            Phrase chunk = new Phrase(conte);

            PdfPCell cell = new PdfPCell(chunk);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(username);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(header);

            table.writeSelectedRows(0, -1, document.leftMargin() - 10, document.bottom() - 60, writer.getDirectContent());

            PdfPTable signature = null;
            int numberOfSignature = 0;
            if (numberOfSignature != 0) {
                signature = new PdfPTable(numberOfSignature);
                signature.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
                signature.setLockedWidth(true);
                signature.setWidthPercentage(100);
                signature.getDefaultCell().setFixedHeight(20);

                signature.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                for (int i = 0; i < numberOfSignature; i++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf("____________")));
                    cell1.setBorder(Rectangle.NO_BORDER);
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    signature.addCell(cell1);
                }
                for (int i = 0; i < numberOfSignature; i++) {
                    PdfPCell cell2 = new PdfPCell(new Paragraph(""));
                    cell2.setBorder(Rectangle.NO_BORDER);
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    signature.addCell(cell2);
                }
            }

            if (numberOfSignature != 0) {
                signature.writeSelectedRows(0, -100, document.left(), document.bottom() - 20, writer.getDirectContent());
            }

            try {
                Image image1 = null;
                //Used the image URL from resource

                java.net.URL logoImageURL = getClass().getClassLoader().getResource("resources/images/img-logo.png");
                //image1 = Image.getInstance(URL + "/images/img-logo.png");
                image1 = Image.getInstance(logoImageURL);
                image1.scalePercent(35);

                PdfPTable header = new PdfPTable(3);
                float[] columnWidths = {1f, 2f, 1.1f};
                header.setWidths(columnWidths);
                header.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
                header.setLockedWidth(true);
                header.setWidthPercentage(100);
                header.getDefaultCell().setPadding(15);
                header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                PdfPCell logocell = new PdfPCell(image1, false);
                logocell.setRowspan(3);
                logocell.setFixedHeight(72);
                logocell.setBorder(Rectangle.NO_BORDER);
                header.addCell(logocell);

                PdfPCell Title = new PdfPCell(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
                Title.setBorder(Rectangle.NO_BORDER);
                Title.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.addCell(Title);
                header.addCell("");
                header.completeRow();

                ArrayList<? extends PdfPRow> tmp = header.getRows(0, header.getRows().size());
                header.getRows().clear();
                header.getRows().addAll(tmp);

                header.writeSelectedRows(0, -100, document.left() + 20, document.top() + 50, writer.getDirectContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException de) {
            // logger.error(de.getMessage(), de);
            throw new ExceptionConverter(de);
        }
    }

    /**
     * Creates the PdfTemplate that will hold the total number of pages.
     *
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter,
     * com.itextpdf.text.Document)
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(15, 14);
    }

    /**
     * Allows us to change the content of the header.
     * <p>
     * header
     * The new header String
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Allows us to change the content of the header.
     * <p>
     * header
     * The new username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Allows us to change the content of the header.
     */
    public void setURL(String uRL) {
        URL = uRL;
    }

}