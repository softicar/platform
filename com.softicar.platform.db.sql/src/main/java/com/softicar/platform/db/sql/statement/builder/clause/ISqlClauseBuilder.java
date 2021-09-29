package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.field.ISqlField;

/**
 * Common interface of SQL statement clause builders.
 * <p>
 * An instance of this interface is used by implementations of
 * {@link ISqlClauseBuildable} to generate SQL code.
 *
 * @author Oliver Richers
 */
public interface ISqlClauseBuilder {

	ISqlClauseBuilder fixTable();

	ISqlClauseBuilder unfixTable();

	ISqlClauseBuilder addText(String text);

	ISqlClauseBuilder addText(String text, Object...args);

	ISqlClauseBuilder addField(ISqlField<?, ?> field);

	ISqlClauseBuilder addParameter(Object parameter);

	ISqlClauseBuilder addParameterPack(Iterable<?> parameters);

	String getQuotedName(ISqlField<?, ?> field);
}
