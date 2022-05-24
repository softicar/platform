package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.container.CommonContainerI18n;

/**
 * List all value filer operators.
 *
 * @author Oliver Richers
 */
public enum DataTableValueFilterOperator {

	EQUAL("="),
	NOT_EQUAL("!="),

	GREATER(">"),
	GREATER_EQUAL(">="),

	LESS("<"),
	LESS_EQUAL("<="),

	EMPTY(CommonContainerI18n.EMPTY.toString()),
	NOT_EMPTY(CommonContainerI18n.NOT_EMPTY.toString());

	private final String operatorSymbol;

	private DataTableValueFilterOperator(String operatorSymbol) {

		this.operatorSymbol = operatorSymbol;
	}

	public String getOperatorSymbol() {

		return operatorSymbol;
	}

	public boolean isEmpty() {

		return equals(EMPTY);
	}

	public boolean isNotEmpty() {

		return equals(NOT_EMPTY);
	}

}
