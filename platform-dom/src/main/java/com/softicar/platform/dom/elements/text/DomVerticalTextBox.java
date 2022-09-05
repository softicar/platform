package com.softicar.platform.dom.elements.text;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomDelegatingParentElement;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Display the appended text and elements vertically.
 * <p>
 * The content is rotated by 90 degrees to the left. It has a fixed width and
 * the text does not wrap.
 *
 * @author Oliver Richers
 */
public class DomVerticalTextBox extends DomDelegatingParentElement {

	private final DomDiv inner;
	private final DomDiv outer;

	public DomVerticalTextBox() {

		this.inner = new DomDiv();
		this.outer = new DomDiv();

		outer.setCssClass(DomCssClasses.DOM_VERTICAL_TEXT_BOX);
		outer.appendChild(inner);
	}

	@Override
	protected IDomParentElement getTargetParentElement() {

		return inner;
	}

	@Override
	protected IDomElement getTargetElement() {

		return outer;
	}

	@Override
	protected IDomNode getTargetNode() {

		return outer;
	}
}
