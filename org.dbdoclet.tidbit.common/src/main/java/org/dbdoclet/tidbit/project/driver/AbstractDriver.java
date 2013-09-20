package org.dbdoclet.tidbit.project.driver;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.fo.FoAttributeSet;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.xiphias.NodeSerializer;
import org.dbdoclet.xiphias.W3cServices;
import org.dbdoclet.xiphias.XPathServices;
import org.dbdoclet.xiphias.dom.DOMTraverser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractDriver {

	private static Log logger = LogFactory.getLog(AbstractDriver.class);

	private File docBookXslDir;
	private final Document[] driverDocs;
	private final File driverFile;

	protected File homeDir;

	public AbstractDriver(File homeDir, File driverFile, File paramFile)
			throws ParserConfigurationException, SAXException, IOException {

		if (homeDir == null) {
			throw new IllegalArgumentException(
					"The parameter home must not be null!");
		}

		if (driverFile == null) {
			throw new IllegalArgumentException(
					"The argument xslt must not be null!");
		}

		logger.debug("XSLT Datei = " + driverFile.getAbsolutePath());
		this.homeDir = homeDir;
		this.driverFile = driverFile;

		driverDocs = new Document[2];

		if (driverFile.exists() == false) {
			throw new FileNotFoundException(driverFile.getAbsolutePath());
		}

		if (paramFile != null && paramFile.exists() == false) {
			throw new FileNotFoundException(paramFile.getAbsolutePath());
		}

		if (driverFile.exists()) {
			driverDocs[0] = load(driverFile);
		}

		if (paramFile != null && paramFile.exists()) {
			driverDocs[1] = load(paramFile);
			docBookXslDir = paramFile.getParentFile();
		}
	}

	public static int computeFontSize(String fontSize) {

		int size = 12;

		if (fontSize == null || fontSize.trim().length() == 0) {
			return size;
		}

		if (fontSize.endsWith("pt")) {
			fontSize = StringServices.cutSuffix(fontSize, "pt");
		}

		try {
			size = Integer.parseInt(fontSize);
		} catch (Throwable oops) {
			System.err.println(oops.getMessage());
		}

		return size;
	}

	public static String getFoFontFamily(String family) {

		if (family == null) {
			return "serif";
		}

		String cmp = family.toLowerCase();

		if (cmp.equals("dialog") || cmp.equals("sansserif")) {
			return "sans-serif";
		}

		if (cmp.equals("serif")) {
			return "serif";
		}

		if (cmp.startsWith("monospace")) {
			return "monospace";
		}

		return family;
	}

	public static String getJavaFontFamily(String family) {

		if (family == null) {
			return null;
		}

		String cmp = family.toLowerCase();

		if (cmp.equals("sans-serif") || cmp.equals("sansserif")) {
			return "SansSerif";
		}

		if (cmp.equals("serif")) {
			return "Serif";
		}

		if (cmp.startsWith("monospace")) {
			return "Monospace";
		}

		return family;
	}

	public boolean attributeSetIsCustomized(String attributeSetName) {

		if (driverDocs[0] == null) {
			return false;
		}

		Element properties = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:attribute-set[@name='" + attributeSetName + "']");
		return properties != null ? true : false;
	}

	public String getAttribute(String name, String key) {

		if (driverDocs[0] == null) {
			return null;
		}

		String value;

		Element properties = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:attribute-set[@name='" + name + "']");

		if (properties != null) {

			value = (String) XPathServices.getValue(properties,
					"//xsl:attribute[@name='" + key + "']");

			if (value != null) {
				return value;
			}
		}

		return null;
	}

	public Element getAttributeSet(String name) {

		if (driverDocs[0] == null) {
			return null;
		}

		return (Element) XPathServices.getNode(driverDocs[0],
				"/xsl:stylesheet/xsl:attribute-set[@name='" + name + "']");
	}

	public Color getColorAttribute(String key) {
		return getColorAttribute(key, "color");
	}

	public Color getColorAttribute(String key, String name) {

		String sb = getAttribute(key, name);

		if (sb == null) {
			return null;
		}

		return toColor(sb);
	}

	public Color getColorParameter(String param) {
		return toColor(getParameter(param));
	}

	public Font getFontAttributes(String key) {

		String fontFamily = getJavaFontFamily(getAttribute(key, "font-family"));
		String fontWeight = getAttribute(key, "font-weight");
		String fontStyle = getAttribute(key, "font-style");
		String fontSize = getAttribute(key, "font-size");

		Font font = null;
		int style = Font.PLAIN;
		int size = 12;

		if (fontFamily != null) {

			if (fontWeight != null && fontWeight.equals("bold")) {
				style |= Font.BOLD;
			}

			if (fontStyle != null && fontStyle.equals("italic")) {
				style |= Font.ITALIC;
			}

			if (fontSize != null) {
				size = computeFontSize(fontSize);
			}

			font = new Font(fontFamily, style, size);
		}

		return font;
	}

	public ArrayList<Node> getIncludes() {

		if (driverDocs[0] == null) {
			return null;
		}

		return XPathServices.getNodes(driverDocs[0],
				"/xsl:stylesheet/xsl:import | /xsl:stylesheet/xsl:include");
	}

	public String getMediumName() {

		if (driverFile == null) {
			return "unknown";
		}

		return FileServices.getFileBase(driverFile);
	}

	public int getNumberParameter(String name, int defValue) {

		String value = getParameter(name);

		if (value == null) {
			return defValue;
		}

		try {
			return Integer.parseInt(value);
		} catch (Throwable oops) {
			return defValue;
		}
	}

	public String getParameter(String key) {
		return getParameter(key, null);
	}

	public String getParameter(String key, String def) {

		if (key == null) {
			throw new IllegalArgumentException(
					"The argument key must not be null!");
		}

		if (driverDocs[0] == null) {
			return null;
		}

		Element elem = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:param[@name='" + key + "']");

		if (elem != null) {

			String value = elem.getAttribute("select");
			if (value == null || value.length() == 0) {
				value = elem.getTextContent();
			}

			return value;
		}

		elem = (Element) XPathServices.getNode(driverDocs[1],
				"//xsl:param[@name='" + key + "']");

		if (elem != null) {

			if (isContainer(elem)) {
				return def;
			}

			String value = elem.getAttribute("select");
			if (value == null || value.length() == 0) {
				value = elem.getTextContent();
			}

			return value;
		}

		return def;
	}

	public Element getTemplate(String name) {

		if (driverDocs[0] == null) {
			return null;
		}

		return (Element) XPathServices.getNode(driverDocs[0],
				"/xsl:stylesheet/xsl:template[@name='" + name + "']");
	}

	/**
	 * Sucht im Customization Layer und in den DocBook-XSL Dateien nach einem
	 * benannten XSL-Template.
	 * 
	 * @param name
	 * @return String
	 */
	public String getTemplateAsText(String name) {

		if (driverDocs[0] == null) {
			return null;
		}

		Element templateElement = (Element) XPathServices.getNode(
				driverDocs[0], "//xsl:template[@name='" + name + "']");

		if (templateElement == null && docBookXslDir != null) {

			for (File xslFile : docBookXslDir.listFiles()) {

				try {

					if (xslFile.getName().toLowerCase().endsWith(".xsl") == false) {
						continue;
					}

					Document doc = load(xslFile);

					templateElement = (Element) XPathServices.getNode(doc,
							"//xsl:template[@name='" + name + "']");

					if (templateElement != null) {
						break;
					}

				} catch (Throwable oops) {
					logger.fatal(oops);
				}
			}
		}

		if (templateElement != null) {

			try {

				StringWriter buffer = new StringWriter();
				NodeSerializer serializer = new NodeSerializer();
				serializer.write(templateElement, buffer);
				return buffer.toString();

			} catch (IOException oops) {
				logger.fatal(oops);
			}
		}

		return null;
	}

	public boolean isParameterEnabled(String key, boolean def) {

		if (key == null) {
			throw new IllegalArgumentException(
					"The argument key must not be null!");
		}

		String text = getParameter(key, null);

		if (text == null) {
			return def;
		}

		if (text.equals("1") || text.equalsIgnoreCase("yes")
				|| text.equalsIgnoreCase("true") || text.equalsIgnoreCase("ja")
				|| text.equalsIgnoreCase("wahr")) {
			return true;
		}

		return false;
	}

	public void merge(AbstractDriver otherDriver) {

		Document doc = driverDocs[0];
		Element stylesheet = doc.getDocumentElement();

		for (Node include : getIncludes()) {
			removeElement((Element) include);
		}

		Element refChild = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:param[position()=1]");

		for (Node include : otherDriver.getIncludes()) {

			Node imported = doc.importNode(include, false);
			stylesheet.insertBefore(imported, refChild);
		}

		for (String param : otherDriver.getParameterNames()) {
			setParameter(param, otherDriver.getParameter(param));
		}

		for (String attributeSet : otherDriver.getAttributeSetNames()) {
			setAttributeSet(otherDriver.getAttributeSet(attributeSet));
		}

		for (String template : getTemplateNames()) {
			removeTemplate(template);
		}

		for (String template : otherDriver.getTemplateNames()) {
			setTemplate(otherDriver.getTemplate(template));
		}
	}

	public void removeAttributeSet(String attributeSetName) {

		Element aset = getAttributeSet(attributeSetName);

		if (aset != null) {
			removeElement(aset);
		}
	}

	public void removeFontAttributes(String name) {

		Element aset = getAttributeSet(name);

		if (aset == null) {
			return;
		}

		removeAttribute(aset, "font-family");
		removeAttribute(aset, "font-weight");
		removeAttribute(aset, "font-style");
		removeAttribute(aset, "font-size");
		removeAttribute(aset, "color");
		removeAttribute(aset, "background-color");
	}

	public void save(File xslt) throws Exception {

		if (xslt == null) {
			throw new IllegalArgumentException(
					"The argument xslt must not be null!");
		}

		if (driverDocs[0] == null) {
			return;
		}

		logger.debug("XSLT Datei = " + xslt.getAbsolutePath());

		Element root = driverDocs[0].getDocumentElement();
		validate(root);

		FileServices.createPath(xslt.getParentFile());
		NodeSerializer serializer = new NodeSerializer();
		serializer.setEncoding("UTF-8");
		serializer.write(root, xslt);
	}

	public void setAttribute(String name, String key, String value) {

		if (name == null || key == null) {
			return;
		}

		Element properties = findAttributeSet(name);
		setAttribute(properties, key, value);
	}

	public void setColorAttribute(String attributeSetName, String attribute,
			Color color) {

		if (attributeSetName == null || color == null) {
			return;
		}

		Element properties = findAttributeSet(attributeSetName);

		String s = toRGB(color);
		setAttribute(properties, attribute, s);
	}

	public void setColorParameter(String param, Color color) {
		setParameter(param, toRGB(color));
	}

	public void setFontAttributes(String name, Font font, Color foreground,
			Color background) {

		if (font == null) {
			return;
		}

		Element properties = findAttributeSet(name);

		setAttribute(properties, "font-family",
				getFoFontFamily(font.getFamily()));
		setAttribute(properties, "font-weight", getFontWeight(font.getStyle()));
		setAttribute(properties, "font-style", getFontStyle(font.getStyle()));
		setAttribute(properties, "font-size", String.valueOf(font.getSize())
				+ "pt");

		setColorAttribute(name, "color", foreground);
		setColorAttribute(name, "background-color", background);
	}

	public void setParameter(String key, Object value) {

		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException(
					"The argument key must not be null!");
		}

		if (value == null) {
			removeParameter(key);
			return;
		}

		String vb = value.toString();

		if (value instanceof Boolean) {
			vb = ((Boolean) value).booleanValue() ? "1" : "0";
		}

		Element elem = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:param[@name='" + key + "']");

		if (elem != null) {

			elem.removeAttribute("select");
			elem.setTextContent(vb);

		} else {

			Element refChild = (Element) XPathServices.getNode(driverDocs[0],
					"//xsl:param[position()=1]");
			Element parent = null;

			if (refChild != null) {
				parent = (Element) refChild.getParentNode();
			}

			Element child = driverDocs[0].createElement("xsl:param");
			child.setAttribute("name", key);
			child.setTextContent(vb);

			if (parent != null) {

				parent.insertBefore(child, refChild);

			} else {

				parent = (Element) XPathServices.getNode(driverDocs[0],
						"/xsl:stylesheet");
				parent.appendChild(child);
			}
		}
	}

	/**
	 * Fügt ein benanntes XSL-Template in die Treiberdatei ein. Das Template
	 * wird als Zeichenkette übergeben.
	 * 
	 * @param name
	 * @param buffer
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void setTemplate(String name, String buffer)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(buffer));

		Document doc = parser.parse(is);

		Element templateElement = (Element) XPathServices.getNode(
				driverDocs[0], "//xsl:template[@name='" + name + "']");

		if (templateElement != null) {
			removeElement(templateElement);
		}

		templateElement = (Element) W3cServices.copyNode(driverDocs[0],
				doc.getDocumentElement());
		Element root = driverDocs[0].getDocumentElement();

		root.appendChild(templateElement);
	}

	private ArrayList<String> getAttributeSetNames() {

		ArrayList<String> nameList = new ArrayList<String>();

		ArrayList<Node> nodeList = XPathServices.getNodes(driverDocs[0],
				"/child::xsl:stylesheet/xsl:attribute-set");

		if (nodeList != null) {

			for (Node node : nodeList) {
				Element element = (Element) node;
				nameList.add(element.getAttribute("name"));
			}
		}
		return nameList;
	}

	private String getFontStyle(int style) {

		if ((style & Font.ITALIC) == Font.ITALIC) {
			return "italic";
		} else {
			return "normal";
		}
	}

	private String getFontWeight(int style) {

		if ((style & Font.BOLD) == Font.BOLD) {
			return "bold";
		} else {
			return "normal";
		}
	}

	private ArrayList<String> getParameterNames() {

		ArrayList<String> nameList = new ArrayList<String>();

		ArrayList<Node> nodeList = XPathServices.getNodes(driverDocs[0],
				"/child::xsl:stylesheet/xsl:param");

		if (nodeList != null) {

			for (Node node : nodeList) {
				Element element = (Element) node;
				nameList.add(element.getAttribute("name"));
			}
		}
		return nameList;
	}

	private Element findAttributeSet(String name) {

		Element aset = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:attribute-set[@name='" + name + "']");

		if (aset == null) {

			Element parent = (Element) XPathServices.getNode(driverDocs[0],
					"/xsl:stylesheet");

			aset = driverDocs[0].createElement("xsl:attribute-set");
			aset.setAttribute("name", name);

			if (parent != null) {
				parent.appendChild(aset);
			}
		}

		return aset;
	}

	private ArrayList<String> getTemplateNames() {

		ArrayList<String> nameList = new ArrayList<String>();

		ArrayList<Node> nodeList = XPathServices.getNodes(driverDocs[0],
				"/child::xsl:stylesheet/xsl:template");

		if (nodeList != null) {

			for (Node node : nodeList) {
				Element element = (Element) node;
				nameList.add(element.getAttribute("name"));
			}
		}
		return nameList;
	}

	private boolean isContainer(Element parent) {

		NodeList children = parent.getChildNodes();

		if (children == null) {
			return false;
		}

		for (int i = 0; i < children.getLength(); i++) {

			Node child = children.item(i);

			if (child instanceof Element) {
				return true;
			}
		}

		return false;
	}

	private void removeAttribute(Element aset, String key) {

		if (aset == null) {
			return;
		}

		Element param = (Element) XPathServices.getNode(aset,
				"//xsl:attribute[@name='" + key + "']");

		if (param != null) {
			removeElement(param);
		}
	}

	private void removeElement(Element elem) {

		if (elem != null) {

			Node parent = elem.getParentNode();
			Node sibling = elem.getNextSibling();

			while (sibling != null && sibling instanceof Text) {

				String text = ((Text) sibling).getTextContent();

				if (text != null && text.trim().length() == 0) {
					parent.removeChild(sibling);
				} else {
					break;
				}

				sibling = elem.getNextSibling();
			}

			parent.removeChild(elem);
		}
	}

	private void removeParameter(String key) {

		Element param = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:param[@name='" + key + "']");

		if (param != null) {
			removeElement(param);
		}
	}

	private void removeTemplate(String key) {

		Element param = (Element) XPathServices.getNode(driverDocs[0],
				"//xsl:template[@name='" + key + "']");

		if (param != null) {
			removeElement(param);
		}
	}

	private String replaceInstallationDirectory(String value) {

		if (value.contains("@dbdoclet_home@")) {

			value = StringServices.replace(value, "@dbdoclet_home@",
					homeDir.getPath());

			File file = new File(value);

			if (file.exists() == true) {

				try {
					value = file.toURI().toURL().toString();
				} catch (MalformedURLException oops) {
					logger.error(oops);
				}
			}
		}

		return value;
	}

	private void setAttribute(Element properties, String key, String value) {

		Element attribute = (Element) XPathServices.getNode(properties,
				"//xsl:attribute[@name='" + key + "']");

		if (value == null) {

			if (attribute != null) {
				properties.removeChild(attribute);
			}

			return;
		}

		if (attribute != null) {

			attribute.setTextContent(value);

		} else {

			attribute = driverDocs[0].createElement("xsl:attribute");
			attribute.setAttribute("name", key);
			attribute.setTextContent(value);
			properties.appendChild(attribute);
		}
	}

	private void setAttributeSet(Element attributeSet) {

		Document doc = driverDocs[0];

		Element asetNew = (Element) doc.importNode(attributeSet, true);
		Element asetOld = getAttributeSet(asetNew.getAttribute("name"));

		Element stylesheet = doc.getDocumentElement();

		if (asetOld != null) {
			stylesheet.insertBefore(asetNew, asetOld);
			stylesheet.removeChild(asetOld);
		} else {

			Element refChild = (Element) XPathServices.getNode(doc,
					"/xsl:stylesheet/xsl:attribute-set[position()=1]");

			if (refChild == null) {
				stylesheet.appendChild(asetNew);
			} else {
				stylesheet.insertBefore(asetNew, refChild);
			}
		}
	}

	private void setTemplate(Element template) {

		Document doc = driverDocs[0];

		Element templateNew = (Element) doc.importNode(template, true);
		Element templateOld = getTemplate(templateNew.getAttribute("name"));

		Element stylesheet = doc.getDocumentElement();

		if (templateOld != null) {
			stylesheet.insertBefore(templateNew, templateOld);
			stylesheet.removeChild(templateOld);
		} else {
			Element refChild = (Element) XPathServices.getNode(doc,
					"/xsl:stylesheet/xsl:template[position()=1]");

			if (refChild == null) {
				stylesheet.appendChild(templateNew);
			} else {
				stylesheet.insertBefore(templateNew, refChild);
			}
		}
	}

	private Color toColor(String value) {

		if (value != null && value.trim().startsWith("#")
				&& value.length() == 7) {

			int red = Integer.parseInt(value.substring(1, 3), 16);
			int green = Integer.parseInt(value.substring(3, 5), 16);
			int blue = Integer.parseInt(value.substring(5, 7), 16);

			return new Color(red, green, blue);
		}

		return Color.black;
	}

	private String toRGB(Color color) {

		if (color == null) {
			return null;
		}

		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		String s = String.format("#%02x%02x%02x", red, green, blue);
		return s;
	}

	private void validate(Element root) throws Exception {

		Element refChild;
		ArrayList<Node> nodeList;

		nodeList = XPathServices.getNodes(root,
				"text() | xsl:attribute-set/text() | xsl:template/text()");

		for (Node node : nodeList) {
			node.getParentNode().removeChild(node);
		}

		refChild = (Element) XPathServices.getNode(root,
				"xsl:param[position()=1]");

		root.insertBefore(driverDocs[0].createTextNode("\n"), refChild);

		nodeList = XPathServices.getNodes(root, "xsl:param");
		Collections.sort(nodeList, new ParamComparator());

		for (Node node : nodeList) {
			root.removeChild(node);
		}

		refChild = (Element) XPathServices.getNode(root,
				"xsl:attribute-set[position()=1]");

		if (refChild == null) {
			refChild = (Element) XPathServices.getNode(root,
					"xsl:template[position()=1]");
		}

		for (Node node : nodeList) {

			if (node instanceof Element) {

				Element elem = (Element) node;
				String param = "";

				NamedNodeMap attrMap = elem.getAttributes();

				for (int i = 0; i < attrMap.getLength(); i++) {

					Attr attr = (Attr) attrMap.item(i);

					String name = attr.getName();

					if (name != null && name.equals("name")) {
						param = attr.getValue();
					}
				}

				if (param != null && param.equals("line-height")) {

					String value = elem.getTextContent();

					// Entfernen eines ungültigen line-height Parameters.
					if (value == null || value.trim().length() == 0) {
						elem.setTextContent("1.2");
					}
				}

				String value = elem.getTextContent();
				value = replaceInstallationDirectory(value);

				if (param != null && param.endsWith(".path")) {
					value = validatePathParameter(value);
				}

				elem.setTextContent(value);
			}

			if (refChild == null) {
				root.appendChild(node);
			} else {
				root.insertBefore(node, refChild);
			}
		}

		DOMTraverser traverser = new DOMTraverser(new DriverNodeVisitor());
		traverser.traverse(root);
	}

	protected Document load(File xslt) throws ParserConfigurationException,
			SAXException, IOException {

		if (xslt == null) {
			throw new IllegalArgumentException(
					"The argument xslt must not be null!");
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setExpandEntityReferences(false);
		DocumentBuilder parser = factory.newDocumentBuilder();

		Document doc = parser.parse(xslt);
		return doc;
	}

	/**
	 * Prüft die angegebene Zeichenkette auf Gültigkeit bezüglich eines
	 * Pfad-Parameters. Parameter die Pfade repräsentieren werden üblicherweise
	 * als URIs angegeben. Ist die angegebene Zeichenkette kein gültiger URL,
	 * wird geprüft sie eine absolute Pfadangabe enthält und in diesem Fall in
	 * einen URI umgewandelt. Alle anderen Zeichenketten werden unverändert
	 * durchgereicht.
	 * 
	 * @param text
	 * @return
	 */
	protected String validatePathParameter(String text) {

		if (text == null || text.length() == 0) {
			return text;
		}

		try {

			URL url = new URL(text);
			text = url.toString();

		} catch (MalformedURLException oops) {

			if (FileServices.isAbsolutePath(text)) {
				text = new File(text).toURI().toString();
			}
		}

		return text;
	}

	public void setSpacingAttributes(String attributeSetName,
			FoAttributeSet aset) {

		setAttribute(attributeSetName, "space-before.minimum",
				aset.getSpaceBeforeMinimum());
		setAttribute(attributeSetName, "space-before.optimum",
				aset.getSpaceBeforeOptimum());
		setAttribute(attributeSetName, "space-before.maximum",
				aset.getSpaceBeforeMaximum());
		setAttribute(attributeSetName, "space-after.minimum",
				aset.getSpaceAfterMinimum());
		setAttribute(attributeSetName, "space-after.optimum",
				aset.getSpaceAfterOptimum());
		setAttribute(attributeSetName, "space-after.maximum",
				aset.getSpaceAfterMaximum());
		setAttribute(attributeSetName, "padding", aset.getPadding());
		setAttribute(attributeSetName, "text-indent", aset.getTextIndent());
		setAttribute(attributeSetName, "start-indent", aset.getStartIndent());
		setAttribute(attributeSetName, "end-indent", aset.getEndIndent());
	}

	public void removeSpacingAttributes(String attributeSetName) {

		Element aset = getAttributeSet(attributeSetName);

		if (aset == null) {
			return;
		}

		removeAttribute(aset, "space-before.minimum");
		removeAttribute(aset, "space-before.optimum");
		removeAttribute(aset, "space-before.maximum");
		removeAttribute(aset, "space-after.minimum");
		removeAttribute(aset, "space-after.optimum");
		removeAttribute(aset, "space-after.maximum");
		removeAttribute(aset, "padding");
		removeAttribute(aset, "text-indent");
		removeAttribute(aset, "start-indent");
		removeAttribute(aset, "end-indent");
	}

	public void setFrameAttributes(String attributeSetName, String pos,
			FoAttributeSet aset) {

		setAttribute(attributeSetName, "border-" + pos + "-style",
				aset.getFrameStyle());
		setAttribute(attributeSetName, "border-" + pos + "-width",
				aset.getFrameWidth());
		setColorAttribute(attributeSetName, "border-" + pos + "-color",
				aset.getFrameColor());
	}

	public void removeFrameAttributes(String attributeSetName, String pos) {

		Element aset = getAttributeSet(attributeSetName);

		removeAttribute(aset, "border-" + pos + "-style");
		removeAttribute(aset, "border-" + pos + "-width");
		removeAttribute(aset, "border-" + pos + "-color");
	}

	public void setLineAttributes(String attributeSetName, FoAttributeSet aset) {
		setAttribute(attributeSetName, "wrap-option", aset.getWrapOption());
		setAttribute(attributeSetName, "text-align", aset.getTextAlign());
		setAttribute(attributeSetName, "width", aset.getFoWidth());
		setAttribute(attributeSetName, "line-height", aset.getLineHeight());
	}

	public void removeLineAttributes(String attributeSetName) {

		Element aset = getAttributeSet(attributeSetName);

		if (aset == null) {
			return;
		}

		removeAttribute(aset, "wrap-option");
		removeAttribute(aset, "text-align");
		removeAttribute(aset, "width");
		removeAttribute(aset, "line-height");
	}

	public boolean getBooleanAttribute(String attributeSetName, String attr,
			boolean def) {

		String value = getAttribute(attributeSetName, attr);

		if (value != null) {

			if (value.equals("1")) {
				value = "true";
			}

			return Boolean.valueOf(value);
		}

		return def;
	}
}
