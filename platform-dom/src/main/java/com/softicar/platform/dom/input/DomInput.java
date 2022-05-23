package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.node.DomNodes;

/**
 * Abstract base class for HTML input elements.
 *
 * @author Oliver Richers
 */
public abstract class DomInput extends DomElement implements IDomDisableable, IDomFocusable {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.INPUT;
	}

	@Override
	public DomInput setDisabled(boolean disabled) {

		return DomNodes.setDisabled(this, disabled);
	}

	/**
	 * @deprecated use {@link #setDisabled(boolean)} instead
	 */
	@Deprecated
	public final DomInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public boolean isDisabled() {

		return DomNodes.isDisabled(this);
	}

	/**
	 * @deprecated use {@link #isDisabled()} instead
	 */
	@Deprecated
	public final boolean isEnabled() {

		return !isDisabled();
	}
}
