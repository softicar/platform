package com.softicar.platform.db.runtime.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Builder interface for instances of {@link IDbTable}.
 *
 * @param <R>
 *            the table row class
 * @param <P>
 *            the primary key value class
 * @author Oliver Richers
 */
public interface IDbTableBuilder<R, P> {

	// -------------------- table -------------------- //

	IDbTable<R, P> getTable();

	void setTable(IDbTable<R, P> table);

	DbTableName getTableName();

	// -------------------- rows -------------------- //

	Class<R> getRowClass();

	Supplier<R> getRowFactory();

	// -------------------- title -------------------- //

	IDisplayString getTitle();

	IDisplayString getPluralTitle();

	// -------------------- fields -------------------- //

	List<IDbField<R, ?>> getAllFields();

	List<IDbField<R, ?>> getControlFields();

	List<IDbField<R, ?>> getDataFields();

	int getFieldCount();

	// -------------------- keys -------------------- //

	Collection<IDbKey<R>> getAllKeys();

	IDbTableKey<R, P> getPrimaryKey();

	Collection<IDbKey<R>> getUniqueKeys();

	Collection<IDbKey<R>> getIndexKeys();
}
