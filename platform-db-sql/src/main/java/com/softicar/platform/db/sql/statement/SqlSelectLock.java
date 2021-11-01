package com.softicar.platform.db.sql.statement;

public enum SqlSelectLock {

	FOR_UPDATE("FOR UPDATE"),
	IN_SHARE_MODE("LOCK IN SHARE MODE");

	private String text;

	private SqlSelectLock(String text) {

		this.text = text;
	}

	public String getText() {

		return text;
	}
}
