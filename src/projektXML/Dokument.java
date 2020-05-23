package projektXML;

import java.io.FileOutputStream;

//rejestr do tworzenia implementacji DOM
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

//Implementacja DOM Level 3 Load & Save
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser; // Do serializacji (zapisywania) dokumentow
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;

//Konfigurator i obsluga bledow
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;

//Do pracy z dokumentem
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Dokument {
	private Drzewo drzewo;
	private Document dokument;
	private DOMImplementationRegistry registry;
	private DOMImplementationLS impl;
	private LSParser builder;
	private DOMConfiguration config;
	private DOMErrorHandler errorHandler;
	private LSSerializer domWriter;
	private LSOutput dOut;
	
	private String input;
	private String output;
	private String schema;
	
	
	public Dokument(String input_p, String output_p) {
		input = input_p;
		output = output_p;
		read(input);
		setTree();
	}
	
	public Dokument(String input_p, String output_p, String schema_p) {
		input = input_p;
		output = output_p;
		schema = schema_p;
		read();
		setTree();
	}
	
	private void setTree() {
		drzewo = new Drzewo(this);
	}
	
	public void newSpc() {
		drzewo.createSpc();
	}
	
	public Element newElement(String nazwa) {
		return dokument.createElement(nazwa);
	}
	
	public Element getID(String id) {
		return dokument.getElementById(id);
	}
	
	public Element getRoot() {
		return dokument.getDocumentElement();
	}
	
	private void read(String input) { //Bez schematu.
		try {
			//Uzyskanie buildera.
			registry = DOMImplementationRegistry.newInstance();
			impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

			//Ustawienie lapacza bledow.
			errorHandler = getErrorHandler();
		
			//Ustawienie parameterow.
			config = builder.getDomConfig();
			config.setParameter("error-handler", errorHandler);
			config.setParameter("validate", Boolean.TRUE);

			//Sparsowanie dokumentu do 'dokument'.
			dokument = builder.parseURI(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void read() { //Ze schematem.
		try {
			//Uzyskanie buildera.
			registry = DOMImplementationRegistry.newInstance();
			impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

			//Ustawienie lapacza bledow.
			errorHandler = getErrorHandler();
		
			//Ustawienie parameterow.
			config = builder.getDomConfig();
			config.setParameter("error-handler", errorHandler);
			config.setParameter("validate", Boolean.TRUE);
			config.setParameter("schema-type", "http://www.w3.org/2001/XMLSchema");
			config.setParameter("schema-location",  schema);

			//Sparsowanie dokumentu do 'dokument'.
			dokument = builder.parseURI(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		try {
			// Pozyskanie serializatora i jego konfiguratora. Konfiguracja.
			domWriter = impl.createLSSerializer();
			config = domWriter.getDomConfig();
			config.setParameter("xml-declaration", Boolean.TRUE);

			// Pozyskanie i konfiguracja wyjscia.
			dOut = impl.createLSOutput();
			dOut.setEncoding("UTF-8");
			dOut.setByteStream(new FileOutputStream(output));
		
			//Zapis.
			domWriter.write(dokument, dOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static DOMErrorHandler getErrorHandler() {
		return new DOMErrorHandler() {
			public boolean handleError(DOMError error) {
				short severity = error.getSeverity();
				if (severity == error.SEVERITY_ERROR) {
					System.out.println("[dom3-error]: " + error.getMessage());
				}
				if (severity == error.SEVERITY_WARNING) {
					System.out.println("[dom3-warning]: " + error.getMessage());
				}
				if (severity == error.SEVERITY_FATAL_ERROR) {
					System.out.println("[dom3-fatal-error]: " + error.getMessage());
				}
				return true;
			}
		};
	}

}
