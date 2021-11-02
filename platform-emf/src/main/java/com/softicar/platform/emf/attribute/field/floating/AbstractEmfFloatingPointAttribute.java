package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfFloatingPointAttribute<R extends IEmfTableRow<R, ?>, V> extends EmfFieldAttribute<R, V>
		implements IEmfFloatingPointAttributeStrategy<V> {

	private static final int DEFAULT_PRECISION = 6;
	protected int precision;

	public AbstractEmfFloatingPointAttribute(IDbField<R, V> field) {

		super(field);
		this.precision = DEFAULT_PRECISION;

		setDisplayFactory(value -> new EmfFloatingPointDisplay<>(this, value));
		setInputFactory(() -> new EmfFloatingPointInput<>(this));
	}
}
