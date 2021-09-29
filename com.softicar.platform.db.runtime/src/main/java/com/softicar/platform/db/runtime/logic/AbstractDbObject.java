package com.softicar.platform.db.runtime.logic;

import com.softicar.platform.db.runtime.entity.AbstractDbEntity;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Represents an {@link IDbTableRow} with identity.
 *
 * @param <R>
 *            the actual type of the table row
 * @author Oliver Richers
 */
public abstract class AbstractDbObject<R extends AbstractDbObject<R>> extends AbstractDbEntity<R, Integer> implements IDbObject<R> {

	@Override
	public final Integer getId() {

		return pk();
	}

	@Override
	public final R copy() {

		return table()//
			.getRowFactory()
			.get()
			.initializer()
			.initializeCopy(getThis());
	}

	@Override
	public DbObjectInitializer<R> initializer() {

		return new DbObjectInitializer<>(getThis());
	}

	protected void initialize(R source) {

		initializer().initializeCopy(source);
	}
}
