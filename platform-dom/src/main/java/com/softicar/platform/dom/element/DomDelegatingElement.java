package com.softicar.platform.dom.element;

import com.softicar.platform.dom.node.DomDelegatingNode;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;
import java.util.Collection;

public abstract class DomDelegatingElement extends DomDelegatingNode implements IDomElement {

	protected abstract IDomElement getTargetElement();

	@Override
	public DomElementTag getTag() {

		return getTargetElement().getTag();
	}

	// -------------------- CSS class -------------------- //

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

	// -------------------- CSS style -------------------- //

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

	@Override
	public IDomElement setDisplayNone(boolean displayNone) {

		return getTargetElement().setDisplayNone(displayNone);
	}

	// -------------------- tab index -------------------- //

	@Override
	public IDomElement setTabIndex(Integer tabIndex) {

		return getTargetElement().setTabIndex(tabIndex);
	}

	// -------------------- title -------------------- //

	@Override
	public IDomElement setTitle(String title) {

		return getTargetElement().setTitle(title);
	}
}
