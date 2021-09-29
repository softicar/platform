package com.softicar.platform.db.sql.statement;

/**
 * Represents a <i>SELECT</i> with a joined table.
 *
 * @param <R>
 *            the row type of the selected table
 * @param <J>
 *            the row type of the joined table
 * @author Oliver Richers
 */
public interface ISqlJoin<R, J> extends ISqlSelectOrJoin<R, J, ISqlJoin<R, J>> {

	ISqlSelect<R> getSelect();
}
