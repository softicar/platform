package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineLazySetup;
import com.softicar.platform.dom.event.DomEventHandlerNodeCaller;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Supplier;
import org.junit.rules.TestWatcher;

/**
 * An implementation of {@link IDomTestEngine} which is based upon direct
 * interaction with the current {@link DomDocument}.
 *
 * @author Alexander Schmidt
 */
public class DomDocumentTestEngine extends TestWatcher implements IDomTestEngine {

	private final IDomTestEngineLazySetup setup;

	public DomDocumentTestEngine() {

		this.setup = DomDocumentTestEngineLazySetup.createAndRegister();
	}

	@Override
	public void setNodeSupplier(Supplier<IDomNode> nodeSupplier) {

		setup.setNodeSupplier(nodeSupplier);
	}

	@Override
	public IDomNode getBodyNode() {

		return setup.getBodyNode();
	}

	@Override
	public void sendEvent(IDomNode node, DomEventType type) {

		new DomEventHandlerNodeCaller(node, new DomTestEvent(type)).call();

		node.getDomDocument().getRefreshBus().submitEvent();
	}

	@Override
	public void setInputValue(IDomTextualInput node, String text) {

		node.setInputText(text);
		sendEvent(node, DomEventType.TAB);
	}

	@Override
	public String getInputValue(IDomTextualInput node) {

		return node.getInputText();
	}

	@Override
	public boolean isDisplayed(IDomNode node) {

		// TODO implement PLAT-758
		throw new UnsupportedOperationException("Not implemented. Consider to use a different test engine.");
	}
}
