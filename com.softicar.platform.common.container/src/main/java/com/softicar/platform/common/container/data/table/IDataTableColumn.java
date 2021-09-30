package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a table column of an {@link IDataTable}.
 *
 * @author Oliver Richers
 */
public interface IDataTableColumn<R, V> {

	/**
	 * Returns the column title.
	 *
	 * @return column title (never null)
	 */
	IDisplayString getTitle();

	/**
	 * Returns the class of the values in this column.
	 *
	 * @return the column value class (never null)
	 */
	Class<V> getValueClass();

	/**
	 * Returns the column value from the given table row.
	 *
	 * @param tableRow
	 *            the table row
	 * @return the value (may be null)
	 */
	V getValue(R tableRow);

	/**
	 * Returns a collection of all values in the given rows.
	 *
	 * @param tableRows
	 *            the table rows
	 * @return collection of all values (the returned collection of values has
	 *         exactly the same size as the given collection of table rows)
	 */
	default Collection<V> getValues(Collection<? extends R> tableRows) {

		return tableRows//
			.stream()
			.map(row -> getValue(row))
			.collect(Collectors.toList());
	}

	/**
	 * Returns an optional value comparator.
	 *
	 * @return an {@link Optional} of {@link Comparator} (never null)
	 */
	default Optional<Comparator<V>> getValueComparator() {

		return Optional.empty();
	}

	/**
	 * Pre-fetches data for the given values.
	 * <p>
	 * For example, if this is a foreign-key column, this method un-stubs the
	 * given values.
	 *
	 * @param values
	 *            the values to prefetch data for
	 */
	void prefetchData(Collection<V> values);
}
