package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.entity.IDbEntity;

public interface IDbBaseField<R, B extends IDbEntity<B, P>, P> extends IDbForeignRowField<R, B, P> {

	// nothing to add
}
