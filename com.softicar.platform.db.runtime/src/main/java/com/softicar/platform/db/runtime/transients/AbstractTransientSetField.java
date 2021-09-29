package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Defines a transient field with values of type {@link Set}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientSetField<O extends IDbTableRow<O, ?>, V> extends AbstractTransientCollectionField<O, Set<V>, V> {

	public AbstractTransientSetField(Supplier<Set<V>> factory) {

		super(TransientFieldValueTypes.getSet(), Collections.emptySet(), factory, Set::add);
	}
}
