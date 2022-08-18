package com.softicar.platform.dom.utils;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.exception.IDomExceptionDisplayElement;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DomPayloadCodeExecutor {

	private final List<Consumer<Exception>> exceptionHandlers;
	private IDomNode eventNode;

	public DomPayloadCodeExecutor() {

		this.exceptionHandlers = new ArrayList<>();
		this.exceptionHandlers.add(this::showExceptionAsAlert);
		this.eventNode = null;
	}

	public DomPayloadCodeExecutor addExceptionHandler(Consumer<Exception> exceptionHandler) {

		this.exceptionHandlers.add(exceptionHandler);
		return this;
	}

	public DomPayloadCodeExecutor setEventNode(IDomNode eventNode) {

		this.eventNode = eventNode;
		return this;
	}

	public void execute(INullaryVoidFunction payloadCode) {

		try {
			payloadCode.apply();
		} catch (Exception exception) {
			exceptionHandlers.forEach(handler -> handler.accept(exception));
		}
	}

	private IDisplayString getDisplayMessage(Exception exception) {

		if (exception instanceof SofticarUserException) {
			return IDisplayString.create(exception.getLocalizedMessage());
		} else {
			return DomI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED;
		}
	}

	private void showExceptionAsAlert(Exception exception) {

		IDomExceptionDisplayElement displayElement = findExceptionDisplayElement(exception);
		if (displayElement != null) {
			displayElement.display(exception);
		} else {
			CurrentDomDocument.get().getBody().executeAlert(getDisplayMessage(exception));
		}
	}

	private IDomExceptionDisplayElement findExceptionDisplayElement(Exception exception) {

		while (eventNode != null) {
			if (eventNode instanceof IDomExceptionDisplayElement) {
				IDomExceptionDisplayElement displayElement = (IDomExceptionDisplayElement) eventNode;
				if (displayElement.accepts(exception)) {
					return displayElement;
				}
			}
			eventNode = eventNode.getParent();
		}
		return null;
	}
}
