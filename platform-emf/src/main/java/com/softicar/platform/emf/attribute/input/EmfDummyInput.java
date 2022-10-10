package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * Fallback implementation of {@link IEmfInput}.
 *
 * @author Oliver Richers
 */
public class EmfDummyInput<V> extends AbstractDomValueInputDiv<V> implements IEmfInput<V> {

	private V value;

	public EmfDummyInput(IEmfTableRow<?, ?> row) {

		DevNull.swallow(row);
		this.value = null;
	}

	@Override
	public void setValue(V value) {

		this.value = value;
	}

	@Override
	public Optional<V> getValue() {

		return Optional.ofNullable(value);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		// nothing to do
	}

	@Override
	public IEmfInput<V> appendLabel(IDisplayString label) {

		//nothing to do
		return this;
	}
}
