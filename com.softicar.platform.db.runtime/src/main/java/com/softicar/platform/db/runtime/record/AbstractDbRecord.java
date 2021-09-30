package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.AbstractDbTableRow;

public abstract class AbstractDbRecord<R extends AbstractDbRecord<R, P>, P> extends AbstractDbTableRow<R, P> implements IDbRecord<R, P> {

	@Override
	public IDbRecordInitializer<R, P> initializer() {

		return new DbRecordInitializer<>(getThis());
	}

	@Override
	public int compareTo(R other) {

		for (IDbField<R, ?> field: table().getPrimaryKey().getFields()) {
			int result = compare(field, getThis(), other);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	private <V> int compare(IDbField<R, V> field, R first, R second) {

		V firstValue = field.getValue(first);
		V secondValue = field.getValue(second);
		return field.getValueType().compare(firstValue, secondValue);
	}
}
