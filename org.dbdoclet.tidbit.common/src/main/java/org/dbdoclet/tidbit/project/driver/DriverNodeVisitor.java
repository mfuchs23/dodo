package org.dbdoclet.tidbit.project.driver;

import org.dbdoclet.xiphias.dom.AbstractNodeVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DriverNodeVisitor extends AbstractNodeVisitor {

	@Override
	public void accept(Node node) {

		if (node == null) {
			return;
		}

		Document doc = node.getOwnerDocument();

		if (node.getNodeType() == Node.ELEMENT_NODE) {

			Element elem = (Element) node;

			if (elem.getTagName().equals("xsl:text")) {

				String value = elem.getTextContent();

				if (value != null) {

					if (value.indexOf("\n") != -1) {

						NodeList childList = elem.getChildNodes();

						for (int i = 0; i < childList.getLength(); i++) {
							elem.removeChild(childList.item(i));
						}

						elem.setTextContent(null);
						
						if (value.equals("\n")) {
							elem.appendChild(doc
									.createEntityReference("linefeed"));
						} else {

							String[] parts = value.split("\n");
							for (int i = 0; i < parts.length; i++) {

								elem.appendChild(doc.createTextNode(parts[i]));

								if (i < parts.length - 1) {
									elem.appendChild(doc
											.createEntityReference("linefeed"));
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void openTag(Node node) throws Exception {
		// Auto-generated method stub
		
	}

	@Override
	public void closeTag(Node node) throws Exception {
		// Auto-generated method stub
		
	}

}
