package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Supplier;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Facilitates parsing XML data.
 *
 * @author Alexander Schmidt
 */
public class XmlParser {

	private final ByteBuffer xmlBuffer;
	private ByteBuffer schemaBuffer;

	/**
	 * Constructs a new {@link XmlParser}.
	 *
	 * @param xmlInputStreamSupplier
	 *            a {@link Supplier} of an {@link InputStream} that contains the
	 *            XML data to parse (never <i>null</i>)
	 */
	public XmlParser(Supplier<InputStream> xmlInputStreamSupplier) {

		this(new ByteBuffer(xmlInputStreamSupplier));
	}

	/**
	 * Constructs a new {@link XmlParser}.
	 *
	 * @param xmlBuffer
	 *            a {@link ByteBuffer} that contains the XML data to parse
	 *            (never <i>null</i>)
	 */
	public XmlParser(ByteBuffer xmlBuffer) {

		this.xmlBuffer = Objects.requireNonNull(xmlBuffer);
		this.schemaBuffer = null;
	}

	/**
	 * Defines the XSD (XML Schema Definition) to use for validation.
	 *
	 * @param schemaInputStreamSupplier
	 *            a {@link Supplier} of an {@link InputStream} that contains the
	 *            XSD data to use for validation (may be <i>null</i>)
	 * @return this {@link XmlParser}
	 */
	public XmlParser setSchemaDefinition(Supplier<InputStream> schemaInputStreamSupplier) {

		return setSchemaDefinition(new ByteBuffer(schemaInputStreamSupplier));
	}

	/**
	 * Defines the XSD (XML Schema Definition) to use for validation.
	 *
	 * @param schemaBuffer
	 *            a {@link ByteBuffer} that contains the XSD data to use for
	 *            validation (may be <i>null</i>)
	 * @return this {@link XmlParser}
	 */
	public XmlParser setSchemaDefinition(ByteBuffer schemaBuffer) {

		this.schemaBuffer = schemaBuffer;
		return this;
	}

	/**
	 * Parses the previously-defined XML data into an XML tree, with the
	 * returned {@link XmlNode} as root.
	 * <p>
	 * If a schema definition was specified, the XML data is also validated.
	 *
	 * @return the root {@link XmlNode} of the XML tree (never <i>null</i>)
	 * @see #setSchemaDefinition(ByteBuffer)
	 */
	public XmlNode parse() {

		var xmlReader = new XmlReader(xmlBuffer::getInputStream);

		Optional//
			.ofNullable(schemaBuffer)
			.ifPresent(buffer -> xmlReader.validate(buffer::getInputStream));

		return processNode(//
			xmlReader.getDocumentElement(),
			null,
			0);
	}

	private XmlNode processNode(Node node, XmlNode parentNode, int nodeDepth) {

		String nodeTag = node.getNodeName();
		var attributeMap = createAttributeMap(node.getAttributes());
		String nodeContent = getNodeContent(node);

		XmlNode currentNode = new XmlNode(nodeTag, attributeMap, nodeContent, parentNode, nodeDepth);

		List<Node> childElementNodes = getNodesByType(node.getChildNodes(), Node.ELEMENT_NODE);
		++nodeDepth;

		if (!childElementNodes.isEmpty()) {
			for (Node childElementNode: childElementNodes) {
				XmlNode childNode = processNode(childElementNode, currentNode, nodeDepth);

				currentNode.addChild(childNode);
			}
		}

		return currentNode;
	}

	/**
	 * Gets a {@link List} of nodes from the given {@link NodeList}, according
	 * to the given node type (as defined at the top of {@link Node}).
	 *
	 * @param nodes
	 * @param nodeType
	 * @return The given NodeList represented as List<Node> and filtered by the
	 *         desired type.
	 */
	private List<Node> getNodesByType(NodeList nodes, int nodeType) {

		List<Node> nodeList = new ArrayList<>();

		for (int index = 0; index < nodes.getLength(); index++) {
			Node node = nodes.item(index);

			if (node.getNodeType() == nodeType) {
				nodeList.add(node);
			}
		}

		return nodeList;
	}

	private Map<String, String> createAttributeMap(NamedNodeMap attributes) {

		var map = new TreeMap<String, String>();

		for (int i = 0; i < attributes.getLength(); i++) {
			Node attrNode = attributes.item(i);
			String attrName = attrNode.getNodeName();
			String attrValue = attrNode.getNodeValue();

			map.put(attrName, attrValue);
		}

		return map;
	}

	/**
	 * @param node
	 * @return The trimmed textual content of a node (i.e. the first found text
	 *         between the node's start and end tag, if any). Defaults to an
	 *         empty {@link String}.
	 */
	private String getNodeContent(Node node) {

		NodeList childNodes = node.getChildNodes();

		for (int index = 0; index < childNodes.getLength(); index++) {
			Node child = childNodes.item(index);

			if (child.getNodeType() == Node.TEXT_NODE) {
				return child.getTextContent().trim();
			}
		}

		return "";
	}
}
