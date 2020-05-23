package projektXML;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		Dokument dokument = new Dokument(".\\projekt.xml", ".\\projekt_out.xml", ".\\projekt.xsd", reader);
		try {
			System.out.println("$Komenda:");
			String komenda = reader.readLine();
			while(!komenda.equals("\\q")) {
				if(komenda.equals("\\s")) {
					dokument.save();
				}
				else if (komenda.equals("\\r")){
					dokument.read();
				}
				else if (komenda.equals("nowy gatunek")){
					dokument.newSpc();
				}
				else if (komenda.equals("usun gatunek")){
					dokument.remSpc();
				}
				else if (komenda.equals("wypisz nazwe")){
					dokument.printRootName();
				}
				else if (komenda.equals("zmien nazwe")){
					dokument.changeRootName();
				}
				else if (komenda.equals("usun schemat wyjscia")){
					dokument.removeOutputSchema();
				}
				else {
					System.out.println("Nieznana komenda!");
				}
				System.out.println("$Komenda:");
				komenda = reader.readLine();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
