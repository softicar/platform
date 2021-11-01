package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Represents an {@link IDbField} that references an {@link IDbTable}.
 * <p>
 * The values of this field are instances of {@link IDbTableRow} of the
 * referenced {@link IDbTable}.
 *
 * @author Oliver Richers
 */
public interface IDbForeignRowField<R, F extends IDbTableRow<F, FP>, FP> extends ISqlForeignRowField<R, F, FP>, IDbField<R, F> {

	@Override
	IDbField<F, FP> getTargetField();

	@Override
	IDbTable<F, FP> getTargetTable();

	/**
	 * Pre-fetches the target table rows of this field for the given table rows.
	 * <p>
	 * All referenced target table rows that are not cached or are marked as
	 * invalidated as well as those that are stubs will be loaded from the
	 * database. All other target table rows will be returned as they are.
	 *
	 * @param rows
	 *            the table rows (never null)
	 * @return all referenced target table rows (never null)
	 */
	Collection<F> prefetch(Iterable<? extends R> rows);

	/**
	 * Pre-fetches the target table rows of this field for the given table rows.
	 * <p>
	 * Equivalent to {@link #prefetch(Iterable)} but with a custom collection
	 * factory.
	 *
	 * @param rows
	 *            the table rows (never null)
	 * @param collectionFactory
	 *            the factory for the returned collection (never null)
	 * @return all referenced target table rows (never null)
	 */
	<C extends Collection<F>> C prefetch(Iterable<? extends R> rows, Supplier<C> collectionFactory);

	/**
	 * Returns the <i>ON DELETE</i> action.
	 *
	 * @return the {@link DbForeignKeyAction} (never null)
	 */
	DbForeignKeyAction getOnDelete();

	/**
	 * Returns the <i>ON UPDATE</i> action.
	 *
	 * @return the {@link DbForeignKeyAction} (never null)
	 */
	DbForeignKeyAction getOnUpdate();

	/**
	 * Returns the optional name of the foreign key.
	 *
	 * @return the optional foreign key name (never null)
	 */
	Optional<String> getForeignKeyName();

	/**
	 * Returns the primary key of the default value, if any.
	 *
	 * @return the primary key of the default value (may be null)
	 */
	FP getDefaultValuePk();
}
