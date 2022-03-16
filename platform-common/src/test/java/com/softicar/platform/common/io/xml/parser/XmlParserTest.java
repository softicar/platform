package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import org.junit.Test;

public class XmlParserTest extends AbstractXmlParserTest {

	// -------------------------------- parse -------------------------------- //

	@Test
	public void testParseWithoutSchemaDefinitionAfterByteBufferConstructor() {

		XmlParser parser = new XmlParser(new ByteBuffer(XML_BYTES));
		XmlNode node = parser.parse();
		assertEquals("shiporder", node.getTag());
	}

	@Test
	public void testParseWithoutSchemaDefinitionAfterInputStreamSupplierConstructor() {

		XmlParser parser = new XmlParser(() -> new ByteArrayInputStream(XML_BYTES));
		XmlNode node = parser.parse();
		assertEquals("shiporder", node.getTag());
	}

	@Test
	public void testParseWithSchemaDefinition() {

		XmlParser parser = new XmlParser(new ByteBuffer(XML_BYTES));
		parser.setSchemaDefinition(new ByteBuffer(XSD_BYTES));
		XmlNode node = parser.parse();
		assertEquals("shiporder", node.getTag());
	}

	@Test(expected = XmlParserException.class)
	public void testParseWithSchemaDefinitionViolation() {

		String offendingXml = XML.replaceAll("country", "invalid-garbage");
		XmlParser parser = new XmlParser(new ByteBuffer(offendingXml.getBytes(StandardCharsets.UTF_8)));
		parser.setSchemaDefinition(new ByteBuffer(XSD_BYTES));
		XmlNode node = parser.parse();
		assertEquals("shiporder", node.getTag());
	}

	// -------------------------------- parse: edge cases -------------------------------- //

	@Test(expected = NullPointerException.class)
	public void testParseAfterInputStreamSupplierConstructorWithNull() {

		DevNull.swallow(new XmlParser(() -> null));
	}

	@Test(expected = XmlParserException.class)
	public void testParseWithMalformattedXml() {

		String malformattedXml = XML.substring(1, XML.length() - 1);
		XmlParser parser = new XmlParser(new ByteBuffer(malformattedXml.getBytes(StandardCharsets.UTF_8)));
		XmlNode node = parser.parse();
		assertEquals("root", node.getTag());
	}

	@Test(expected = XmlParserException.class)
	public void testParseWithEmptyXml() {

		XmlParser parser = new XmlParser(new ByteBuffer("".getBytes(StandardCharsets.UTF_8)));
		XmlNode node = parser.parse();
		assertEquals("root", node.getTag());
	}

	// -------------------------------- constructors: edge cases -------------------------------- //

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullByteBuffer() {

		DevNull.swallow(new XmlParser((ByteBuffer) null));
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullInputStreamSupplier() {

		DevNull.swallow(new XmlParser((Supplier<InputStream>) null));
	}
}
