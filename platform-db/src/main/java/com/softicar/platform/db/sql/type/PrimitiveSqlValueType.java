package com.softicar.platform.db.sql.type;

/**
 * Base class of all primitive value types.
 * 
 * @author Oliver Richers
 */
abstract class PrimitiveSqlValueType<T> implements ISqlValueType<T> {

	@Override
	public int getColumnCount() {

		return 1;
	}
}
