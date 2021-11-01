package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Set;
import java.util.TreeSet;

/**
 * Defines a transient field with values of type {@link Set}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientTreeSetField<O extends IDbTableRow<O, ?>, V extends Comparable<? super V>> extends AbstractTransientSetField<O, V> {

	public AbstractTransientTreeSetField() {

		super(TreeSet::new);
	}
}
