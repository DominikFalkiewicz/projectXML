package projektXML;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.ArrayList;

public class Nisze {
	private Element nisze;
	
	Nisze(Element nisze_p){
		nisze = nisze_p;
	}
	
	public List<String> getNameList(){
		NodeList lista_nisz = nisze.getElementsByTagName("nisza");
		List<String> lista_nazw_nisz = new ArrayList();
		Element nisza;
		for(int i = 0; i < lista_nisz.getLength(); i++) {
			nisza = (Element) lista_nisz.item(i);
			lista_nazw_nisz.add(nisza.getElementsByTagName("nnazwa").item(0).getTextContent());
		}
		return lista_nazw_nisz;
	}
	public List<String> getIdList(){
		NodeList lista_nisz = nisze.getElementsByTagName("nisza");
		List<String> lista_id_nisz = new ArrayList();
		Element nisza;
		for(int i = 0; i < lista_nisz.getLength(); i++) {
			nisza = (Element) lista_nisz.item(i);
			lista_id_nisz.add(nisza.getAttribute("id"));
		}
		return lista_id_nisz;
	}
	public String getNameId(String nazwa) {
		List<String> lista_id_kladow = getIdList();
		List<String> lista_nazw_kladow = getNameList();
		String nisza_id;
		for(int i = 0; i < lista_nazw_kladow.size(); i++) {
			if(lista_nazw_kladow.get(i).equals(nazwa)) {
				nisza_id = lista_id_kladow.get(i);
				return nisza_id;
			}
		}
		return null;
	}
}
