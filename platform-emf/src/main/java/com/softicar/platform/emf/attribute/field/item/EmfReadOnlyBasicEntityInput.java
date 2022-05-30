package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfReadOnlyBasicEntityInput<V extends IEntity> extends DomDiv implements IEmfInput<V> {

	private V value;
	private boolean disabled;

	public EmfReadOnlyBasicEntityInput() {

		this.value = null;
		this.disabled = false;
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
	public EmfReadOnlyBasicEntityInput<V> setDisabled(boolean disabled) {

		this.disabled = disabled;
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public final EmfReadOnlyBasicEntityInput<V> setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}
}
