package com.softicar.platform.dom.node;

import com.softicar.platform.dom.attribute.DomAttribute;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.attribute.IDomAttributeMap;
import com.softicar.platform.dom.attribute.IDomAttributeRegistry;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Optional;

/**
 * Basic implementation of {@link IDomNode}.
 * <p>
 * This class implements the basic methods of {@link IDomNode} and all methods
 * concerning the node attributes.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomNode implements IDomNode {

	private final int nodeId;
	private IDomParentElement parent;
	private IDomAttributeMap attributeMap;

	// -------------------- basic methods -------------------- //

	public AbstractDomNode(IDomDocument document) {

		this.nodeId = document.registerNode(this);
		this.attributeMap = document.getAttributeRegistry().getEmptyMap();
	}

	@Override
	public final int getNodeId() {

		return nodeId;
	}

	@Override
	public final IDomParentElement getParent() {

		return parent;
	}

	@Override
	public final void disappend() {

		if (parent != null) {
			parent.removeChild(this);
		}
	}

	@Override
	public boolean isAppended() {

		return Optional//
			.ofNullable(getParent())
			.map(IDomParentElement::isAppended)
			.orElse(false);
	}

	@Override
	public final IDomDocument getDomDocument() {

		return attributeMap.getDomDocument();
	}

	@Override
	public IDomNodeAccessor getAccessor() {

		return new Accessor(this);
	}

	// -------------------- attribute methods -------------------- //

	@Override
	public final Iterable<IDomAttribute> getAttributes() {

		return attributeMap.values();
	}

	@Override
	public final IDomAttribute getAttribute(String name) {

		IDomAttribute attribute = attributeMap.getAttribute(name);
		return attribute != null? attribute : new DomAttribute(name, null, false);
	}

	@Override
	public final Optional<String> getAttributeValue(String name) {

		return Optional//
			.ofNullable(attributeMap.getAttribute(name))
			.map(attribute -> attribute.getValue());
	}

	@Override
	public final IDomNode setAttribute(IDomAttribute attribute) {

		setAttributeInMap(attribute);
		getDomDocument().getEngine().setNodeAttribute(this, attribute);
		return this;
	}

	@Override
	public final IDomNode setAttribute(String name, int value) {

		return setAttribute(name, "" + value, false);
	}

	@Override
	public final IDomNode setAttribute(String name, boolean value) {

		return setAttribute(name, value? "true" : "false", false);
	}

	@Override
	public final IDomNode setAttribute(String name, String value) {

		if (value != null) {
			return setAttribute(name, value, true);
		} else {
			return setAttribute(name, value, false);
		}
	}

	@Override
	public final IDomNode setAttribute(String name, String value, boolean quote) {

		return setAttribute(new DomAttribute(name, value, quote));
	}

	@Override
	public final IDomNode unsetAttribute(String name) {

		return setAttribute(new DomAttribute(name, null, false));
	}

	// -------------------- accessor -------------------- //

	private class Accessor implements IDomNodeAccessor {

		private final AbstractDomNode node;

		public Accessor(AbstractDomNode node) {

			this.node = node;
		}

		@Override
		public void setParent(IDomParentElement parent) {

			node.setParent(parent);
		}

		@Override
		public void setAttributeInMap(IDomAttribute attribute) {

			node.setAttributeInMap(attribute);
		}

		@Override
		public void setAttributeInMap(String name, boolean value) {

			setAttributeInMap(name, value? "true" : "false", false);
		}

		@Override
		public void setAttributeInMap(String name, String value) {

			setAttributeInMap(name, value, true);
		}

		@Override
		public void setAttributeInMap(String name, String value, boolean quote) {

			setAttributeInMap(new DomAttribute(name, value, quote));
		}
	}

	// -------------------------------- internal -------------------------------- //

	void setParent(IDomParentElement parent) {

		this.parent = parent;
	}

	void setAttributeInMap(IDomAttribute attribute) {

		if (attribute.getValue() != null) {
			this.attributeMap = getAttributeRegistry().put(attributeMap, attribute);
		} else {
			this.attributeMap = getAttributeRegistry().remove(attributeMap, attribute.getName());
		}
	}

	private IDomAttributeRegistry getAttributeRegistry() {

		return attributeMap.getAttributeRegistry();
	}
}
