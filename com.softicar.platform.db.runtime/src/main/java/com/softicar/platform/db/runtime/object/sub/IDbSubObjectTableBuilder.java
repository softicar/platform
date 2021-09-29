package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;

/**
 * Builder interface for instances of {@link IDbSubObjectTable}.
 *
 * @param <R>
 *            the table row class
 * @param <B>
 *            the table row class of the base table
 * @param <P>
 *            the primary key value class of the base table
 * @author Oliver Richers
 */
public interface IDbSubObjectTableBuilder<R extends IDbSubObject<R, B>, B extends IDbEntity<B, P>, P> extends IDbTableBuilder<R, B> {

	IDbEntityTable<B, P> getBaseTable();

	IDbBaseField<R, B, P> getBaseField();
}
