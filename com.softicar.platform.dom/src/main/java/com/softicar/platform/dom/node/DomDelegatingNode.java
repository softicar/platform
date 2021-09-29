package com.softicar.platform.dom.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class DomDelegatingNode implements IDomNode {

	protected abstract IDomNode getTargetNode();

	@Override
	public int getNodeId() {

		return getTargetNode().getNodeId();
	}

	@Override
	public IDomParentElement getParent() {

		return getTargetNode().getParent();
	}

	@Override
	public void disappend() {

		getTargetNode().disappend();
	}

	@Override
	public IDomDocument getDomDocument() {

		return getTargetNode().getDomDocument();
	}

	@Override
	public IDomNodeAccessor getAccessor() {

		return getTargetNode().getAccessor();
	}

	@Override
	public Iterable<IDomAttribute> getAttributes() {

		return getTargetNode().getAttributes();
	}

	@Override
	public IDomAttribute getAttribute(String name) {

		return getTargetNode().getAttribute(name);
	}

	@Override
	public Optional<String> getAttributeValue(String name) {

		return getTargetNode().getAttributeValue(name);
	}

	@Override
	public IDomNode setAttribute(IDomAttribute attribute) {

		return getTargetNode().setAttribute(attribute);
	}

	@Override
	public IDomNode setAttribute(String name, int value) {

		return getTargetNode().setAttribute(name, value);
	}

	@Override
	public IDomNode setAttribute(String name, boolean value) {

		return getTargetNode().setAttribute(name, value);
	}

	@Override
	public IDomNode setAttribute(String name, String value) {

		return getTargetNode().setAttribute(name, value);
	}

	@Override
	public IDomNode setAttribute(String name, String value, boolean quote) {

		return getTargetNode().setAttribute(name, value, quote);
	}

	@Override
	public IDomNode unsetAttribute(String name) {

		return getTargetNode().unsetAttribute(name);
	}

	@Override
	public void buildHtml(Appendable out) throws IOException {

		getTargetNode().buildHtml(out);
	}

	@Override
	public void executeAlert(IDisplayString message) {

		getTargetNode().executeAlert(message);
	}

	@Override
	public void executeConfirm(INullaryVoidFunction confirmHandler, IDisplayString message) {

		getTargetNode().executeConfirm(confirmHandler, message);
	}

	@Override
	public void executePrompt(Consumer<String> promptHandler, IDisplayString message, String defaultValue) {

		getTargetNode().executePrompt(promptHandler, message, defaultValue);
	}
}
