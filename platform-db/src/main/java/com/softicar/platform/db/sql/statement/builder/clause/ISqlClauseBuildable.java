package com.softicar.platform.db.sql.statement.builder.clause;

/**
 * This is the common interface of all SQL expressions and statements.
 * <p>
 * Implementations of this interface represent language constructs that can be
 * converted into SQL code by using {@link ISqlClauseBuilder}
 * 
 * @author Oliver Richers
 */
public interface ISqlClauseBuildable {

	/**
	 * Builds this expression by using the specified builder.
	 * 
	 * @param builder
	 *            the builder to use
	 */
	void build(ISqlClauseBuilder builder);
}
