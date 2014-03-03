package drow.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.*;

import sl.docx.DocxDocument;
import sl.docx.DocxEditorKit;
import drow.view.DocumentView;

public class Exporter {
	
	private DocumentView docView;
	private JTextPane textPane;
	private DocxDocument styledDocument;
	
	public Exporter(DocumentView docView) {
		this.docView = docView;
		this.textPane = docView.getDrowDocument().getTextPane();
		this.styledDocument = docView.getDrowDocument().getStyledDocument();
	}
	
	public void exportFile(String fileName, FileFilter fileFilter) {
		DrowFileFilter dFilter = (DrowFileFilter)fileFilter;
		
		// TODO: check if extension exists before adding it
		fileName += dFilter.getFullExtension();
		
		if(fileFilter.equals(Filters.DOC)) {
			asDoc(fileName);
		}
		
		if(fileFilter.equals(Filters.DOCX)) {
			asDocx(fileName);
		}

		if(fileFilter.equals(Filters.RTF)) {
			asRtf(fileName);
		}
		
		if(fileFilter.equals(Filters.TXT)) {
			asTxt(fileName);
		}
		
		docView.setCurrentFileName(fileName);
		docView.setTitle(fileName);
		docView.setChanged(false);
		docView.getDrowGui().getActionSave().setEnabled(false);
	}

	private void asDoc(String fileName) {

	}

	private void asDocx(String fileName) {
		textPane.setEditorKit(new DocxEditorKit());
		
		try {
			textPane.getEditorKit().write(new FileOutputStream(fileName), styledDocument, 0, styledDocument.getLength());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textPane.setStyledDocument(styledDocument);
	}

	private void asTxt(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			docView.getDrowDocument().getTextPane().write(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void asRtf(String fileName) {

	}
}
