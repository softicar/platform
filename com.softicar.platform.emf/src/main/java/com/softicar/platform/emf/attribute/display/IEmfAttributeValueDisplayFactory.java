package com.softicar.platform.emf.attribute.display;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Optional;

@FunctionalInterface
public interface IEmfAttributeValueDisplayFactory<V> {

	/**
	 * Creates an {@link IDomElement} to display the given value.
	 *
	 * @param value
	 *            the value to display (may be <i>null</i>)
	 * @return an {@link IDomElement} to display the given value (may be
	 *         <i>null</i>)
	 */
	IDomElement createDisplay(V value);

	default IDomElement createDisplay(Optional<V> value) {

		if (value.isPresent()) {
			return createDisplay(value.get());
		} else {
			return new DomDiv();
		}
	}
}
