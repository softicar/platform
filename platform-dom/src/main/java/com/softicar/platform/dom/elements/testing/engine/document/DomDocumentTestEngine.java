package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineLazySetup;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomContextMenuEventHandler;
import com.softicar.platform.dom.event.IDomDoubleClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEscapeKeyEventHandler;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.dom.input.IDomStringInputNode;
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

		DomTestEvent event = new DomTestEvent(type);
		CurrentDomDocument.get().setCurrentEvent(event);

		if (node instanceof IDomEventHandler) {
			try {
				((IDomEventHandler) node).handleDOMEvent(event);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		} else if (type == DomEventType.CLICK && node instanceof IDomClickEventHandler) {
			((IDomClickEventHandler) node).handleClick(event);
		} else if (type == DomEventType.ENTER && node instanceof IDomEnterKeyEventHandler) {
			((IDomEnterKeyEventHandler) node).handleEnterKey(event);
		} else if (type == DomEventType.SPACE && node instanceof IDomSpaceKeyEventHandler) {
			((IDomSpaceKeyEventHandler) node).handleSpaceKey(event);
		} else if (type == DomEventType.ESCAPE && node instanceof IDomEscapeKeyEventHandler) {
			((IDomEscapeKeyEventHandler) node).handleEscapeKey(event);
		} else if (type == DomEventType.DBLCLICK && node instanceof IDomDoubleClickEventHandler) {
			((IDomDoubleClickEventHandler) node).handleDoubleClick(event);
		} else if (type == DomEventType.CONTEXTMENU && node instanceof IDomContextMenuEventHandler) {
			((IDomContextMenuEventHandler) node).handleContextMenu(event);
		}
		node.getDomDocument().getRefreshBus().submitEvent();
	}

	@Override
	public void setInputValue(IDomStringInputNode node, String text) {

		node.setValue(text);
	}

	@Override
	public String getInputValue(IDomStringInputNode node) {

		return node.getValue();
	}
}
