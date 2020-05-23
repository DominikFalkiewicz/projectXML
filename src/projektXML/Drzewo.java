package projektXML;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

public class Drzewo {
	private Dokument master;
	private Element drzewo;
    private BufferedReader reader;
	
	public Drzewo(Element drzewo_p, Dokument master_p) {
		drzewo = drzewo_p;
		master = master_p;
	    reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void createSpc() {
		try {
			System.out.println("GENERATOR GATUNKU");
			//Ustalenie rodzaju:
			String ranga_rodzaj_id = null;
			Element rangi = master.getID("r");
			Element ranga;
			NodeList lista_rang = rangi.getElementsByTagName("ranga");
			for(int i = 0; i < lista_rang.getLength(); i++) {
				ranga = (Element) lista_rang.item(i);
				if(ranga.getTextContent().equals("Rodzaj")) {
					ranga_rodzaj_id = ranga.getAttribute("id");
					break;
				}
			}
			if(ranga_rodzaj_id == null) {
				System.out.println("W tej chwili w drzewie nie ma rangi \"Rodzaj\". Nie mo�na utwo�y� gatunku.");
				return;
			}

			String klad_rodzaj_id = null;
			String klad_rodzaj_nazwa = null;
			Element klady = master.getID("k");
			Element klad;
			NodeList lista_kladow = klady.getElementsByTagName("klad");
			List<String> lista_rodzajow = new ArrayList<String>();
			List<String> lista_nazw_rodzajow = new ArrayList<String>();
			for(int i = 0; i < lista_kladow.getLength(); i++) {
				klad = (Element) lista_kladow.item(i);
				if(klad.getAttribute("ranga").equals(ranga_rodzaj_id)) {
					lista_rodzajow.add(klad.getAttribute("id"));
					lista_nazw_rodzajow.add(klad.getElementsByTagName("knazwa").item(0).getTextContent());
				}
			}
			if(lista_rodzajow.size() == 0) {
				System.out.println("W tej chwili w drzewie nie ma �adnego rodzaju. Nie mo�na utwo�y� gatunku.");
				return;
			}
			
			System.out.println("Obecnie w drzewie s� nast�puj�ce rodzaje:");
			for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
				System.out.println("-" + lista_nazw_rodzajow.get(i));
			}
			String rodzaj_id = "";
			System.out.println("Podaj nazw� rodzajow�:");
			String proponowany_rodzaj = reader.readLine();
			boolean rodzaj_znaleziony = false;
			for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
				if(lista_nazw_rodzajow.get(i).equals(proponowany_rodzaj)) {
					rodzaj_znaleziony = true;
					rodzaj_id = lista_rodzajow.get(i);
					break;
				}
			}
			while(!rodzaj_znaleziony) {
				System.out.println("W tej chwili w drzewie nie ma rodzaju o podanej nazwie. Podaj inn� nazw� rodzajow�:");
				proponowany_rodzaj = reader.readLine();
				for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
					if(lista_nazw_rodzajow.get(i).equals(proponowany_rodzaj)) {
						rodzaj_znaleziony = true;
						rodzaj_id = lista_rodzajow.get(i);
						break;
					}
				}
			}
			
			//Ustalenie nazwy gatunkowej:
			String nazwa;
			System.out.println("Podaj nazw� gatunkow�:");
			String proponowana_nazwa = reader.readLine();
			while(proponowana_nazwa.equals("") || proponowana_nazwa.contains(" ")) {
				System.out.println("Podano nieprawid�ow� nazw�. Podaj nazw� gatunkow�:");
				proponowana_nazwa = reader.readLine();
			}
			nazwa = proponowana_nazwa;
			
