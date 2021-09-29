package com.softicar.platform.common.container.data.table;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Extension interface for {@link IDataTable} providing default implementations
 * for many row related methods.
 * <p>
 * The only methods that really need to be implemented are
 * {@link #iterator(int, int)} and {@link #count()}.
 *
 * @author Oliver Richers
 */
public interface IDataTableRowMethods<R> extends Iterable<R> {

	/**
	 * Returns an {@link Iterator} over all table rows.
	 *
	 * @return an {@link Iterator} over all filtered and sorted table rows
	 */
	@Override
	default Iterator<R> iterator() {

		return iterable().iterator();
	}

	/**
	 * Returns an {@link Iterator} over all table rows in the given range.
	 *
	 * @param offset
	 *            the 0-based index of the first row to return or <i>zero</i> or
	 *            negative for no offset
	 * @param limit
	 *            the maximum number of rows to return or <i>zero</i> or
	 *            negative for no limit
	 * @return an {@link Iterator} over all filtered and sorted table rows
	 */
	Iterator<R> iterator(int offset, int limit);

	/**
	 * Returns all table rows as a {@link Stream}.
	 *
	 * @return a {@link Stream} of all filtered and sorted table rows
	 */
	default Stream<R> stream() {

		return stream(0, 0);
	}

	/**
	 * Returns at most <i>limit</i> table rows as a {@link Stream}.
	 *
	 * @return a {@link Stream} of filtered and sorted table rows
	 */
	default Stream<R> stream(int limit) {

		return stream(0, limit);
	}

	/**
	 * Returns all table rows in the given range as a {@link Stream}.
	 *
	 * @param offset
	 *            the 0-based index of the first row to return or <i>zero</i> or
	 *            negative for no offset
	 * @param limit
	 *            the maximum number of rows to return or <i>zero</i> or
	 *            negative for no limit
	 * @return a {@link Stream} of all filtered and sorted table rows
	 */
	default Stream<R> stream(int offset, int limit) {

		return StreamSupport.stream(iterable(offset, limit).spliterator(), false);
	}

	/**
	 * Returns all table rows as an {@link Iterable}.
	 *
	 * @return an {@link Iterable} of all filtered and sorted table rows
	 */
	default Iterable<R> iterable() {

		return iterable(0, 0);
	}

	/**
	 * Returns at most <i>limit</i> table rows as an {@link Iterable}.
	 *
	 * @return an {@link Iterable} of filtered and sorted table rows
	 */
	default Iterable<R> iterable(int limit) {

		return iterable(0, limit);
	}

	/**
	 * Returns all table rows in the given range as an {@link Iterable}.
	 *
	 * @param offset
	 *            the 0-based index of the first row to return or <i>zero</i> or
	 *            negative for no offset
	 * @param limit
	 *            the maximum number of rows to return or <i>zero</i> or
	 *            negative for no limit
	 * @return an {@link Iterable} of filtered and sorted table rows
	 */
	default Iterable<R> iterable(int offset, int limit) {

		return () -> iterator(offset, limit);
	}

	/**
	 * Returns all table rows as a {@link List}.
	 *
	 * @return a {@link List} of all filtered and sorted table rows
	 */
	default List<R> list() {

		return list(0, 0);
	}

	/**
	 * Returns at most <i>limit</i> table rows as a {@link List}.
	 *
	 * @return a {@link List} of filtered and sorted table rows
	 */
	default List<R> list(int limit) {

		return list(0, limit);
	}

	/**
	 * Returns all table rows in the given range as a {@link List}.
	 *
	 * @param offset
	 *            the 0-based index of the first row to return or <i>zero</i> or
	 *            negative for no offset
	 * @param limit
	 *            the maximum number of rows to return or <i>zero</i> or
	 *            negative for no limit
	 * @return a {@link List} of filtered and sorted table rows
	 */
	default List<R> list(int offset, int limit) {

		return stream(offset, limit).collect(Collectors.toList());
	}

	/**
	 * Returns the total number of table rows after filtering.
	 *
	 * @return the total row count
	 */
	int count();

	/**
	 * Checks if at least one table row exists after filtering.
	 *
	 * @return <i>true</i> if one or more rows exist; <i>false</i> otherwise
	 */
	default boolean exists() {

		return count() > 0;
	}

	/**
	 * Asserts that this table contains at most one row and returns it as an
	 * {@link Optional}.
	 *
	 * @param exceptionFactory
	 *            the supplier to use to create the exception if multiple rows
	 *            were found
	 * @return the optional row
	 */
	default Optional<R> getOneAsOptional(Supplier<? extends RuntimeException> exceptionFactory) {

		Iterator<R> iterator = iterator(0, 2);
		Optional<R> element = iterator.hasNext()? Optional.ofNullable(iterator.next()) : Optional.empty();
		if (iterator.hasNext()) {
			throw exceptionFactory.get();
		}
		return element;
	}

	/**
	 * Asserts that this table contains at most one row and returns it as an
	 * {@link Optional}.
	 *
	 * @return the optional row
	 * @throws RuntimeException
	 *             if more than one row was found
	 */
	default Optional<R> getOneAsOptional() {

		return getOneAsOptional(() -> new RuntimeException("Multiple rows found while only one was expected."));
	}

	/**
	 * Returns the first row of this table as an {@link Optional}.
	 *
	 * @return the optional first row
	 */
	default Optional<R> getFirstAsOptional() {

		Iterator<R> iterator = iterator(0, 1);
		return iterator.hasNext()? Optional.ofNullable(iterator.next()) : Optional.empty();
	}
}
