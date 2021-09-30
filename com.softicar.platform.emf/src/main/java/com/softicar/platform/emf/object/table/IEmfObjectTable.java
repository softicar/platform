package com.softicar.platform.emf.object.table;

import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.object.IEmfObject;

/**
 * Interface of database tables for {@link IEmfObject}.
 *
 * @author Oliver Richers
 */
public interface IEmfObjectTable<O extends IEmfObject<O>, S> extends IDbObjectTable<O>, IEmfEntityTable<O, Integer, S> {

	// nothing to add
}