			//Ustalenie niszy:
			Element nisze = master.getID("n");
			Element nisza;
			NodeList lista_nisz = nisze.getElementsByTagName("nisza");
			List<String> lista_nazw_nisz = new ArrayList<String>();
			List<String> lista_id_nisz = new ArrayList<String>();
			for(int i = 0; i < lista_nisz.getLength(); i++) {
				nisza = (Element) lista_nisz.item(i);
				lista_id_nisz.add(nisza.getAttribute("id"));
				lista_nazw_nisz.add(nisza.getElementsByTagName("nnazwa").item(0).getTextContent());
			}
			if(lista_id_nisz.size() == 0) {
				System.out.println("W tej chwili w drzewie nie ma �adnej niszy. Nie mo�na utwo�y� gatunku.");
				return;
			}

			System.out.println("Obecnie w drzewie s� nast�puj�ce nisze:");
			for(int i = 0; i < lista_nazw_nisz.size(); i++) {
				System.out.println("-" + lista_nazw_nisz.get(i));
			}
			
			String nisza_id = "";
			System.out.println("Podaj nisz�:");
			String proponowana_nisza = reader.readLine();
			boolean nisza_znaleziona = false;
			for(int i = 0; i < lista_nazw_nisz.size(); i++) {
				if(lista_nazw_nisz.get(i).equals(proponowana_nisza)) {
					nisza_znaleziona = true;
					nisza_id = lista_id_nisz.get(i);
					break;
				}
			}
			while(!nisza_znaleziona) {
				System.out.println("W tej chwili w drzewie nie ma niszy o podanej nazwie. Podaj inn� nisz�:");
				proponowana_nisza = reader.readLine();
				for(int i = 0; i < lista_nazw_nisz.size(); i++) {
					if(lista_nazw_nisz.get(i).equals(proponowana_nisza)) {
						nisza_znaleziona = true;
						nisza_id = lista_id_nisz.get(i);
						break;
					}
				}
			}
			//Ustalenie czy gatunek wymar�:
			String wymarcie;
			System.out.println("Czy gatunek wymar�? (tak/nie)");
			String proponowane_wymarcie = reader.readLine();
			while(!proponowane_wymarcie.equals("tak") && !proponowane_wymarcie.equals("nie")) {
				System.out.println("Podano nieprawid�ow� warto��. Czy gatunek wymar�? (tak/nie)");
				proponowane_wymarcie = reader.readLine();
			}
			if(proponowane_wymarcie.equals("tak")) {
				wymarcie = "1";
			}
			else {
				wymarcie = "0";
			}
			//Ustalenie sciezki do awatara:
			String img;
			System.out.println("Podaj �cie�k� do awatara:");
			String proponowana_img = reader.readLine();
			while(proponowana_img.equals("")) {
				System.out.println("Podano nieprawid�ow� �cie�k�. Podaj �cie�k� do awatara:");
				proponowana_img = reader.readLine();
			}
			img = proponowana_img;
			addSpc(nazwa, rodzaj_id, nisza_id, wymarcie, img, null, null, null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void addSpc(String nazwa, String rodzaj, String nisza, String wymarly, String img, String imf, String data, String datowanie) {
		Element gatunek = master.newElement("gatunek");
		Element gnazwa = master.newElement("gnazwa");
		gnazwa.setTextContent(nazwa);
		gatunek.appendChild(gnazwa);
		if(data != null) {
			Element data_odkrycia = master.newElement("data_odkrycia");
			gnazwa.setTextContent(data);
			gatunek.appendChild(data_odkrycia);
		}
		if(datowanie != null) {
			Element najwcz_datowanie = master.newElement("najwcz_datowanie");
			gnazwa.setTextContent(datowanie);
			gatunek.appendChild(najwcz_datowanie);
		}
		gatunek.setAttribute("rodzaj", rodzaj);
		gatunek.setAttribute("nisza", nisza);
		gatunek.setAttribute("wymarly", wymarly);
		gatunek.setAttribute("img", img);
		if(imf != null) {
			gatunek.setAttribute("imf", imf);
		}
		Element gatunki = master.getID("g");
		gatunki.appendChild(gatunek);
	}

}
