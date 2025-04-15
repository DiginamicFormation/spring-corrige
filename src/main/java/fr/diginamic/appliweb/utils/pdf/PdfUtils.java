package fr.diginamic.appliweb.utils.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.entites.Ville;
import fr.diginamic.appliweb.utils.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class PdfUtils {

    static final DeviceRgb HEADER_COLOR = new DeviceRgb(41, 128, 185); // Bleu
    static final DeviceRgb TITLE_COLOR = new DeviceRgb(52, 152, 219); // Bleu clair
    static final DeviceRgb TABLE_HEADER_COLOR = new DeviceRgb(236, 240, 241); // Gris très clair
    static final DeviceRgb BORDER_COLOR = new DeviceRgb(189, 195, 199); // Gris clair
    static final DeviceRgb WHITE = new DeviceRgb(255, 255, 255);

    // Polices
    static PdfFont fontRegular;
    static PdfFont fontBold;
    static PdfFont fontItalic;

    public void createPdf(Departement departement, HttpServletResponse response)
            throws FileNotFoundException, IOException {

        // Initialisation du document
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        document.setMargins(36, 36, 36, 36);

        fontRegular = PdfFontFactory.createFont("Helvetica");
        fontBold = PdfFontFactory.createFont("Helvetica-Bold");
        fontItalic = PdfFontFactory.createFont("Helvetica-Oblique");

        // Création de l'en-tête
        createHeader(document, departement.getNom(), departement.getCode());

        // Ajout d'informations générales
        createInfoSection(document, departement.getNom(), departement.getCode());

        // Création de la table des villes
        createCitiesTable(document, departement.getVilles());

        // Ajout d'un pied de page
        createFooter(document);

        document.close();
    }

    private void createHeader(Document document, String nomDepartement, String codeDepartement) {
        // Bannière principale
        Table header = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
        Cell headerCell = new Cell();
        headerCell.setBackgroundColor(HEADER_COLOR);
        headerCell.setPadding(20);
        headerCell.setBorder(new SolidBorder(BORDER_COLOR, 1));

        Paragraph title = new Paragraph("Département: " + nomDepartement)
                .setFont(fontBold)
                .setFontSize(24)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER);

        headerCell.add(title);
        header.addCell(headerCell);
        document.add(header);

        // Espacement
        document.add(new Paragraph("\n"));
    }

    private void createInfoSection(Document document, String nomDepartement, String codeDepartement) {
        // Encadré d'informations générales
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
        infoTable.setBackgroundColor(new DeviceRgb(240, 248, 255)); // Couleur bleu très clair
        infoTable.setBorder(new SolidBorder(BORDER_COLOR, 1));

        // Nom du département
        infoTable.addCell(CellFactory.getCell("Nom du département:", nomDepartement, 14, TITLE_COLOR, 6));

        // Code du département
        Cell codeCell = CellFactory.getCell("Code du département:", codeDepartement, 14, TITLE_COLOR, 6);
        infoTable.addCell(codeCell);

        document.add(infoTable);

        // Espacement
        document.add(new Paragraph("\n"));
    }

    private void createCitiesTable(Document document, List<Ville> villes) {
        // Titre de la section
        Paragraph citiesTitle = new Paragraph("Les "+villes.size()+" plus grandes villes")
                .setFont(fontBold)
                .setFontSize(16)
                .setFontColor(TITLE_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(citiesTitle);

        document.add(new Paragraph("\n").setFontSize(5));

        // Création de la table
        Table citiesTable = new Table(UnitValue.createPercentArray(new float[]{3, 2})).useAllAvailableWidth();
        citiesTable.setBorder(new SolidBorder(BORDER_COLOR, 1));

        // En-tête de la table
        citiesTable.addHeaderCell(CellFactory.getCell("Nom de la ville", fontBold,TABLE_HEADER_COLOR, 6));
        citiesTable.addHeaderCell(CellFactory.getCell("Population", fontBold, TABLE_HEADER_COLOR, 6));

        // Remplissage de la table avec les données
        boolean alternate = false;
        for (Ville ville : villes) {
            DeviceRgb backgroundColor = alternate ? WHITE : new DeviceRgb(245, 245, 245);
            citiesTable.addCell(CellFactory.getCell(ville.getNom(), fontRegular, backgroundColor, 6));
            citiesTable.addCell(CellFactory.getCell(StringUtils.formatNombre(ville.getNbHabs()), fontRegular, backgroundColor, 6));

            alternate = !alternate;
        }

        document.add(citiesTable);
    }

    private void createFooter(Document document) {
        document.add(new Paragraph("\n"));

        Table footer = new Table(1).useAllAvailableWidth();
        Cell footerCell = new Cell();
        footerCell.setPadding(10);
        footerCell.setBorder(new SolidBorder(BORDER_COLOR, 0.5f));
        footerCell.setBackgroundColor(new DeviceRgb(245, 245, 245));

        Paragraph footerText = new Paragraph("Document généré automatiquement - " + java.time.LocalDate.now())
                .setFont(fontItalic)
                .setFontSize(10)
                .setFontColor(new DeviceRgb(100, 100, 100))
                .setTextAlignment(TextAlignment.CENTER);

        footerCell.add(footerText);
        footer.addCell(footerCell);
        document.add(footer);
    }
}
