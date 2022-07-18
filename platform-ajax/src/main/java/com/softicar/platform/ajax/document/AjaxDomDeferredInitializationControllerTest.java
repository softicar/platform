package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineMethods;
import com.softicar.platform.dom.node.initialization.AbstractDomDeferredInitializationControllerTest;
import org.junit.Rule;
import org.junit.Test;

public class AjaxDomDeferredInitializationControllerTest extends AbstractDomDeferredInitializationControllerTest
		implements IAjaxSeleniumLowLevelTestEngineMethods {

	@Rule public final IAjaxSeleniumLowLevelTestEngine engine = new AjaxSeleniumLowLevelTestEngine();

	private final TestDiv testDiv;

	public AjaxDomDeferredInitializationControllerTest() {

		testDiv = openTestNode(() -> new TestDiv(this::addInitializedNode));
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngine getTestEngine() {

		return engine;
	}

	@Test
	public void test() {

		// assert initial state
		assertTrue(initializedNodes.isEmpty());

		// connect upper node
		click(TestDiv.CONNECT_UPPER_NODE_BUTTON);
		waitForServer();
		assertEquals(1, initializedNodes.size());
		assertSame(testDiv.upperNode, initializedNodes.get(0));

		// connect lower and middle ndoes
		click(TestDiv.CONNECT_MIDDLE_AND_LOWER_NODES_BUTTON);
		waitForServer();
		assertEquals(3, initializedNodes.size());
		assertSame(testDiv.upperNode, initializedNodes.get(0));
		assertSame(testDiv.lowerNode, initializedNodes.get(1));
		assertSame(testDiv.middleNode, initializedNodes.get(2));
	}
}
