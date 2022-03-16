package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;

public class XmlNodeTest extends AbstractXmlParserTest {

	private final XmlNode root;

	public XmlNodeTest() {

		this.root = new XmlParser(new ByteBuffer(XML_BYTES)).parse();
	}

	@Test
	public void testGetTag() {

		String tag = root.getTag();
		assertEquals("shiporder", tag);
	}

	@Test
	public void testGetAttributes() {

		var attributes = root.getAttributes();
		assertEquals(3, attributes.size());
		assertEquals("889923", attributes.get("orderid"));
		assertEquals("http://www.w3.org/2001/XMLSchema-instance", attributes.get("xmlns:xsi"));
		assertEquals("shiporder.xsd", attributes.get("xsi:noNamespaceSchemaLocation"));
	}

	@Test
	public void testGetTextContent() {

		String textContent = root.getTextContent();
		assertEquals("", textContent);
	}

	@Test
	public void testGetParent() {

		XmlNode parent = root.getParent();
		assertNull(parent);
	}

	@Test
	public void testGetDepth() {

		int depth = root.getDepth();
		assertEquals(0, depth);
	}

	@Test
	public void testGetChildren() {

		List<XmlNode> children = root.getChildren();
		assertEquals(4, children.size());
		assertEquals("orderperson", children.get(0).getTag());
		assertEquals("shipto", children.get(1).getTag());
		assertEquals("item", children.get(2).getTag());
		assertEquals("item", children.get(3).getTag());
	}

	@Test
	public void testGetChildCount() {

		int childCount = root.getChildCount();
		assertEquals(4, childCount);
	}

	@Test
	public void testGetChildrenWithTagByString() {

		List<XmlNode> childrenWithShiptoTag = root.getChildrenWithTag("shipto");
		List<XmlNode> childrenWithItemTag = root.getChildrenWithTag("item");

		assertEquals(1, childrenWithShiptoTag.size());
		assertEquals(2, childrenWithItemTag.size());
	}

	@Test
	public void testGetChildrenWithTagByIXmlTag() {

		List<XmlNode> childrenWithShiptoTag = root.getChildrenWithTag(() -> "shipto");
		List<XmlNode> childrenWithItemTag = root.getChildrenWithTag(() -> "item");

		assertEquals(1, childrenWithShiptoTag.size());
		assertEquals(2, childrenWithItemTag.size());
	}

	@Test
	public void testGetNodeByString() {

		XmlNode node = root.getNode("shipto", "country").get();

		assertEquals("Norway", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeByStringWithIndex() {

		XmlNode node = root.getNode("item:1", "title").get();
		assertEquals("Hide your heart", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeByStringWithAmbiguousPath() {

		XmlNode node = root.getNode("item", "title").get();
		assertEquals("Empire Burlesque", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeByStringWithNonexistentPath() {

		Optional<XmlNode> node = root.getNode("shipto", "nonexistent");
		assertTrue(node.isEmpty());
	}

	@Test
	public void testGetNodeByStringWithEmptyPath() {

		Optional<XmlNode> node = root.getNode(new String[0]);
		assertTrue(node.isEmpty());
	}

	@Test
	public void testGetNodeByTag() {

		XmlNode node = root.getNode(() -> "shipto", () -> "country").get();

		assertEquals("Norway", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeByTagWithIndex() {

		XmlNode node = root
			.getNode(//
				XmlTag.create("item", 1),
				XmlTag.create("title"))
			.get();

		assertEquals("Hide your heart", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeOrThrowByString() {

		XmlNode node = root.getNodeOrThrow("shipto", "country");

		assertEquals("Norway", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeOrThrowByStringWithIndex() {

		XmlNode node = root.getNodeOrThrow("item:1", "title");
		assertEquals("Hide your heart", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeOrThrowByStringWithAmbiguousPath() {

		XmlNode node = root.getNodeOrThrow("item", "title");
		assertEquals("Empire Burlesque", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test(expected = XmlParserNodeNotFoundException.class)
	public void testGetNodeOrThrowByStringWithNonexistentPath() {

		root.getNodeOrThrow("shipto", "nonexistent");
	}

	@Test(expected = XmlParserNodeNotFoundException.class)
	public void testGetNodeOrThrowByStringWithEmptyPath() {

		root.getNodeOrThrow(new String[0]);
	}

	@Test
	public void testGetNodeOrThrowByTag() {

		XmlNode node = root.getNodeOrThrow(() -> "shipto", () -> "country");

		assertEquals("Norway", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetNodeOrThrowByTagWithIndex() {

		XmlNode node = root
			.getNodeOrThrow(//
				XmlTag.create("item", 1),
				XmlTag.create("title"));

		assertEquals("Hide your heart", node.getTextContent());
		assertEquals(2, node.getDepth());
	}

	@Test
	public void testGetAllTags() {

		Set<String> allTags = root.getAllTags();
		assertEquals(12, allTags.size());
		assertTrue(allTags.contains("address"));
		assertTrue(allTags.contains("city"));
		assertTrue(allTags.contains("country"));
		assertTrue(allTags.contains("item"));
		assertTrue(allTags.contains("name"));
		assertTrue(allTags.contains("note"));
		assertTrue(allTags.contains("orderperson"));
		assertTrue(allTags.contains("price"));
		assertTrue(allTags.contains("quantity"));
		assertTrue(allTags.contains("shiporder"));
		assertTrue(allTags.contains("shipto"));
		assertTrue(allTags.contains("title"));
	}
}
