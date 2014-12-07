package driimerfinance.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTable;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;

/**
 * Special case of exports: Class to create a PDF from the exported data
 * 
 * (c) 2014 Driimer Finance
 */
public class PDFExporter {
	private String outputPath = null;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	/**
	 * Constructor
	 */
	public PDFExporter() {
	}

	public void createJournalPdf() throws DocumentException, IOException {
		Document document = new Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(outputPath));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		String documentTitle = new String("Buchungsjournal");
		document.open();
		document.addTitle(documentTitle);
		document.addSubject(documentTitle);
		document.addKeywords("keyword1, keyword2, keyword3");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator(System.getProperty("user.name"));

		// addTitlePage(document, "Buchungsjournal");
		Anchor anchor = new Anchor(documentTitle, catFont);
		anchor.setName(documentTitle);

		Chapter chapter = new Chapter(new Paragraph(anchor), 1);
		Paragraph titleParagraph = new Paragraph();
		addTitlePage(titleParagraph);
		chapter.add(titleParagraph);
		addEmptyLine(titleParagraph, 1);
		createJournalTable(chapter);

		document.add(chapter);
		document.close();
	}

	public void createErPdf(JTable jtable) throws DocumentException,
			IOException {
		Document document = new Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(outputPath));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		String documentTitle = new String("Erfolgsrechnung");
		document.open();
		document.addTitle(documentTitle);
		document.addSubject(documentTitle);
		document.addKeywords("keyword1, keyword2, keyword3");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator(System.getProperty("user.name"));

		// addTitlePage(document, "Buchungsjournal");
		Anchor anchor = new Anchor(documentTitle, catFont);
		anchor.setName(documentTitle);

		Chapter chapter = new Chapter(new Paragraph(anchor), 1);
		Paragraph titleParagraph = new Paragraph();
		addTitlePage(titleParagraph);
		chapter.add(titleParagraph);
		addEmptyLine(titleParagraph, 1);
		System.out.println("createertable");
		createErTable(chapter, jtable);

		document.add(chapter);
		document.close();
	}

	public void createBalancePdf(JTable jtable) throws DocumentException,
			IOException {
		Document document = new Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(outputPath));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		String documentTitle = new String("Bilanz");
		document.open();
		document.addTitle(documentTitle);
		document.addSubject(documentTitle);
		document.addKeywords("keyword1, keyword2, keyword3");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator(System.getProperty("user.name"));

		// addTitlePage(document, "Buchungsjournal");
		Anchor anchor = new Anchor(documentTitle, catFont);
		anchor.setName(documentTitle);

		Chapter chapter = new Chapter(new Paragraph(anchor), 1);
		Paragraph titleParagraph = new Paragraph();
		addTitlePage(titleParagraph);
		chapter.add(titleParagraph);
		addEmptyLine(titleParagraph, 1);
		System.out.println("createertable");
		createBalanceTable(chapter, jtable);

		document.add(chapter);
		document.close();
	}

	private static void addTitlePage(Paragraph para) throws DocumentException {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy:HH:mm");
		Object date = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
		Paragraph preface = new Paragraph();
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph(
				"Report erstellt von: " + System.getProperty("user.name") + ", " + date, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				smallBold));
		addEmptyLine(para, 1);
		para.add(preface);
	}

	public void setOutputPath(String outputPaht) {
		this.outputPath = outputPaht;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void createJournalTable(Section subCatPart)
			throws BadElementException {
		PdfPTable table = new PdfPTable(7);

		PdfPCell c1 = new PdfPCell(new Phrase("ID"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Datum"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Soll-Konto"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Haben-Konto"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Buchungssatz"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Betrag"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Beleg-Nr"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		MandantDBHelper helper = new MandantDBHelper();
		List<Transaction> transactions = helper.getAllTransactions();
		for (Transaction transaction : transactions) {

			Account sollAccount = helper.getAccountById(transaction
					.getFk_SollKonto());
			Account habenAccount = helper.getAccountById(transaction
					.getFk_HabenKonto());

			table.addCell(transaction.getId().toString());
			table.addCell(transaction.getStringDate());
			table.addCell(sollAccount.getName());
			table.addCell(habenAccount.getName());
			table.addCell(transaction.getBezeichnung());
			table.addCell(FinanceHelper.formatAmount(transaction.getBetrag()));
			table.addCell(transaction.getBelegNr().toString());
		}
		subCatPart.add(table);

	}

	private void createErTable(Section subCatPart, JTable jtable)
			throws BadElementException {
		int count = jtable.getRowCount();
		System.out.println("count: " + count);
		PdfPTable table = new PdfPTable(4);
		table.addCell("Aufwand");
		table.addCell("Betrag");
		table.addCell("Ertrag");
		table.addCell("Betrag");
		for (int i = 0; i < count; i++) {
			System.out.println("I: " + i);
			for (int a = 0; a <= 3; a++) {
				System.out.println("a: " + a);
				Object obj1 = GetTableData(jtable, i, a);
				String value1 = obj1.toString();
				System.out.println("TableCellValue: " + value1);
				table.addCell(value1);
			}
		}

		table.setHeaderRows(1);

		subCatPart.add(table);

	}

	private void createBalanceTable(Section subCatPart, JTable jtable)
			throws BadElementException {
		int count = jtable.getRowCount();
		System.out.println("count: " + count);
		PdfPTable table = new PdfPTable(4);
		table.addCell("Aktiven");
		table.addCell("Betrag");
		table.addCell("Passiven");
		table.addCell("Betrag");
		for (int i = 0; i < count; i++) {
			System.out.println("I: " + i);
			for (int a = 0; a <= 3; a++) {
				System.out.println("a: " + a);
				Object obj1 = GetTableData(jtable, i, a);
				String value1 = obj1.toString();
				System.out.println("TableCellValue: " + value1);
				table.addCell(value1);
			}
		}

		table.setHeaderRows(1);

		subCatPart.add(table);

	}

	public Object GetTableData(JTable table, int row_index, int col_index) {
		return table.getModel().getValueAt(row_index, col_index);
	}
}