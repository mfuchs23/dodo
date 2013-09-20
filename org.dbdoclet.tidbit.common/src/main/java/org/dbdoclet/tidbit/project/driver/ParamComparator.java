package org.dbdoclet.tidbit.project.driver;

import java.util.Comparator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ParamComparator implements Comparator<Node> {

	public int compare(Node node1, Node node2) {
		
		Element param1 = (Element) node1;
		Element param2 = (Element) node2;
		String name1 = param1.getAttribute("name");
		String name2 = param2.getAttribute("name");
		return name1.compareTo(name2);
	}
}
