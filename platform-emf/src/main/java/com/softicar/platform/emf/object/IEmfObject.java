package com.softicar.platform.emf.object;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.table.IEmfObjectTable;

public interface IEmfObject<O extends IEmfObject<O>> extends IEmfEntity<O, Integer>, IDbObject<O> {

	@Override
	IEmfObjectTable<O, ?> table();
}
