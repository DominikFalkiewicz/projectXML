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
    private Gatunki gatunki;
    private Nisze nisze;
    private Rangi rangi;
    private Element okresy;
    
	
	public Drzewo(Dokument master_p) {
		master = master_p;
		drzewo = master.getRoot();
		klady = new Klady(master.getID("k"));
		gatunki = new Gatunki(master.getID("g"));
		nisze = new Nisze(master.getID("n"));
		rangi = new Rangi(master.getID("r"));
		okresy = master.getID("o");
	    reader = master.getReader();
	}
	
	public void printName() {
		Element nazwa = (Element) drzewo.getFirstChild().getNextSibling();
		System.out.println(nazwa.getTextContent());
	}
	
	public void removeSchema() {
		if(drzewo.hasAttribute("xmlns:xsi")) {
			drzewo.removeAttribute("xmlns:xsi");
		}
		if(drzewo.hasAttribute("xsi:noNamespaceSchemaLocation")) {
			drzewo.removeAttribute("xsi:noNamespaceSchemaLocation");
		}
		System.out.println("Usuniêto schemat.");
	}

	public void setName() {
		Element nazwa = (Element) drzewo.getFirstChild().getNextSibling();
		System.out.println("Obecna nazwa: " + nazwa.getTextContent() + ". Podaj now¹ nazwê:");
		try {
			String nn = reader.readLine();
			if (nn.isEmpty()) {
				System.out.println("Nazwa nie mo¿e byæ pusta!");
			}
			else {
				nazwa.setTextContent(nn);
				System.out.println("Nowa nazwa: " + nn);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
			List<String> lista_nazw_rodzajow = klady.getRankNameList(ranga_rodzaj_id);
			if(lista_nazw_rodzajow.size() == 0) {
				System.out.println("W tej chwili w drzewie nie ma ¿adnego rodzaju. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}
			System.out.println("Obecnie w drzewie s¹ nastêpuj¹ce rodzaje:");
			for(int i = 0; i < lista_nazw_rodzajow.size(); i++) {
				System.out.println("-" + lista_nazw_rodzajow.get(i));
			}
			
			System.out.println("Podaj nazwê rodzajow¹:");
			String proponowany_rodzaj = reader.readLine();
			String rodzaj_id = klady.getRankedNameId(ranga_rodzaj_id, proponowany_rodzaj);
			while(rodzaj_id == null) {
				System.out.println("W tej chwili w drzewie nie ma rodzaju o podanej nazwie. Podaj inn¹ nazwê rodzajow¹:");
				proponowany_rodzaj = reader.readLine();
				rodzaj_id = klady.getRankedNameId(ranga_rodzaj_id, proponowany_rodzaj);
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
			List<String> lista_nazw_nisz = nisze.getNameList();
			if(lista_nazw_nisz.size() == 0) {
				System.out.println("W tej chwili w drzewie nie ma ¿adnej niszy. Nie mo¿na utwo¿yæ gatunku.");
				return;
			}
			System.out.println("Obecnie w drzewie s¹ nastêpuj¹ce nisze:");
			for(int i = 0; i < lista_nazw_nisz.size(); i++) {
				System.out.println("-" + lista_nazw_nisz.get(i));
			}
			
			System.out.println("Podaj niszê:");
			String proponowana_nisza = reader.readLine();
			String nisza_id = nisze.getNameId(proponowana_nisza);
			while(nisza_id == null) {
				System.out.println("W tej chwili w drzewie nie ma niszy o podanej nazwie. Podaj inn¹ niszê:");
				proponowana_nisza = reader.readLine();
				nisza_id = nisze.getNameId(proponowana_nisza);
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
			
			//Ustalenie sciezki do skamieliny:
			String imf;
			System.out.println("Czy chcesz dodaæ grafikê skamieliny? (tak/nie)");
			String czy_skamielina = reader.readLine();
			while(!czy_skamielina.equals("tak") && !czy_skamielina.equals("nie")) {
				System.out.println("Podano nieprawid³ow¹ wartoœæ. Czy chcesz dodaæ grafikê skamieliny? (tak/nie)");
				czy_skamielina = reader.readLine();
			}
			if(czy_skamielina.equals("tak")) {
				System.out.println("Podaj œcie¿kê do skamieliny:");
				String proponowana_imf = reader.readLine();
				while(proponowana_imf.equals("")) {
					System.out.println("Podano nieprawid³ow¹ œcie¿kê. Podaj œcie¿kê do skamieliny:");
					proponowana_imf = reader.readLine();
				}
				imf = proponowana_imf;
			}
			else {
				imf = null;
			}
			
			//Ustalenie daty odkycia:
			String data;
			System.out.println("Czy chcesz dodaæ datê odkrycia? (tak/nie)");
			String czy_data = reader.readLine();
			while(!czy_data.equals("tak") && !czy_data.equals("nie")) {
				System.out.println("Podano nieprawid³ow¹ wartoœæ. Czy chcesz dodaæ datê odkrycia? (tak/nie)");
				czy_data = reader.readLine();
			}
			if(czy_data.equals("tak")) {
				System.out.println("Podaj datê odkrycia:");
				String proponowana_data = reader.readLine();
				while(proponowana_data.equals("")) {
					System.out.println("Podano nieprawid³ow¹ œcie¿kê. Podaj datê odkrycia:");
					proponowana_data = reader.readLine();
				}
				data = proponowana_data;
			}
			else {
				data = null;
			}
			
			//Ustalenie datowania:
			String datowanie;
			System.out.println("Czy chcesz dodaæ datowanie? (tak/nie)");
			String czy_datowanie = reader.readLine();
			while(!czy_datowanie.equals("tak") && !czy_datowanie.equals("nie")) {
				System.out.println("Podano nieprawid³ow¹ wartoœæ. Czy chcesz dodaæ datowanie? (tak/nie)");
				czy_datowanie = reader.readLine();
			}
			if(czy_datowanie.equals("tak")) {
				System.out.println("Podaj datowanie:");
				String proponowane_datowanie = reader.readLine();
				while(proponowane_datowanie.equals("")) {
					System.out.println("Podano nieprawid³ow¹ œcie¿kê. Podaj datowanie:");
					proponowane_datowanie = reader.readLine();
				}
				datowanie = proponowane_datowanie;
			}
			else {
				datowanie = null;
			}
			
			addSpc(nazwa, rodzaj_id, nisza_id, wymarcie, img, imf, data, datowanie);
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
			data_odkrycia.setTextContent(data);
			gatunek.appendChild(data_odkrycia);
		}
		if(datowanie != null) {
			Element najwcz_datowanie = master.newElement("najwcz_datowanie");
			najwcz_datowanie.setTextContent(datowanie);
			gatunek.appendChild(najwcz_datowanie);
		}
		gatunek.setAttribute("rodzaj", rodzaj);
		gatunek.setAttribute("nisza", nisza);
		gatunek.setAttribute("wymarly", wymarly);
		gatunek.setAttribute("img", img);
		if(imf != null) {
			gatunek.setAttribute("imf", imf);
		}
		gatunki.add(gatunek);
	}
	
	public void delSpc() {
		try {
			System.out.println("ANIHILATOR GATUNKU");
			//Ustalenie pelnej nazwy:
			System.out.println("Podaj pe³n¹ nazwê gatunku:");
			String nazwa_temp = reader.readLine();
			while(nazwa_temp.split(" ").length != 2) {
				System.out.println("Nieprawid³owa nazwa. Podaj pe³n¹ nazwê gatunku:");
				nazwa_temp = reader.readLine();
			}
			String[] pelna_nazwa = nazwa_temp.split(" ");
			
			//Ustalenie id rodzaju:
			String ranga_rodzaj_id = rangi.getNameId("Rodzaj");
			if(ranga_rodzaj_id == null) {
				System.out.println("W tej chwili w drzewie nie ma rangi \"Rodzaj\". Nie ma wiêc te¿ gatunków.");
				return;
			}
			else if(ranga_rodzaj_id.equals("!no_rank")) {
				System.out.println("W tej chwili w drzewie nie ma ¿adnej rangi. Nie ma wiêc te¿ gatunków.");
				return;
			}
			
			//Ustalenie rodzaju:
			String rodzaj_id = klady.getRankedNameId(ranga_rodzaj_id, pelna_nazwa[0]);
			if(rodzaj_id == null) {
				System.out.println("Nie ma takiego rodzaju.");
				return;
			}
			
			//Usuniecie gatunku:
			if(gatunki.deleteGenusSpc(rodzaj_id, pelna_nazwa[1])) {
				System.out.println("Nie ma takiego gatunku.");
			}
			else {
				System.out.println("Usuniêto " + nazwa_temp + ".");
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
