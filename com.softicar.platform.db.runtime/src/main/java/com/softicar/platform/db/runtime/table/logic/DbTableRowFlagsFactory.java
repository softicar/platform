package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;

public class DbTableRowFlagsFactory {

	public static byte[] createFlags(IDbTable<?, ?> table) {

		return new byte[getFlagByteCount(table)];
	}

	private static int getFlagByteCount(IDbTable<?, ?> table) {

		int flagCount = getFlagCount(table);
		return (flagCount + 7) / 8;
	}

	private static int getFlagCount(IDbTable<?, ?> table) {

		return DbTableRowFlag.values().length + table.getDataFields().size();
	}
}
