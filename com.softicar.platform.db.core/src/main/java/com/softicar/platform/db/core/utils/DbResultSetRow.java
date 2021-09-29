package com.softicar.platform.db.core.utils;

import com.softicar.platform.db.core.DbResultSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class DbResultSetRow {

	private final Map<String, String> values;

	public DbResultSetRow(DbResultSet resultSet, int columnCount) {

		this.values = new TreeMap<>();

		for (int i = 1; i <= columnCount; i++) {
			values.put(resultSet.getColumnName(i), resultSet.getString(i));
		}
	}

	public String get(String key) {

		return values.get(key);
	}

	public Set<Entry<String, String>> entrySet() {

		return values.entrySet();
	}
}
