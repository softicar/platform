package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.IDomElement;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO should use DomNodeTester but that's not accessible
 * <p>
 * TODO highly incomplete. implement more test cases.
 */
public class DomWikiDivBuilderTest extends Assert {

	private final DomWikiDivBuilder builder;

	public DomWikiDivBuilderTest() {

		CurrentDomDocument.set(new DomDocument());
		builder = new DomWikiDivBuilder();
	}

	@Test
	public void testAddLine() {

		final String payloadText = "payload text";
		builder.addLine(IDisplayString.create(payloadText));

		IDomElement element = builder.build();

		assertTrue(getHtmlString(element).contains(payloadText));
	}

	private String getHtmlString(IDomElement element) {

		try {
			StringBuilder output = new StringBuilder();
			element.buildHtml(output);
			return output.toString();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
