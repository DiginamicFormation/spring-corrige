package fr.diginamic.appliweb.utils.pdf;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

class CellFactory {

    static Cell getCell(String libelle, String value, int fontSize, DeviceRgb titleColor, int padding) {
        Cell nameCell = new Cell();
        nameCell.setBorder(null);
        nameCell.add(new Paragraph(libelle).setFont(PdfUtils.fontBold).setFontSize(fontSize).setFontColor(titleColor));
        nameCell.add(new Paragraph(value).setFont(PdfUtils.fontRegular).setFontSize(fontSize));
        nameCell.setPadding(padding);
        return nameCell;
    }

    static Cell getCell(String libelle, PdfFont font, DeviceRgb color, int padding) {
        Cell nomCell = new Cell();
        nomCell.add(new Paragraph(libelle).setFont(font));
        nomCell.setPadding(padding);
        nomCell.setBackgroundColor(color);
        return nomCell;
    }

    static Cell getCell(String libelle, PdfFont font, int padding) {
        Cell nomCell = new Cell();
        nomCell.add(new Paragraph(libelle).setFont(font));
        nomCell.setPadding(padding);
        return nomCell;
    }
}
