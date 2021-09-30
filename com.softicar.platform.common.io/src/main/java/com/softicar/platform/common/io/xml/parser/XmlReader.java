package com.softicar.platform.common.io.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class XmlReader {

	private final Document document;

	public XmlReader(Supplier<InputStream> inputStreamSupplier) {

		try (var inputStream = inputStreamSupplier.get()) {
			var factory = createBuilderFactory();
			var builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ThrowingErrorHandler());
			this.document = process(builder.parse(inputStream));
		} catch (SAXException | ParserConfigurationException | IOException exception) {
			throw new XmlParserException(exception);
		}
	}

	public void validate(Supplier<InputStream> schemaStreamSupplier) {

		try (InputStream schemaStream = schemaStreamSupplier.get()) {
			validate(document, createSchema(schemaStream));
		} catch (SAXException | IOException exception) {
			throw new XmlParserException(exception);
		}
	}

	public Element getDocumentElement() {

		return document.getDocumentElement();
	}

	private void validate(Document document, Schema schema) throws SAXException, IOException {

		var validator = schema.newValidator();
		validator.setErrorHandler(new ThrowingErrorHandler());
		validator.validate(new DOMSource(document));
	}

	private Document process(Document document) {

		// apply normalization - see http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		document.getDocumentElement().normalize();

		return document;
	}

	private DocumentBuilderFactory createBuilderFactory() throws ParserConfigurationException {

		var factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		return factory;
	}

	private Schema createSchema(InputStream schemaStream) throws SAXException {

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return factory.newSchema(new StreamSource(schemaStream));
	}

	private static class ThrowingErrorHandler implements ErrorHandler {

		@Override
		public void error(SAXParseException exception) throws SAXException {

			throw exception;
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {

			throw exception;
		}

		@Override
		public void warning(SAXParseException exception) throws SAXException {

			throw exception;
		}
	}
}
