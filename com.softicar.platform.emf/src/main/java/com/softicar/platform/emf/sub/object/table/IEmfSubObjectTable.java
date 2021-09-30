package com.softicar.platform.emf.sub.object.table;

import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTable;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.sub.entity.table.IEmfSubEntityTable;
import com.softicar.platform.emf.sub.object.IEmfSubObject;

/**
 * Interface of database tables for {@link IEmfSubObject}.
 *
 * @author Oliver Richers
 */
public interface IEmfSubObjectTable<O extends IEmfSubObject<O, B>, B extends IEmfEntity<B, P>, P, S>
		extends IDbSubObjectTable<O, B, P>, IEmfSubEntityTable<O, B, B, P, S> {

	@Override
	IEmfEntityTable<B, P, S> getBaseTable();
}
