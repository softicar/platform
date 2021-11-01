package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;

/**
 * Implementation of {@link IInMemoryDataTableFilter} for {@link String}.
 *
 * @author Alexander Schmidt
 */
class InMemoryDataTableStringFilter<R> implements IInMemoryDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final DataTableStringFilterOperator operator;
	private final String string;
	private final String lowerCaseString;

	public InMemoryDataTableStringFilter(IDataTableColumn<R, String> column, DataTableStringFilterOperator operator, String string) {

		this.column = column;
		this.operator = operator;
		this.string = stripSqlWildcards(string);
		this.lowerCaseString = this.string.toLowerCase();
	}

	@Override
	public boolean applyFilter(R row) {

		switch (operator) {
		case LIKE:
			return getValueOrEmptyString(column, row).toLowerCase().contains(lowerCaseString);
		case NOT_LIKE:
			return !getValueOrEmptyString(column, row).toLowerCase().contains(lowerCaseString);
		case REGEXP:
			return getValueOrEmptyString(column, row).matches(string);
		case NOT_REGEXP:
			return !getValueOrEmptyString(column, row).matches(string);
		default:
			throw new SofticarUnknownEnumConstantException(operator);
		}
	}

	private String getValueOrEmptyString(IDataTableColumn<R, String> column, R row) {

		String value = column.getValue(row);
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * FIXME: this should not be necessary - why are those wildcards added in
	 * the first place?
	 */
	private String stripSqlWildcards(String string) {

		if (string.startsWith("%") && string.endsWith("%")) {
			return string.substring(1, string.length() - 1);
		} else {
			return string;
		}
	}
}
