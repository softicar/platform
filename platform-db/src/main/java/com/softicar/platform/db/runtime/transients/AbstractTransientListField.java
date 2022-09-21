package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Defines a transient field with values of type {@link List}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientListField<O extends IDbTableRow<O, ?>, V> extends AbstractTransientCollectionField<O, List<V>, V> {

	public AbstractTransientListField() {

		super(TransientFieldValueTypes.getList(), Collections.emptyList(), ArrayList::new, List::add);
	}
}
