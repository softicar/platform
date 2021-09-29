package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.field.ISqlField;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IDbField<R, V> extends ISqlField<R, V>, IStaticObject {

	@Override
	IDbTable<R, ?> getTable();

	Function<? super R, V> getValueGetter();

	BiConsumer<? super R, V> getValueSetter();

	V getDefault();

	/**
	 * Returns whether <i>null</i> is a valid value for this field.
	 *
	 * @return <i>true</i> if this field is nullable; <i>false</i> otherwise
	 */
	boolean isNullable();

	/**
	 * Returns whether this field has a default value.
	 *
	 * @return <i>true</i> if this field has a default value; <i>false</i>
	 *         otherwise
	 */
	boolean hasDefault();

	/**
	 * Returns the title of this field.
	 *
	 * @return the field title (never null)
	 */
	IDisplayString getTitle();

	/**
	 * Returns an optional comment of this field.
	 *
	 * @return the optional comment
	 */
	Optional<String> getComment();

	// -------------------- checked value access -------------------- //

	/**
	 * Returns the current value of this field from the given object.
	 * <p>
	 * This method first checks if the data of the object must be loaded or
	 * reloaded from the database, which is the case for stub-objects and
	 * invalidated objects.
	 *
	 * @param object
	 *            the object
	 * @return the field value
	 */
	@Override
	default V getValue(R object) {

		return getTable().getValue(object, this);
	}

	/**
	 * Returns a collection of all values in the given rows.
	 * <p>
	 * All tables rows are un-stubbed before the values are collected.
	 * <p>
	 * TODO merge this with {@link IDataTableColumn#getValues}
	 *
	 * @param tableRows
	 *            the table rows
	 * @return collection of all values (the returned collection of values has
	 *         exactly the same size as the given collection of table rows)
	 */
	default Collection<V> getValues(Collection<R> tableRows) {

		getTable().unstubAll(tableRows);

		return tableRows//
			.stream()
			.map(row -> getValue(row))
			.collect(Collectors.toList());
	}

	/**
	 * Sets this field of the given object to the specified value.
	 * <p>
	 * This method first calls {@link #getValue} to check if the new value is
	 * different from the current value of the field. This may cause a reload of
	 * the object from the database. If the new value is equal to the existing
	 * value, this method does nothing and returns. If the new value is
	 * different, a backup of the object is created and attached to the current
	 * {@link DbTransaction} object (if not done so yet), so that the initial
	 * value can be restored on a transaction rollback. After assigning the new
	 * value to the field, the data-changed flags of the object is enabled and
	 * all registered callbacks are executed.
	 *
	 * @param object
	 *            the object containing the field
	 * @param value
	 *            the new value
	 * @return <i>true</i> if a new value was assigned, <i>false</i> if the new
	 *         value is equal to the current value
	 */
	@Override
	default boolean setValue(R object, V value) {

		return getTable().setValue(object, this, value);
	}

	// -------------------- direct value access -------------------- //

	/**
	 * Returns the current value of this field directly, without any
	 * side-effects.
	 * <p>
	 * In contrast to the {@link #getValue} method, this method does nothing but
	 * read the value from the object field.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @param object
	 *            the object
	 * @return the current value
	 */
	default V getValueDirectly(R object) {

		return getValueGetter().apply(object);
	}

	/**
	 * Assigns the specified value directly to the given object, without any
	 * side-effects.
	 * <p>
	 * In contrast to the {@link #setValue} method, this method does nothing but
	 * assign the value to the object field.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @param object
	 *            the object
	 * @param value
	 *            the new value to assign
	 */
	default void setValueDirectly(R object, V value) {

		getValueSetter().accept(object, value);
	}

	// -------------------- value copy -------------------- //

	/**
	 * Creates a copy of the given value.
	 * <p>
	 * For mutable values, like byte arrays and enum sets, it is necessary to
	 * create a deep copy. For immutable values as well as for values
	 * implementing {@link IDbTableRow}, the creation of a deep copy is not
	 * necessary; the value will be returned as it is.
	 *
	 * @param value
	 *            the original value (may be null)
	 * @return a copy of the value, the value itself, or null if the given value
	 *         is null
	 */
	default V copyValue(V value) {

		return value;
	}

	// -------------------- miscellaneous -------------------- //

	default void prefetchData(Collection<V> values) {

		// nothing to prefetch by default
		DevNull.swallow(values);
	}

	/**
	 * Creates a {@link DbFieldCaster} for this {@link IDbField}.
	 *
	 * @return a {@link DbFieldCaster} for this {@link IDbField} (never null)
	 */
	default DbFieldCaster<R, V> cast() {

		return new DbFieldCaster<>(this);
	}
}
