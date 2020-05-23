package projektXML;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Rangi {
	private Element rangi;
	
	Rangi(Element rangi_p){
		rangi = rangi_p;
	}
	
	public String getNameId(String nazwa) {
		NodeList lista_rang = rangi.getElementsByTagName("ranga");
		if (lista_rang.getLength() == 0) {
			return "!no_rank";
		}
		
		Element ranga;
		for(int i = 0; i < lista_rang.getLength(); i++) {
			ranga = (Element) lista_rang.item(i);
			if(ranga.getTextContent().equals(nazwa)) {
				return ranga.getAttribute("id");
			}
		}
		return null;
	}
}
