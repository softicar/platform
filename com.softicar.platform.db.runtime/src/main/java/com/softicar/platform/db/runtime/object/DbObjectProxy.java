package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.row.DbTableRowProxy;

/**
 * A {@link DbTableRowProxy} for {@link IDbObject} instances.
 *
 * @author Oliver Richers
 */
public class DbObjectProxy<O extends IDbObject<O>> extends DbTableRowProxy<O, Integer> {

	public DbObjectProxy(O row) {

		super(row);
	}
}
