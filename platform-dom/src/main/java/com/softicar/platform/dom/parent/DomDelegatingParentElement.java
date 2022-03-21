package com.softicar.platform.dom.parent;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomDelegatingElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.List;

public abstract class DomDelegatingParentElement extends DomDelegatingElement implements IDomParentElement {

	protected abstract IDomParentElement getTargetParentElement();

	@Override
	public <T extends IDomNode> T insertAt(T child, int index) {

		return getTargetParentElement().insertAt(child, index);
	}

	@Override
	public <T extends IDomNode> T replaceChild(T newChild, IDomNode oldChild) {

		return getTargetParentElement().replaceChild(newChild, oldChild);
	}

	@Override
	public void removeChild(IDomNode child) {

		getTargetParentElement().removeChild(child);
	}

	@Override
	public void removeChildren() {

		getTargetParentElement().removeChildren();
	}

	@Override
	public <T extends IDomNode> T appendChild(T child) {

		return getTargetParentElement().appendChild(child);
	}

	@Override
	public void appendChild(Object child) {

		getTargetParentElement().appendChild(child);
	}

	@Override
	public void appendChildren(Object...children) {

		getTargetParentElement().appendChildren(children);
	}

	@Override
	public <T extends IDomNode> T prependChild(T child) {

		return getTargetParentElement().prependChild(child);
	}

	@Override
	public <T extends IDomNode> T insertBefore(T child, IDomNode otherChild) {

		return getTargetParentElement().insertBefore(child, otherChild);
	}

	@Override
	public <T extends IDomNode> T insertAfter(T child, IDomNode otherChild) {

		return getTargetParentElement().insertAfter(child, otherChild);
	}

	@Override
	public void removeChildrenAfter(IDomNode child) {

		getTargetParentElement().removeChildrenAfter(child);
	}

	@Override
	public boolean hasChild(IDomNode child) {

		return getTargetParentElement().hasChild(child);
	}

	@Override
	public IDomTextNode appendText(String text) {

		return getTargetParentElement().appendText(text);
	}

	@Override
	public IDomTextNode appendText(IDisplayString text) {

		return getTargetParentElement().appendText(text);
	}

	@Override
	public IDomTextNode prependText(String text) {

		return getTargetParentElement().prependText(text);
	}

	@Override
	public IDomTextNode prependText(IDisplayString text) {

		return getTargetParentElement().prependText(text);
	}

	@Override
	public IDomTextNode appendText(String text, Object...args) {

		return getTargetParentElement().appendText(text, args);
	}

	@Override
	public IDomParentElement appendNewChild(DomElementTag tag) {

		return getTargetParentElement().appendNewChild(tag);
	}

	@Override
	public int getChildCount() {

		return getTargetParentElement().getChildCount();
	}

	@Override
	public int getChildIndex(IDomNode child) {

		return getTargetParentElement().getChildIndex(child);
	}

	@Override
	public IDomNode getChild(int index) {

		return getTargetParentElement().getChild(index);
	}

	@Override
	public List<IDomNode> getChildren() {

		return getTargetParentElement().getChildren();
	}

	@Override
	public void disableAllChildren() {

		getTargetParentElement().disableAllChildren();
	}

	@Override
	public void setEnabledRecursively(boolean enabled) {

		getTargetParentElement().setEnabledRecursively(enabled);
	}

	@Override
	public void listenToEvent(DomEventType type) {

		getTargetParentElement().listenToEvent(type);
	}

	@Override
	public void unlistenToEvent(DomEventType type) {

		getTargetParentElement().unlistenToEvent(type);
	}
}
