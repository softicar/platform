package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;

/**
 * A container element for {@link DomButton} and similar action elements.
 *
 * @author Oliver Richers
 */
public class DomActionBar extends DomBar {

	public DomActionBar() {

		this(new IDomNode[0]);
	}

	public DomActionBar(IDomNode...children) {

		super(children);
		addCssClass(DomCssClasses.DOM_ACTION_BAR);
	}
}
