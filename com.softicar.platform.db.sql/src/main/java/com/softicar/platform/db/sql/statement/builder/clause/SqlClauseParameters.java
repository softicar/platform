package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.formatting.Formatting;
import com.softicar.platform.db.sql.ISqlTableRow;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This class handles SQL parameters of {@link SqlClauseBuilder}.
 * <p>
 * TODO Refactor this class to conform to OOP guidelines, that is, instead of
 * providing static methods, an instance of this class should represent a
 * parameter list.
 *
 * @author Oliver Richers
 */
public class SqlClauseParameters {

	public static void addToParameterList(List<Object> parameters, Object parameter) {

		if (parameter == null) {
			parameters.add(parameter);
		} else if (parameter instanceof String) {
			parameters.add(parameter);
		} else if (parameter instanceof Number) {
			// java.lang.Number
			// java.lang.AtomicInteger
			// java.lang.AtomicLong
			// java.lang.BigDecimal
			// java.lang.BigInteger
			// java.lang.Byte
			// java.lang.Double
			// java.lang.Float
			// java.lang.Integer
			// java.lang.Long
			// java.lang.Short
			parameters.add(parameter);
		} else if (parameter instanceof Date) {
			// java.util.Date
			// java.sql.Date
			// java.sql.Timestamp
			parameters.add(parameter);
		} else if (parameter instanceof Boolean) {
			parameters.add(parameter);
		} else if (parameter instanceof Set<?>) {
			parameters.add(Imploder.implode((Set<?>) parameter, ","));
		} else if (parameter.getClass().isEnum()) {
			parameters.add(parameter.toString());
		} else if (parameter.getClass().isArray()) {
			parameters.add(parameter);
		} else if (parameter instanceof Day) {
			parameters.add(parameter.toString());
		} else if (parameter instanceof DayTime) {
			parameters.add(parameter.toString());
		} else if (parameter instanceof Time) {
			parameters.add(((Time) parameter).toIsoFormat());
		} else if (parameter instanceof ISqlTableRow) {
			addPrimaryKeyOfRow(parameters, (ISqlTableRow<?, ?>) parameter);
		} else {
			throw new SofticarException("Parameter of class %s not yet supported.", parameter.getClass().getCanonicalName());
		}
	}

	private static <R, P> void addPrimaryKeyOfRow(List<Object> parameters, ISqlTableRow<R, P> row) {

		P pk = row.pk();
		if (pk == null) {
			throw new IllegalArgumentException(Formatting.format("A non-persistent table row of type %s was used in a database statement.", row.getClass()));
		}
		addToParameterList(parameters, pk);
	}
}
