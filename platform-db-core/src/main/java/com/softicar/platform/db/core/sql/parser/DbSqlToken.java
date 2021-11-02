package com.softicar.platform.db.core.sql.parser;

public class DbSqlToken {

	private final DbSqlTokenType type;
	private final String text;

	public DbSqlToken(DbSqlTokenType type, String text) {

		this.type = type;
		this.text = text;
	}

	public DbSqlTokenType getType() {

		return type;
	}

	public String getText() {

		return text;
	}

	@Override
	public String toString() {

		return text;
	}
}
