package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.node.DomNodes;

/**
 * Abstract base class for HTML input elements.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomInput extends DomElement implements IDomInput, IDomFocusable {

	@Override
	public final DomElementTag getTag() {

		return DomElementTag.INPUT;
	}

	@Override
	public AbstractDomInput setDisabled(boolean disabled) {

		return DomNodes.setDisabled(this, disabled);
	}

	@Override
	public boolean isDisabled() {

		return DomNodes.isDisabled(this);
	}

	@Override
	public final AbstractDomInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}
}
