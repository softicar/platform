package com.softicar.platform.dom.document;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.elements.DomScript;
import java.util.stream.Collectors;
import org.junit.Test;

public class DomDocumentScriptRegistryTest extends Asserts {

	@Test
	public void testGetInstance() {

		var documentA = new DomDocument();
		var documentB = new DomDocument();

		var registryA = DomDocumentScriptRegistry.getInstance(documentA);
		var registryB = DomDocumentScriptRegistry.getInstance(documentB);

		assertNotSame(registryA, registryB);
		assertSame(registryA, DomDocumentScriptRegistry.getInstance(documentA));
		assertSame(registryB, DomDocumentScriptRegistry.getInstance(documentB));
	}

	@Test
	public void testRegisterScript() {

		var document = new DomDocument();
		var registry = DomDocumentScriptRegistry.getInstance(document);

		CurrentDomDocument.set(document);
		registry.registerScript("http://example.org/foo", MimeType.TEXT_JAVASCRIPT);
		registry.registerScript("http://example.org/foo", MimeType.TEXT_JAVASCRIPT);
		registry.registerScript("http://example.org/foo/", MimeType.TEXT_JAVASCRIPT);

		registry.registerScript("http://example.org/bar", MimeType.TEXT_PLAIN);
		registry.registerScript("http://example.org/bar", MimeType.TEXT_JAVASCRIPT);

		var scriptElements = document//
			.getHead()
			.getChildren()
			.stream()
			.filter(DomScript.class::isInstance)
			.map(DomScript.class::cast)
			.collect(Collectors.toList());

		assertCount(3, scriptElements);
		assertScriptElement("http://example.org/foo", MimeType.TEXT_JAVASCRIPT, scriptElements.get(0));
		assertScriptElement("http://example.org/foo/", MimeType.TEXT_JAVASCRIPT, scriptElements.get(1));
		assertScriptElement("http://example.org/bar", MimeType.TEXT_PLAIN, scriptElements.get(2));
	}

	private void assertScriptElement(String expectedUrl, MimeType expectedType, DomScript scriptElement) {

		assertEquals(expectedUrl, assertOne(scriptElement.getAttributeValue("src")));
		assertEquals(expectedType.getIdentifier(), assertOne(scriptElement.getAttributeValue("type")));
	}
}
