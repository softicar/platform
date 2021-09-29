package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Basic interface implemented by all value-aware input nodes.
 *
 * @author Alexander Schmidt
 */
public interface IDomValueBasedInputNode<V> extends IDomInputNode {

	// -------------------------------- value -------------------------------- //

	void setValue(V value);

	V getValue();

	// -------------------------------- placeholder -------------------------------- //

	default IDomValueBasedInputNode<V> setPlaceholder(IDisplayString placeholder) {

		setAttribute("placeholder", placeholder.toString());
		return this;
	}
}
