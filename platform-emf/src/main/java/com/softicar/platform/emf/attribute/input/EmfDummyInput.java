package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * Fallback implementation of {@link IEmfInput}.
 *
 * @author Oliver Richers
 */
public class EmfDummyInput<V> extends DomDiv implements IEmfInput<V> {

	private V value;

	public EmfDummyInput(IEmfTableRow<?, ?> row) {

		DevNull.swallow(row);
	}

	@Override
	public void setValue(V value) {

		this.value = value;
	}

	@Override
	public Optional<V> getValue() {

		return Optional.ofNullable(value);
	}
}
