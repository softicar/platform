package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class DbFieldBasedDataTableColumn<R, V> implements IDataTableColumn<R, V> {

	private final IDbField<R, V> field;
	private final Optional<Comparator<V>> valueComparator;

	public DbFieldBasedDataTableColumn(IDbField<R, V> field) {

		this.field = field;
		this.valueComparator = Optional.of(field.getValueType()::compare);
	}

	public IDbField<R, V> getField() {

		return field;
	}

	@Override
	public IDisplayString getTitle() {

		return field.getTitle();
	}

	@Override
	public Class<V> getValueClass() {

		return field.getValueType().getValueClass();
	}

	@Override
	public V getValue(R tableRow) {

		return field.getValue(tableRow);
	}

	@Override
	public Optional<Comparator<V>> getValueComparator() {

		return valueComparator;
	}

	@Override
	public void prefetchData(Collection<V> values) {

		field.prefetchData(values);
	}
}
