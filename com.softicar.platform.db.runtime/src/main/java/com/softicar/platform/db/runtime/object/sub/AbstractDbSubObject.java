package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.AbstractDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntity;

/**
 * Basic implementation of {@link IDbSubObject}
 *
 * @param <R>
 *            the actual type of the table row
 * @author Oliver Richers
 */
public abstract class AbstractDbSubObject<R extends AbstractDbSubObject<R, B>, B extends IDbEntity<B, ?>> extends AbstractDbEntity<R, B>
		implements IDbSubObject<R, B> {

	@Override
	public final Integer getId() {

		return pk().getId();
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
	public DbSubObjectInitializer<R, B> initializer() {

		return new DbSubObjectInitializer<>(getThis());
	}

	protected void initialize(R source) {

		initializer().initializeCopy(source);
	}
}
