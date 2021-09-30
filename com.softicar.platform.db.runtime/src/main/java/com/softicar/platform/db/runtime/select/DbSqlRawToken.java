package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.sql.token.ISqlToken;

public class DbSqlRawToken implements ISqlToken {

	private final String text;

	public DbSqlRawToken(String text) {

		this.text = text;
	}

	@Override
	public String toString() {

		return text;
	}
}
