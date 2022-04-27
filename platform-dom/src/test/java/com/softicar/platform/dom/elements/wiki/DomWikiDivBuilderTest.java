package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

/**
 * TODO highly incomplete. implement more test cases.
 */
public class DomWikiDivBuilderTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomWikiDivBuilder builder;

	public DomWikiDivBuilderTest() {

		this.builder = new DomWikiDivBuilder();

		setNodeSupplier(() -> builder.build());
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testAddLine() {

		String payloadText = "payload text";
		builder.addLine(IDisplayString.create(payloadText));

		findBody().assertContainsText(payloadText);
	}
}
