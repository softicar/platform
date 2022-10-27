package com.softicar.platform.dom.node.initialization;

import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class DomDeferredInitializationControllerTest extends AbstractDomDeferredInitializationControllerTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final TestDiv testDiv;

	public DomDeferredInitializationControllerTest() {

		testDiv = new TestDiv(this::addInitializedNode);
		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void test() {

		// assert initial state
		assertTrue(initializedNodes.isEmpty());

		// connect upper node
		findButton(TestDiv.CONNECT_UPPER_NODE_BUTTON).click();
		assertEquals(1, initializedNodes.size());
		assertSame(testDiv.upperNode, initializedNodes.get(0));

		// connect lower and middle nodes
		findButton(TestDiv.CONNECT_MIDDLE_AND_LOWER_NODES_BUTTON).click();
		assertEquals(3, initializedNodes.size());
		assertSame(testDiv.upperNode, initializedNodes.get(0));
		assertSame(testDiv.lowerNode, initializedNodes.get(1));
		assertSame(testDiv.middleNode, initializedNodes.get(2));
	}
}
