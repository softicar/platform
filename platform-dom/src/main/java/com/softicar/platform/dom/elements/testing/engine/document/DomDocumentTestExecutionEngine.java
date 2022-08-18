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
import com.softicar.platform.dom.utils.DomPayloadCodeExecutor;
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

		var event = new DomTestEvent(node, type);
		var executor = new DomPayloadCodeExecutor().setEventNode(node);
		new DomEventHandlerNodeCaller(executor, node, event).call();
		node.getDomDocument().getRefreshBus().submitEvent();
	}

	@Override
	public void setValueText(IDomTextualInput node, String text) {

		node.setValue(text);
		sendEvent(node, DomEventType.TAB);
		sendEvent(node, DomEventType.CHANGE);
	}

	@Override
	public String getValueText(IDomTextualInput node) {

		return node.getValueText();
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
