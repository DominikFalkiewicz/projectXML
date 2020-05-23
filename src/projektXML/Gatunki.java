package projektXML;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Gatunki {
	private Element gatunki;
	
	Gatunki(Element gatunki_p){
		gatunki = gatunki_p;
	}
	
	public void add(Element gatunek) {
		gatunki.appendChild(gatunek);
	}
	
	public boolean deleteGenusSpc(String rodzaj_id, String nazwa) {
		NodeList lista_gatunkow = gatunki.getElementsByTagName("gatunek");
		Element gatunek;
		for(int i = 0; i < lista_gatunkow.getLength(); i++) {
			gatunek = (Element) lista_gatunkow.item(i);
			if(gatunek.getAttribute("rodzaj") == rodzaj_id && gatunek.getElementsByTagName("gnazwa").item(0).getTextContent().equals(nazwa)) {
				gatunki.removeChild(gatunek);
				return false;
			}
		}
		return true;
	}
}
