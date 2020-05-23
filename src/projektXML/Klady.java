package projektXML;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.ArrayList;

public class Klady {
	private Element klady;
	
	Klady(Element klady_p){
		klady = klady_p;
	}
	
	public List<String> getRankNameList(String ranga){
		NodeList lista_kladow = klady.getElementsByTagName("klad");
		List<String> lista_nazw_kladow = new ArrayList();
		Element klad;
		for(int i = 0; i < lista_kladow.getLength(); i++) {
			klad = (Element) lista_kladow.item(i);
			if(klad.getAttribute("ranga").equals(ranga)) {
				lista_nazw_kladow.add(klad.getElementsByTagName("knazwa").item(0).getTextContent());
			}
		}
		return lista_nazw_kladow;
	}
	
	public List<String> getRankIdList(String ranga){
		NodeList lista_kladow = klady.getElementsByTagName("klad");
		List<String> lista_id_kladow = new ArrayList();
		Element klad;
		for(int i = 0; i < lista_kladow.getLength(); i++) {
			klad = (Element) lista_kladow.item(i);
			if(klad.getAttribute("ranga").equals(ranga)) {
				lista_id_kladow.add(klad.getAttribute("id"));
			}
		}
		return lista_id_kladow;
	}
	
	public String getRankedNameId(String ranga_id, String nazwa) {
		List<String> lista_id_kladow = getRankIdList(ranga_id);
		List<String> lista_nazw_kladow = getRankNameList(ranga_id);
		String rodzaj_id;
		for(int i = 0; i < lista_nazw_kladow.size(); i++) {
			if(lista_nazw_kladow.get(i).equals(nazwa)) {
				rodzaj_id = lista_id_kladow.get(i);
				return rodzaj_id;
			}
		}
		return null;
	}
	
}
