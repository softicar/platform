package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.dom.elements.DomDiv;
import java.util.Optional;

/**
 * Fallback implementation of {@link IEmfInput}.
 *
 * @author Oliver Richers
 */
public class EmfDummyInput<V> extends DomDiv implements IEmfInput<V> {

	private V value;

	@Override
	public void setValue(V value) {

		this.value = value;
	}

	@Override
	public Optional<V> getValue() {

		return Optional.ofNullable(value);
	}
}
