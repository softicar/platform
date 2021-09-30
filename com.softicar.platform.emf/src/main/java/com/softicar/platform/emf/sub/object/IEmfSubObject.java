package com.softicar.platform.emf.sub.object;

import com.softicar.platform.db.runtime.object.sub.IDbSubObject;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;

public interface IEmfSubObject<E extends IEmfSubObject<E, B>, B extends IEmfEntity<B, ?>> extends IEmfEntity<E, B>, IDbSubObject<E, B> {

	@Override
	IEmfSubObjectTable<E, B, ?, ?> table();
}
