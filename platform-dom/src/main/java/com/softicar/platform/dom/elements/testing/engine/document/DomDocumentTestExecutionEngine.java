package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineLazySetup;
import com.softicar.platform.dom.event.DomEventHandlerNodeCaller;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.rules.TestWatcher;

/**
 * An implementation of {@link IDomTestExecutionEngine} which is based upon
 * direct interaction with the current {@link DomDocument}.
 *
 * @author Alexander Schmidt
 */
public class DomDocumentTestExecutionEngine extends TestWatcher implements IDomTestExecutionEngine {

	private final IDomTestExecutionEngineLazySetup setup;

	public DomDocumentTestExecutionEngine() {

		this.setup = DomDocumentTestExecutionEngineLazySetup.createAndRegister();
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

		new DomEventHandlerNodeCaller(node, new DomTestEvent(node, type)).call();

		node.getDomDocument().getRefreshBus().submitEvent();
	}

	@Override
	public void setInputValue(IDomTextualInput node, String text) {

		node.setInputText(text);
	}

	@Override
	public String getInputValue(IDomTextualInput node) {

		return node.getInputText();
	}

	@Override
	public boolean isDisplayed(IDomNode node) {

		return !isDisplayNone(node) && (isDocumenyBody(node) || isParentDisplayed(node));
	}

	private boolean isDisplayNone(IDomNode node) {

		return node//
			.getDomEngine()
			.getNodeStyle(node, CssStyle.DISPLAY.getJavascriptName())
			.map(value -> value.equalsIgnoreCase(CssDisplay.NONE.getValue()))
			.orElse(false);
	}

	private boolean isDocumenyBody(IDomNode node) {

		return node == node.getDomDocument().getBody();
	}

	private boolean isParentDisplayed(IDomNode node) {

		return Optional//
			.ofNullable(node.getParent())
			.map(this::isDisplayed)
			.orElse(false);
	}
}
