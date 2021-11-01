package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default implementation of {@link IDataTableColumn}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class InMemoryDataTableColumn<R, V> implements IDataTableColumn<R, V> {

	private final Function<R, V> valueGetter;
	private final Class<V> valueClass;
	private final Optional<Comparator<V>> valueComparator;
	private final IDisplayString title;

	public InMemoryDataTableColumn(InMemoryDataTableColumnBuilder<R, V> columnBuilder) {

		this.valueGetter = columnBuilder.getValueGetter();
		this.valueClass = columnBuilder.getValueClass();
		this.valueComparator = columnBuilder.getValueComparator();
		this.title = columnBuilder.getTitle();
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public Class<V> getValueClass() {

		return valueClass;
	}

	@Override
	public V getValue(R tableRow) {

		Collection<V> values = getValues(Collections.singleton(tableRow));
		if (values != null && !values.isEmpty()) {
			return values.iterator().next();
		} else {
			return null;
		}
	}

	@Override
	public Collection<V> getValues(Collection<? extends R> tableRows) {

		Collection<V> values = new ArrayList<>();
		for (R tableRow: tableRows) {
			values.add(Optional.ofNullable(tableRow).map(valueGetter::apply).orElse(null));
		}
		return values;
	}

	@Override
	public Optional<Comparator<V>> getValueComparator() {

		return valueComparator;
	}

	@Override
	public void prefetchData(Collection<V> values) {

		// nothing to do
	}
}
