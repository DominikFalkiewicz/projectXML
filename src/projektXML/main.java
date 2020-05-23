package projektXML;

public class main {

	public static void main(String[] args) {
		Dokument dokument = new Dokument(".\\projekt.xml", ".\\projekt_out.xml", ".\\projekt.xsd");
		dokument.newSpc();
	}

}
