package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import java.util.Objects;

public abstract class AbstractDbEnumTableRowValue<T> implements IDbEnumTableRowValue {

	protected final T value;

	public AbstractDbEnumTableRowValue(T value) {

		this.value = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {

		return value + "";
	}
}
