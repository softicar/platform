package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;

/**
 * Builder interface for instances of {@link IDbObjectTable}.
 *
 * @param <R>
 *            the table row class
 * @author Oliver Richers
 */
public interface IDbObjectTableBuilder<R extends IDbObject<R>> extends IDbTableBuilder<R, Integer> {

	IDbIdField<R> getIdField();
}
