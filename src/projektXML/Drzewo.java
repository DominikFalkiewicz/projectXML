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
    private BufferedReader reader;
    
	private Dokument master;
	private Element drzewo;
    private Klady klady;
    private Element gatunki;
    private Element nisze;
    private Rangi rangi;
    private Element okresy;
    
	
	public Drzewo(Dokument master_p) {
		master = master_p;
		drzewo = master.getRoot();
		klady = new Klady(master.getID("k"));
		gatunki = master.getID("g");
		nisze = master.getID("n");
		rangi = new Rangi(master.getID("r"));
		okresy = master.getID("o");
	    reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void createSpc() {
		try {
			System.out.println("GENERATOR GATUNKU");
			//Ustalenie id rangi "Rodzaj":
			String ranga_rodzaj_id = rangi.getNameId("Rodzaj");
			if(ranga_rodzaj_id == null) {
				System.out.println("W tej chwili w drzewie nie ma rangi \"Rodzaj\". Nie mo¿na utwo¿yæ gatunku.");
				return;
			}
			else if(ranga_rodzaj_id.equals("!no_rank")) {
				System.out.println("W tej chwili w drzewie nie ma ¿adnej rangi. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}
			
			//Ustalenie rodzaju:
			System.out.println("Podaj nazwê rodzajow¹:");
			String proponowany_rodzaj = reader.readLine();
			String rodzaj_id = klady.getRankedName(ranga_rodzaj_id, proponowany_rodzaj);
			if(rodzaj_id != null && rodzaj_id.equals("!no_clade")) {
				System.out.println("W tej chwili w drzewie nie ma ¿adnego rodzaju. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}
			while(rodzaj_id == null) {
				System.out.println("W tej chwili w drzewie nie ma rodzaju o podanej nazwie. Podaj inn¹ nazwê rodzajow¹:");
				proponowany_rodzaj = reader.readLine();
				rodzaj_id = klady.getRankedName(ranga_rodzaj_id, proponowany_rodzaj);
			}
			
			//Ustalenie nazwy gatunkowej:
			String nazwa;
			System.out.println("Podaj nazwê gatunkow¹:");
			String proponowana_nazwa = reader.readLine();
			while(proponowana_nazwa.equals("") || proponowana_nazwa.contains(" ")) {
				System.out.println("Podano nieprawid³ow¹ nazwê. Podaj nazwê gatunkow¹:");
				proponowana_nazwa = reader.readLine();
			}
			nazwa = proponowana_nazwa;
			
			//Ustalenie niszy:
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
				System.out.println("W tej chwili w drzewie nie ma ¿adnej niszy. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}

			System.out.println("Obecnie w drzewie s¹ nastêpuj¹ce nisze:");
			for(int i = 0; i < lista_nazw_nisz.size(); i++) {
				System.out.println("-" + lista_nazw_nisz.get(i));
			}
			
			String nisza_id = "";
			System.out.println("Podaj niszê:");
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
				System.out.println("W tej chwili w drzewie nie ma niszy o podanej nazwie. Podaj inn¹ niszê:");
				proponowana_nisza = reader.readLine();
				for(int i = 0; i < lista_nazw_nisz.size(); i++) {
					if(lista_nazw_nisz.get(i).equals(proponowana_nisza)) {
						nisza_znaleziona = true;
						nisza_id = lista_id_nisz.get(i);
						break;
					}
				}
			}
			//Ustalenie czy gatunek wymar³:
			String wymarcie;
			System.out.println("Czy gatunek wymar³? (tak/nie)");
			String proponowane_wymarcie = reader.readLine();
			while(!proponowane_wymarcie.equals("tak") && !proponowane_wymarcie.equals("nie")) {
				System.out.println("Podano nieprawid³ow¹ wartoœæ. Czy gatunek wymar³? (tak/nie)");
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
			System.out.println("Podaj œcie¿kê do awatara:");
			String proponowana_img = reader.readLine();
			while(proponowana_img.equals("")) {
				System.out.println("Podano nieprawid³ow¹ œcie¿kê. Podaj œcie¿kê do awatara:");
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
