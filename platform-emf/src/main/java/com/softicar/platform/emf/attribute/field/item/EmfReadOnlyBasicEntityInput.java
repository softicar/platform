package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfReadOnlyBasicEntityInput<V extends IEntity> extends AbstractDomValueInput<V> implements IEmfInput<V> {

	private V value;

	public EmfReadOnlyBasicEntityInput() {

		this.value = null;
	}

	@Override
	public Optional<V> getValue() {

		return Optional.ofNullable(value);
	}

	@Override
	public void setValue(V value) {

		this.value = value;

		removeChildren();
		if (value != null) {
			appendChild(value.toDisplay());
		}
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		// nothing to do
	}
}
