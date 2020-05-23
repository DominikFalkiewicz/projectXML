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
				System.out.println("W tej chwili w drzewie nie ma rangi \"Rodzaj\". Nie mo¿na utwo¿yæ gatunku.");
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
				System.out.println("W tej chwili w drzewie nie ma ¿adnego rodzaju. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}

			String rodzaj;
			System.out.println("Podaj nazwê rodzajow¹:");
			String proponowany_rodzaj = reader.readLine();
			boolean found = false;
			for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
				if(lista_nazw_rodzajow.get(i).equals(proponowany_rodzaj)) {
					found = true;
					rodzaj = lista_rodzajow.get(i);
					break;
				}
			}
			while(!found) {
				System.out.println("W tej chwili w drzewie nie ma rodzaju o podanej nazwie. Podaj inn¹ nazwê rodzajow¹:");
				proponowany_rodzaj = reader.readLine();
				for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
					if(lista_nazw_rodzajow.get(i).equals(proponowany_rodzaj)) {
						found = true;
						rodzaj = lista_rodzajow.get(i);
						break;
					}
				}
			}
			rodzaj = lista_rodzajow.get(lista_nazw_rodzajow.indexOf(proponowany_rodzaj));
			System.out.println(rodzaj);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void addSpc(String nazwa, String rodzaj, String nisza, String wymarly, String img, String imf, String data, String datowanie) {
		Element gatunek = master.newElement("gatunek");
		Element gnazwa = master.newElement("gnazwa");
		gnazwa.setTextContent(nazwa);
		gatunek.appendChild(gnazwa);
		if(data != null && !data.equals("")) {
			Element data_odkrycia = master.newElement("data_odkrycia");
			gnazwa.setTextContent(data);
			gatunek.appendChild(data_odkrycia);
		}
		if(datowanie != null && !datowanie.equals("")) {
			Element najwcz_datowanie = master.newElement("najwcz_datowanie");
			gnazwa.setTextContent(datowanie);
			gatunek.appendChild(najwcz_datowanie);
		}
		gatunek.setAttribute("rodzaj", rodzaj);
		gatunek.setAttribute("nisza", nisza);
		gatunek.setAttribute("wymarly", wymarly);
		gatunek.setAttribute("img", img);
		if(imf != null && !imf.equals("")) {
			gatunek.setAttribute("imf", imf);
		}
	}

}
