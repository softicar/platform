package com.softicar.platform.common.io.xml.parser;

import java.nio.charset.StandardCharsets;
import org.junit.Assert;

/**
 * Base class of XML parser tests.
 * <p>
 * Provides exemplary XML and XSD data, shamelessly taken from
 * <a href="https://www.w3schools.com/xml/schema_example.asp">w3schools.com</a>
 *
 * @author Alexander Schmidt
 */
abstract class AbstractXmlParserTest extends Assert {

	protected static final String XML = """
			<?xml version="1.0" encoding="UTF-8"?>

			<shiporder orderid="889923"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:noNamespaceSchemaLocation="shiporder.xsd">
			  <orderperson>John Smith</orderperson>
			  <shipto>
			    <name>Ola Nordmann</name>
			    <address>Langgt 23</address>
			    <city>4000 Stavanger</city>
			    <country>Norway</country>
			  </shipto>
			  <item>
			    <title>Empire Burlesque</title>
			    <note>Special Edition</note>
			    <quantity>1</quantity>
			    <price>10.90</price>
			  </item>
			  <item>
			    <title>Hide your heart</title>
			    <quantity>1</quantity>
			    <price>9.90</price>
			  </item>
			</shiporder>
			""";

	protected static final String XSD = """
			<?xml version="1.0" encoding="UTF-8" ?>
			<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">

			<xs:element name="shiporder">
			  <xs:complexType>
			    <xs:sequence>
			      <xs:element name="orderperson" type="xs:string"/>
			      <xs:element name="shipto">
			        <xs:complexType>
			          <xs:sequence>
			            <xs:element name="name" type="xs:string"/>
			            <xs:element name="address" type="xs:string"/>
			            <xs:element name="city" type="xs:string"/>
			            <xs:element name="country" type="xs:string"/>
			          </xs:sequence>
			        </xs:complexType>
			      </xs:element>
			      <xs:element name="item" maxOccurs="unbounded">
			        <xs:complexType>
			          <xs:sequence>
			            <xs:element name="title" type="xs:string"/>
			            <xs:element name="note" type="xs:string" minOccurs="0"/>
			            <xs:element name="quantity" type="xs:positiveInteger"/>
			            <xs:element name="price" type="xs:decimal"/>
			          </xs:sequence>
			        </xs:complexType>
			      </xs:element>
			    </xs:sequence>
			    <xs:attribute name="orderid" type="xs:string" use="required"/>
			  </xs:complexType>
			</xs:element>

			</xs:schema>
			""";

	protected static final byte[] XML_BYTES = XML.getBytes(StandardCharsets.UTF_8);
	protected static final byte[] XSD_BYTES = XSD.getBytes(StandardCharsets.UTF_8);
}
