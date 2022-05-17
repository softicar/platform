package com.softicar.platform.dom.element;

import com.softicar.platform.common.container.collection.MappingCollection;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomAutoEventHandler;
import com.softicar.platform.dom.node.AbstractDomNode;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collection;

/**
 * Abstract implementation of {@link IDomElement}.
 * <p>
 * Derived classes need to implement at least the {@link #getTag()} method.
 *
 * @author Oliver Richers
 */
public abstract class DomElement extends AbstractDomNode implements IDomElement {

	protected DomElement() {

		this(true);
	}

	protected DomElement(boolean doCreate) {

		this(CurrentDomDocument.get(), doCreate);
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

	// -------------------- CSS class -------------------- //

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

	// -------------------- CSS style -------------------- //

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
	public DomElement unsetStyle(ICssStyle style) {

		getDomEngine().unsetNodeStyle(this, style.getJavascriptName());
		return this;
	}

	@Override
	public IDomElement setDisplayNone(boolean displayNone) {

		if (displayNone) {
			setStyle(CssDisplay.NONE);
		} else {
			unsetStyle(CssStyle.DISPLAY);
		}
		return this;
	}

	// -------------------- tab index -------------------- //

	@Override
	public IDomElement setTabIndex(Integer tabIndex) {

		setAttribute("tabindex", tabIndex);
		return this;
	}

	// -------------------- title -------------------- //

	@Override
	public IDomElement setTitle(String title) {

		setAttribute("title", title);
		return this;
	}
}
