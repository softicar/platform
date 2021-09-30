package com.softicar.platform.db.sql.token;

import java.util.Map;
import java.util.TreeMap;

class SqlSymbolMap {

	private final Map<String, SqlSymbol> map = new TreeMap<>();

	public SqlSymbolMap() {

		for (SqlSymbol symbol: SqlSymbol.values()) {
			map.put(symbol.toString(), symbol);
		}
	}

	public SqlSymbol get(String text) {

		return map.get(text);
	}
}
