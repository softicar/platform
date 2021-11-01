package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Defines a transient field with {@link Optional} values.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientOptionalField<O extends IDbTableRow<O, ?>, V> extends AbstractTransientField<O, Optional<V>> {

	public AbstractTransientOptionalField() {

		super(TransientFieldValueTypes.getOptional());
	}

	@Override
	protected final void loadValues(Map<O, Optional<V>> objectValueMap) {

		loadValues(objectValueMap.keySet(), objectValueMap::put);
	}

	@Override
	protected final Optional<V> getDefaultValue() {

		return Optional.empty();
	}

	protected abstract void loadValues(Set<O> objects, IOptionalValueSetter<O, V> setter);
}
