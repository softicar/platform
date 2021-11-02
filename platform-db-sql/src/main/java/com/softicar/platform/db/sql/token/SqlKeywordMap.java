package com.softicar.platform.db.sql.token;

import java.util.Map;
import java.util.TreeMap;

class SqlKeywordMap {

	private static Map<String, SqlKeyword> keywordMap = new TreeMap<>();

	public SqlKeywordMap() {

		for (SqlKeyword keyword: SqlKeyword.values()) {
			keywordMap.put(keyword.name(), keyword);
		}
	}

	public SqlKeyword get(String keywordText) {

		return keywordMap.get(keywordText.toUpperCase());
	}
}
