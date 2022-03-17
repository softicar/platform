package com.softicar.platform.dom.element;

import com.softicar.platform.common.container.collection.MappingCollection;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomAutoEventHandler;
import com.softicar.platform.dom.node.DomNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;
import com.softicar.platform.dom.styles.CssPosition;
import java.io.IOException;
import java.util.Collection;

/**
 * Abstract implementation of {@link IDomElement}.
 * <p>
 * Derived classes need to implement at least the {@link #getTag()} method.
 *
 * @author Oliver Richers
 */
public abstract class DomElement extends DomNode implements IDomElement {

	public static enum HierarchyType {

		PARENT,
		LEAF
	}

	protected DomElement() {

		createElement();
	}

	protected DomElement(boolean doCreate) {

		if (doCreate) {
			createElement();
		}
	}

	protected DomElement(IDomDocument document, boolean doCreate) {

		super(document);

		if (doCreate) {
			createElement();
		}
	}

	protected void createElement() {

		getDomEngine().createElement(getNodeId(), getTag());

		// enable event listening
		if (this instanceof IDomAutoEventHandler) {
			for (DomEventType eventType: DomEventType.values()) {
				eventType.enableEventListening(this);
			}
		}
	}

	@Override
	public abstract DomElementTag getTag();

	// -------------------- events -------------------- //

	@Override
	public void listenToEvent(DomEventType type) {

		getDomEngine().listenToEvent(this, type);
	}

	@Override
	public void unlistenToEvent(DomEventType type) {

		getDomEngine().unlistenToEvent(this, type);
	}

	// -------------------- CSS CLASS -------------------- //

	@Override
	public void setCssClasses(Collection<ICssClass> classes) {

		classes.forEach(ICssClass::beforeUse);
		new DomElementCssClassAttributeManager(this).setClasses(new MappingCollection<>(classes, ICssClass::getName));
	}

	@Override
	public void addCssClasses(Collection<ICssClass> classes) {

		classes.forEach(ICssClass::beforeUse);
		new DomElementCssClassAttributeManager(this).addClasses(new MappingCollection<>(classes, ICssClass::getName));
	}

	@Override
	public void removeCssClasses(Collection<ICssClass> classes) {

		new DomElementCssClassAttributeManager(this).removeClasses(new MappingCollection<>(classes, ICssClass::getName));
	}

	@Override
	public void unsetCssClass() {

		setAttribute("class", null);
	}

	// -------------------- CSS STYLE -------------------- //

	@Override
	public DomElement setStyle(ICssStyle style, String value) {

		if (value != null) {
			getDomEngine().setNodeStyle(this, style.getJavascriptName(), value);
		} else {
			unsetStyle(style);
		}

		return this;
	}

	@Override
	public DomElement setStyle(ICssStyleAttribute styleAttribute) {

		return setStyle(styleAttribute.getStyle(), styleAttribute.getValue());
	}

	@Override
	public DomElement unsetStyle(ICssStyle style) {

		getDomEngine().unsetNodeStyle(this, style.getJavascriptName());
		return this;
	}

	// -------------------- TITLE -------------------- //

	@Override
	public DomElement setTitle(final String title) {

		if (title != null) {
			setAttribute("title", title);
		}
		return this;
	}

	// -------------------- DRAGGING -------------------- //

	@Override
	public void makeDraggable(CssPosition position) {

		setStyle(position);
		getDomEngine().makeDraggable(this, this);
	}

	@Override
	public void makeDraggable(CssPosition position, IDomNode initNode) {

		setStyle(position);
		getDomEngine().makeDraggable(this, initNode);
	}

	// -------------------------------- MISCELLANEOUS -------------------------------- //

	/**
	 * Replaces this node with the given one. This node must have a parent. The
	 * replacing node must NOT have a parent.
	 *
	 * @param <T>
	 *            the type of the replacing node
	 * @param replacingNode
	 *            the node to replace this one
	 * @return The replacing node. NOTE: Most likely you might want to assign
	 *         the return value to the object you called this method on.
	 */
	public <T extends IDomNode> T replaceWith(T replacingNode) {

		if (getParent() != null) {
			return getParent().replaceChild(replacingNode, this);
		} else {
			throw new SofticarDeveloperException("Trying to replace a node that has no parent.");
		}
	}

	// -------------------------------- HTML -------------------------------- //

	/**
	 * Builds HTML code representing this DOMElement.
	 *
	 * @param out
	 *            the {@link Appendable} object for outputting the HTML code
	 */
	@Override
	public void buildHtml(Appendable out) throws IOException {

		out.append("<" + getTag().getName());
		buildAttributesHTML(out);
		out.append("/>");
	}
}
