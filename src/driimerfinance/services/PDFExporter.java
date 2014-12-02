package driimerfinance.services;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
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


	public void createPdf( Object object) throws DocumentException, IOException {
		if (object instanceof JTable){
			JTable table = (JTable) object;
			Document document = new Document(PageSize.A4.rotate());  
		    try {  
		      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPaht));  
		  
		      document.open();
		      document.addTitle("Buchungsjournal");
		      document.addSubject("Subject");
		      document.addKeywords("keyword1, keyword2, keyword3");
		      document.addAuthor("Martin Müller");
		      document.addCreator("Martin Müller");
		      PdfContentByte cb = writer.getDirectContent();  
		  
		      cb.saveState();  
		      Graphics2D g2 = cb.createGraphicsShapes(500, 500);  
		  
		      Shape oldClip = g2.getClip();  
		      g2.clipRect(0, 0, 500, 500);  
		  
		      
			  table.print(g2);  
		      g2.setClip(oldClip);  
		  
		      g2.dispose();  
		      cb.restoreState();  
		    } catch (Exception e) {  
		      System.err.println(e.getMessage());  
		    }  
		    document.close();  
		  }  
		}
}