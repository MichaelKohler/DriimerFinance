package driimerfinance.services;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Special case of exports: Class to create a PDF from the exported data
 * 
 * (c) 2014 Driimer Finance
 */
public class PDFExporter {
	private String outputPaht = null;
	/**
	 * Constructor
	 */
	public PDFExporter() {
//		try {
//			createPdf(this.outputPaht);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void setOutputPaht(String outputPaht) {
		this.outputPaht = outputPaht;
	}


	public void createPdf(String filename, Object object) throws DocumentException, IOException {
		if (object instanceof JTable){
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter.getInstance(document, new FileOutputStream(filename));
			// step 3
			document.open();
			// step 4
			document.add(new Paragraph("Hello World!"));
			// step 5
			document.close();
		}

	}
}
