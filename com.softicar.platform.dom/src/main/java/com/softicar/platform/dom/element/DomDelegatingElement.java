package com.softicar.platform.dom.element;

import com.softicar.platform.dom.node.DomDelegatingNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;
import com.softicar.platform.dom.styles.CssPosition;
import java.util.Collection;

public abstract class DomDelegatingElement extends DomDelegatingNode implements IDomElement {

	protected abstract IDomElement getTargetElement();

	@Override
	public DomElementTag getTag() {

		return getTargetElement().getTag();
	}

	// -------------------- CSS class methods -------------------- //

	@Override
	public void setCssClasses(Collection<ICssClass> classes) {

		getTargetElement().setCssClasses(classes);
	}

	@Override
	public void addCssClasses(Collection<ICssClass> classes) {

		getTargetElement().addCssClasses(classes);
	}

	@Override
	public void removeCssClasses(Collection<ICssClass> classes) {

		getTargetElement().removeCssClasses(classes);
	}

	@Override
	public void unsetCssClass() {

		getTargetElement().unsetCssClass();
	}

	// -------------------- css style methods -------------------- //

	@Override
	public IDomElement setStyle(ICssStyle style, String value) {

		return getTargetElement().setStyle(style, value);
	}

	@Override
	public IDomElement setStyle(ICssStyleAttribute styleAttribute) {

		return getTargetElement().setStyle(styleAttribute);
	}

	@Override
	public IDomElement unsetStyle(ICssStyle style) {

		return getTargetElement().unsetStyle(style);
	}

	// -------------------- title -------------------- //

	@Override
	public IDomElement setTitle(String title) {

		return getTargetElement().setTitle(title);
	}

	// -------------------- dragging -------------------- //

	@Override
	public void makeDraggable(CssPosition position) {

		getTargetElement().makeDraggable(position);
	}

	@Override
	public void makeDraggable(CssPosition position, IDomNode initNode) {

		getTargetElement().makeDraggable(position, initNode);
	}
}
