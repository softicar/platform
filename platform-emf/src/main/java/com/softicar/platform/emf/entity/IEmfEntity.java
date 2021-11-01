package com.softicar.platform.emf.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfEntity<E extends IEmfEntity<E, P>, P> extends IEmfTableRow<E, P>, IDbEntity<E, P>, IEntity {

	@Override
	IEmfEntityTable<E, P, ?> table();
}
