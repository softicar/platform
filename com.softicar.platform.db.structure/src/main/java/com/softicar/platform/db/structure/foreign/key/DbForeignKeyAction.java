package com.softicar.platform.db.structure.foreign.key;

/**
 * Defines the action for an <i>ON DELETE</i> or <i>ON UPDATE</i> trigger of a
 * foreign key.
 *
 * @author Oliver Richers
 */
public enum DbForeignKeyAction {

	CASCADE("CASCADE"),
	NO_ACTION("NO ACTION"),
	RESTRICT("RESTRICT"),
	SET_DEFAULT("SET DEFAULT"),
	SET_NULL("SET NULL");

	private String sql;

	private DbForeignKeyAction(String sql) {

		this.sql = sql;
	}

	public String toSql() {

		return sql;
	}

	public boolean isCascade() {

		return this == CASCADE;
	}

	public static DbForeignKeyAction getDefault() {

		// For an ON DELETE or ON UPDATE that is not specified, the default action is always RESTRICT.
		// see https://dev.mysql.com/doc/refman/5.6/en/create-table-foreign-keys.html#foreign-keys-referential-actions
		return RESTRICT;
	}
}
